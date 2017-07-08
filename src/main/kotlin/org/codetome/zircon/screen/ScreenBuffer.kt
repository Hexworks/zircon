package org.codetome.zircon.screen

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.graphics.impl.BasicTextImage
import org.codetome.zircon.terminal.TerminalSize

/**
 * Defines a buffer used by [TerminalScreen] to keep its state of what's currently displayed and what
 * the edit buffer looks like.
 * A [ScreenBuffer] is essentially a two-dimensional array of [TextCharacter]s with some utility
 * methods to inspect and manipulate it in a safe way.
 */
class ScreenBuffer private constructor(private val backend: BasicTextImage) : TextImage by backend {

    constructor(size: TerminalSize, filler: TextCharacter) : this(BasicTextImage(
            size = size,
            initialContent = filler
    ))

    override fun resize(newSize: TerminalSize, filler: TextCharacter): ScreenBuffer {
        val resizedBackend = backend.resize(newSize, filler)
        return ScreenBuffer(resizedBackend)
    }

    /**
     * Copies the content from a TextImage into this buffer.
     */
    fun copyFrom(source: TextImage, startRowIndex: Int, rows: Int, startColumnIndex: Int, columns: Int, destinationRowOffset: Int, destinationColumnOffset: Int) {
        source.copyTo(backend, startRowIndex, rows, startColumnIndex, columns, destinationRowOffset, destinationColumnOffset)
    }
}
