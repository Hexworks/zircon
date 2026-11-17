package org.hexworks.zircon.terminal

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.SystemTheme
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventMatcher
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.terminal.input.InputParser
import kotlin.time.TimeSource

/** Ctrl+C as a key press: the default quit key of [runTerminal]. */
public val CTRL_C: KeyboardEventMatcher =
    KeyboardEventMatcher(type = KeyboardEventType.KEY_PRESSED, code = KeyCode.KEY_C, ctrlDown = true)

/**
 * Renders [tileGrid] in the terminal the process runs in and blocks until the grid is
 * closed, a [quitKeys] key is pressed, the process is told to stop, or the input ends.
 *
 * ```kotlin
 * val grid = tileGrid { size = Size(80, 24) }
 * runTerminal(grid)
 * ```
 *
 * Needs a terminal with truecolor and the kitty keyboard protocol (foot, ghostty, kitty,
 * wezterm, alacritty 0.13+); fails with a message when the terminal does not answer the
 * protocol query. The grid keeps its size: it is centred in the terminal, and a banner
 * replaces it while the terminal is too small. In raw mode Ctrl+C is a key like any other,
 * so pass an empty [quitKeys] to handle it yourself. [beforeFrame] runs before every
 * render, on the loop's thread. Returns the finished [TerminalSession] with its frame
 * statistics.
 *
 * The terminal's own colors are read at startup (`OSC 10`, `OSC 11`, `OSC 4`) and
 * [onTerminalColors] gets them, on the loop's thread, before the first frame and again
 * whenever they change: the terminal announces a theme switch (mode 2031) or the background
 * answers differently to a poll every two seconds. Build a palette from them with
 * [TerminalColors.toComponentPalette]. The same colors become the system palette
 * (`SystemTheme.palette`), so a screen that calls `followSystemPalette` adopts them without
 * this callback. A terminal that does not answer (tmux) never calls it; there the desktop
 * setting decides light / dark (`SystemTheme.mode`, re-sampled every two seconds).
 *
 * Log statements written while the grid is on screen would scribble over it, so when the
 * application installed no [LoggerFactory.sink] of its own they are held back and printed
 * to the error stream once the terminal is restored. An application that did install one
 * keeps it, and must route the statements away from the terminal itself.
 */
public fun runTerminal(
    tileGrid: TileGrid,
    targetFps: Int = 60,
    quitKeys: Set<KeyboardEventMatcher> = setOf(CTRL_C),
    beforeFrame: () -> Unit = {},
    onTerminalColors: (TerminalColors) -> Unit = {},
    tty: Tty = systemTty(),
): TerminalSession = TerminalSession(tileGrid, targetFps, quitKeys, beforeFrame, tty, onTerminalColors).also { it.run() }

/**
 * One run of the loop. Kept separate from [runTerminal] so tests can drive it with a fake
 * [Tty].
 */
