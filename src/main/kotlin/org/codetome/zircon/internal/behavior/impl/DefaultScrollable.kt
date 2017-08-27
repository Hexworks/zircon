package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.CursorHandler
import org.codetome.zircon.api.behavior.Scrollable

class DefaultScrollable private constructor(private val cursorHandler: CursorHandler,
                                            private val visibleSpaceSize: Size)
    : Scrollable, CursorHandler by cursorHandler {

    private var offset = Position.DEFAULT_POSITION

    constructor(cursorSpaceSize: Size,
                visibleSpaceSize: Size) : this(
            cursorHandler = DefaultCursorHandler(cursorSpaceSize),
            visibleSpaceSize = visibleSpaceSize)

    override fun getVisibleSpaceSize() = visibleSpaceSize

    override fun getVisibleOffset() = offset

    override fun setVisibleOffset(offset: Position) {
        this.offset = offset
    }
}