package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.data.Position

/**
 * Turns a [CellBuffer] into the bytes that bring the terminal from the previous frame to
 * this one: only changed cells are written, cells sharing a colour pair share one SGR,
 * the cursor is moved only when the next changed cell is not the one after the last, and
 * the whole frame sits between synchronized-output markers so the terminal shows it at
 * once. A frame with no change writes nothing.
 *
 * The grid is centred in the terminal. When the terminal is smaller than the grid a banner
 * replaces the grid until it is big enough again.
 */
public class AnsiEncoder(private val gridWidth: Int, private val gridHeight: Int) {

    private val previous = CellBuffer(gridWidth, gridHeight)
    private var hasPrevious = false

    private var terminalWidth = 0
    private var terminalHeight = 0
    private var offsetX = 0
    private var offsetY = 0
    private var bannerShown = false

    // Terminal state as we last left it; -1 means unknown.
    private var cursorX = -1
    private var cursorY = -1
    private var foreground = -1
    private var background = -1
    private var cursorVisible: Boolean? = null
    private var cursorBlinking: Boolean? = null

    public val tooSmall: Boolean get() = terminalWidth < gridWidth || terminalHeight < gridHeight

    /** Grid cell under the terminal cell ([column], [row]), both 0-based, clamped to the grid. */
    public fun toGridPosition(column: Int, row: Int): Position = Position(
        (column - offsetX).coerceIn(0, gridWidth - 1),
        (row - offsetY).coerceIn(0, gridHeight - 1),
    )

    /** Sets the terminal size; the next frame is a full redraw. */
    public fun resize(width: Int, height: Int) {
        terminalWidth = width
        terminalHeight = height
        offsetX = maxOf(0, (width - gridWidth) / 2)
        offsetY = maxOf(0, (height - gridHeight) / 2)
        invalidate()
    }

    /** Forgets the terminal state so the next frame redraws everything. */
    public fun invalidate() {
        hasPrevious = false
        bannerShown = false
        cursorX = -1
        cursorY = -1
        foreground = -1
        background = -1
        cursorVisible = null
        cursorBlinking = null
    }

    /**
     * Appends the bytes for [frame] to [out]. [cursor] is the grid cursor to show, or `null`
     * to hide the terminal cursor.
     */
    public fun encode(frame: CellBuffer, cursor: Position?, blinkingCursor: Boolean, out: ByteSink) {
        if (tooSmall) {
            encodeBanner(out)
            return
        }
        var open = false
        if (!hasPrevious) {
            open = true
            out.ascii(SYNC_START)
            out.ascii("$CSI?25l${CSI}0m${CSI}2J")
            cursorVisible = false
        }
        val codes = frame.codes
        val foregrounds = frame.foregrounds
        val backgrounds = frame.backgrounds
        var index = 0
        for (y in 0 until gridHeight) {
            for (x in 0 until gridWidth) {
                val code = codes[index]
                val fg = foregrounds[index]
                val bg = backgrounds[index]
                val changed = !hasPrevious ||
                    previous.codes[index] != code ||
                    previous.foregrounds[index] != fg ||
                    previous.backgrounds[index] != bg
                if (changed) {
                    if (!open) {
                        open = true
                        out.ascii(SYNC_START)
                    }
                    if (cursorX != x || cursorY != y) moveTo(x, y, out)
                    if (fg != foreground || bg != background) setColors(fg, bg, out)
                    out.codePoint(code)
                    // The cursor sits on the next cell, except after the last terminal column
                    // where terminals differ on where it goes.
                    cursorX = if (x + offsetX + 1 >= terminalWidth) -1 else x + 1
                    cursorY = y
                }
                index++
            }
        }
        previous.copyFrom(frame)
        hasPrevious = true
        open = encodeCursor(cursor, blinkingCursor, open, out)
        if (open) out.ascii(SYNC_END)
    }

    private fun encodeCursor(cursor: Position?, blinking: Boolean, wasOpen: Boolean, out: ByteSink): Boolean {
        var open = wasOpen
        fun ensureOpen() {
            if (!open) {
                open = true
                out.ascii(SYNC_START)
            }
        }
        if (cursor == null) {
            if (cursorVisible != false) {
                ensureOpen()
                out.ascii("$CSI?25l")
                cursorVisible = false
            }
            return open
        }
        if (cursorX != cursor.x || cursorY != cursor.y) {
            ensureOpen()
            moveTo(cursor.x, cursor.y, out)
        }
        if (cursorBlinking != blinking) {
            ensureOpen()
            out.ascii(if (blinking) "${CSI}1 q" else "${CSI}2 q")
            cursorBlinking = blinking
        }
        if (cursorVisible != true) {
            ensureOpen()
            out.ascii("$CSI?25h")
            cursorVisible = true
        }
        return open
    }

    private fun encodeBanner(out: ByteSink) {
        if (bannerShown) return
        val text = "Terminal too small: need ${gridWidth}x$gridHeight, have ${terminalWidth}x$terminalHeight"
        out.ascii(SYNC_START)
        out.ascii("$CSI?25l${CSI}0m${CSI}2J${CSI}1;1H")
        out.ascii(text.take(maxOf(0, terminalWidth)))
        out.ascii(SYNC_END)
        bannerShown = true
        hasPrevious = false
        cursorX = -1
        cursorY = -1
        foreground = -1
        background = -1
        cursorVisible = false
        cursorBlinking = null
    }

    private fun moveTo(x: Int, y: Int, out: ByteSink) {
        out.byte(0x1B)
        out.byte('['.code)
        out.int(y + offsetY + 1)
        out.byte(';'.code)
        out.int(x + offsetX + 1)
        out.byte('H'.code)
        cursorX = x
        cursorY = y
    }

    private fun setColors(fg: Int, bg: Int, out: ByteSink) {
        out.byte(0x1B)
        out.byte('['.code)
        var first = true
        if (fg != foreground) {
            out.rgb(38, fg)
            first = false
        }
        if (bg != background) {
            if (!first) out.byte(';'.code)
            out.rgb(48, bg)
        }
        out.byte('m'.code)
        foreground = fg
        background = bg
    }

    public companion object {
        public const val ESC: String = "\u001b"
        public const val CSI: String = "\u001b["
        public const val SYNC_START: String = "\u001b[?2026h"
        public const val SYNC_END: String = "\u001b[?2026l"
    }
}
