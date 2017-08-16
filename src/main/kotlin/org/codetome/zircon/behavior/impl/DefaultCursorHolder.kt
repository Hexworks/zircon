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
        var fixedPos = cursorPosition
        if (fixedPos.column < 0) {
            fixedPos = fixedPos.withColumn(0)
        }
        if (fixedPos.row < 0) {
            fixedPos = fixedPos.withRow(0)
        }
        this.cursorPosition = fixedPos
    }
}