public class TerminalSession(
    tileGrid: TileGrid,
    private val targetFps: Int,
    private val quitKeys: Set<KeyboardEventMatcher>,
    private val beforeFrame: () -> Unit,
    private val tty: Tty,
    private val onTerminalColors: (TerminalColors) -> Unit = {},
) {
    private val grid = tileGrid as InternalTileGrid
    private val renderer = TerminalRenderer(tileGrid)
    private val frame = CellBuffer(tileGrid.size.width, tileGrid.size.height)
    private val encoder = AnsiEncoder(tileGrid.size.width, tileGrid.size.height)
    private val parser = InputParser(encoder::toGridPosition)
    private val sink = ByteSink()
    private val input = ByteArray(16 * 1024)

    private var kittyFlags: Int? = null
    private var deviceAttributesSeen = false
    private var stop = false

    // The color replies of the query in flight; published when the DA reply that closes it arrives.
    private var foreground: Color? = null
    private var background: Color? = null
    private val ansi = arrayOfNulls<Color>(16)
    private var colorsQueried = false
    private var lastBackgroundPoll = 0.0

    /** The colors the terminal reported last, `null` when it never answered. */
    public var terminalColors: TerminalColors? = null
        private set

    /** Frames rendered so far; the benchmark launcher reads it. */
    public var framesRendered: Long = 0
        private set

    /** Time spent in the render tick (input, animations, drawing every layer), summed over all frames. */
    public var renderMillis: Double = 0.0
        private set

    /** Time spent diffing and encoding frames, summed over all frames. */
    public var encodeMillis: Double = 0.0
        private set

    /** Time spent writing frames to the terminal, summed over all frames. */
    public var writeMillis: Double = 0.0
        private set

    /** Bytes written for frames so far. */
    public var bytesWritten: Long = 0
        private set

    public fun run() {
        tty.open()
        val heldLogs = holdLogs()
        var failure: Throwable? = null
        try {
            handshake()
            enterModes()
            loop()
        } catch (e: Throwable) {
            failure = e
        } finally {
            // Restore first so a stack trace prints on a sane terminal.
            try {
                leaveModes()
            } finally {
                tty.close()
                renderer.close()
                releaseLogs(heldLogs)
            }
        }
        failure?.let { throw it }
    }

    /** Diverts the default log sink into a buffer; returns `null` when the application has its own sink. */
    private fun holdLogs(): BufferedLogSink? {
        if (LoggerFactory.sink !== LoggerFactory.defaultSink) return null
        return BufferedLogSink().also { LoggerFactory.sink = it }
    }

    /** Puts the default sink back and prints what was held, now that the screen is sane. */
    private fun releaseLogs(held: BufferedLogSink?) {
        if (held == null) return
        if (LoggerFactory.sink === held) LoggerFactory.sink = LoggerFactory.defaultSink
        held.drain()?.let { tty.writeError(it) }
    }

    private fun handshake() {
        parser.replyListener = object : InputParser.ReplyListener {
            override fun onKittyKeyboardFlags(flags: Int) {
                kittyFlags = flags
            }

            override fun onDeviceAttributes() {
                deviceAttributesSeen = true
                if (colorsQueried) publishColors()
            }

            override fun onColor(code: Int, index: Int, color: Color) {
                when (code) {
                    10 -> foreground = color
                    11 -> if (colorsQueried) background = color
                    else terminalColors?.let { known -> if (color != known.background) queryColors() }
                    4 -> if (index in 0..15) ansi[index] = color
                }
            }

            override fun onColorScheme(dark: Boolean) {
                queryColors()
            }
        }
        write(colorQueries() + "${AnsiEncoder.CSI}?u${AnsiEncoder.CSI}c")
        colorsQueried = true
        val started = TimeSource.Monotonic.markNow()
        while (!deviceAttributesSeen) {
            val remaining = HANDSHAKE_TIMEOUT_MS - started.elapsedNow().inWholeMilliseconds.toInt()
            if (remaining <= 0) break
            val n = tty.read(input, remaining)
            if (n < 0) break
            if (n > 0) parser.feed(input, n).forEach { renderer.enqueue(it) }
        }
        check(kittyFlags != null) {
            "This terminal does not support the kitty keyboard protocol, which the Zircon terminal " +
                "renderer needs. Use foot, ghostty, kitty, wezterm or alacritty 0.13+ (in tmux: 3.4+)."
        }
    }

    private fun enterModes() {
        // Alt screen, hidden cursor, all keys as escape codes with event types and text,
        // SGR mouse with motion.
        // ... and color scheme change reports (mode 2031).
        write("${AnsiEncoder.CSI}?1049h${AnsiEncoder.CSI}?25l${AnsiEncoder.CSI}>${KITTY_FLAGS}u${AnsiEncoder.CSI}?1003h${AnsiEncoder.CSI}?1006h${AnsiEncoder.CSI}?2031h")
        val size = tty.size()
        encoder.resize(size.width, size.height)
    }

    private fun leaveModes() {
        write("${AnsiEncoder.CSI}?2031l${AnsiEncoder.CSI}?1003l${AnsiEncoder.CSI}?1006l${AnsiEncoder.CSI}<u${AnsiEncoder.CSI}0m${AnsiEncoder.CSI}?25h${AnsiEncoder.CSI}?1049l")
    }

    private fun loop() {
        val frameMillis = 1000.0 / targetFps
        val clock = TimeSource.Monotonic.markNow()
        var nextFrameAt = 0.0
        while (!stop && !grid.closed && !tty.terminateRequested) {
            if (tty.takeResize()) {
                val size = tty.size()
                encoder.resize(size.width, size.height)
            }
            val now = clock.elapsedNow().inWholeMicroseconds / 1000.0
            if (now - lastBackgroundPoll >= COLOR_POLL_MS) {
                // Terminals without mode 2031 never announce a theme switch: ask for the
                // background now and then, and re-read everything when it changed.
                lastBackgroundPoll = now
                if (!colorsQueried) write("${AnsiEncoder.OSC}11;?${AnsiEncoder.ST}")
                // A terminal that never answered (tmux) leaves the desktop setting as the
                // only source of light / dark; sample it at the same pace.
                if (terminalColors == null) SystemTheme.refresh()
            }
            val wait = (nextFrameAt - now).toInt()
            if (wait > 0) {
                val n = tty.read(input, wait)
                if (n < 0) return
                if (n > 0) {
                    dispatch(n)
                    continue
                }
            } else {
                val n = tty.read(input, 0)
                if (n < 0) return
                if (n > 0) dispatch(n)
            }
            renderFrame()
            val after = clock.elapsedNow().inWholeMicroseconds / 1000.0
            nextFrameAt = if (after - nextFrameAt > frameMillis) after + frameMillis else nextFrameAt + frameMillis
        }
    }

    private fun dispatch(length: Int) {
        for (event in parser.feed(input, length)) {
            if (event is KeyboardEvent && quitKeys.any { it.matches(event) }) {
                stop = true
                return
            }
            renderer.enqueue(event)
        }
    }

    private fun renderFrame() {
        val started = TimeSource.Monotonic.markNow()
        beforeFrame()
        renderer.render(frame)
        val rendered = started.elapsedNow().inWholeMicroseconds / 1000.0
        renderMillis += rendered
        sink.reset()
        val cursor = if (grid.isCursorVisible) grid.cursorPosition else null
        encoder.encode(frame, cursor, grid.config.isCursorBlinking, sink)
        val encoded = started.elapsedNow().inWholeMicroseconds / 1000.0
        encodeMillis += encoded - rendered
        if (sink.size > 0) {
            tty.write(sink.array(), sink.size)
            bytesWritten += sink.size
            writeMillis += started.elapsedNow().inWholeMicroseconds / 1000.0 - encoded
        }
        framesRendered++
    }

    /** Asks for every color again; the DA reply at the end tells when all answers are in. */
    private fun queryColors() {
        if (colorsQueried) return
        colorsQueried = true
        write(colorQueries() + "${AnsiEncoder.CSI}c")
    }

    private fun colorQueries(): String = buildString {
        append(AnsiEncoder.OSC).append("10;?").append(AnsiEncoder.ST)
        append(AnsiEncoder.OSC).append("11;?").append(AnsiEncoder.ST)
        for (i in 0 until 16) append(AnsiEncoder.OSC).append("4;").append(i).append(";?").append(AnsiEncoder.ST)
    }

    private fun publishColors() {
        colorsQueried = false
        val fg = foreground
        val bg = background
        foreground = null
        background = null
        val slots = List(16) { i -> ansi[i] ?: TerminalColors.DEFAULT_ANSI[i] }
        ansi.fill(null)
        if (fg == null || bg == null) return
        val colors = TerminalColors(fg, bg, slots)
        if (colors == terminalColors) return
        terminalColors = colors
        // The terminal is the host: its colors become the system palette every screen that
        // follows it adopts (`screen.followSystemPalette`), and its background the mode.
        SystemTheme.update(
            mode = if (colors.isDark) ThemeMode.DARK else ThemeMode.LIGHT,
            palette = colors.toComponentPalette(),
        )
        onTerminalColors(colors)
    }

    private fun write(text: String) {
        val bytes = text.encodeToByteArray()
        tty.write(bytes, bytes.size)
    }

    private companion object {
        // disambiguate (1) + event types (2) + alternate keys (4) + all keys as escapes (8)
        // + associated text (16)
        const val KITTY_FLAGS = 31
        const val HANDSHAKE_TIMEOUT_MS = 2000
        const val COLOR_POLL_MS = 2000.0
    }
}
