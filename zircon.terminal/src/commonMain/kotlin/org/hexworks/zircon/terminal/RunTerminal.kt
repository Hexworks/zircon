package org.hexworks.zircon.terminal

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
 */
public fun runTerminal(
    tileGrid: TileGrid,
    targetFps: Int = 60,
    quitKeys: Set<KeyboardEventMatcher> = setOf(CTRL_C),
    beforeFrame: () -> Unit = {},
    tty: Tty = systemTty(),
): TerminalSession = TerminalSession(tileGrid, targetFps, quitKeys, beforeFrame, tty).also { it.run() }

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
            }
        }
        failure?.let { throw it }
    }

    private fun handshake() {
        parser.replyListener = object : InputParser.ReplyListener {
            override fun onKittyKeyboardFlags(flags: Int) {
                kittyFlags = flags
            }

            override fun onDeviceAttributes() {
                deviceAttributesSeen = true
            }
        }
        write("${AnsiEncoder.CSI}?u${AnsiEncoder.CSI}c")
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
        write("${AnsiEncoder.CSI}?1049h${AnsiEncoder.CSI}?25l${AnsiEncoder.CSI}>${KITTY_FLAGS}u${AnsiEncoder.CSI}?1003h${AnsiEncoder.CSI}?1006h")
        val size = tty.size()
        encoder.resize(size.width, size.height)
    }

    private fun leaveModes() {
        write("${AnsiEncoder.CSI}?1003l${AnsiEncoder.CSI}?1006l${AnsiEncoder.CSI}<u${AnsiEncoder.CSI}0m${AnsiEncoder.CSI}?25h${AnsiEncoder.CSI}?1049l")
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

    private fun write(text: String) {
        val bytes = text.encodeToByteArray()
        tty.write(bytes, bytes.size)
    }

    private companion object {
        // disambiguate (1) + event types (2) + alternate keys (4) + all keys as escapes (8)
        // + associated text (16)
        const val KITTY_FLAGS = 31
        const val HANDSHAKE_TIMEOUT_MS = 2000
    }
}
