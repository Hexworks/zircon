package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.behavior.CursorHolder

class DefaultCursorHolder : CursorHolder {

    private var cursorPosition = Position.DEFAULT_POSITION
    private var cursorVisible = true

    override fun isCursorVisible() = cursorVisible

    override fun setCursorVisible(cursorVisible: Boolean) {
        this.cursorVisible = cursorVisible
    }

    @Synchronized
    override fun getCursorPosition(): Position = cursorPosition

    @Synchronized
    override fun setCursorPosition(cursorPosition: Position) {
        this.cursorPosition = cursorPosition
    }
}