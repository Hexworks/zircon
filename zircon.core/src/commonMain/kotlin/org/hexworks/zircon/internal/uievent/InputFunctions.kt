package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.grid.InternalTileGrid

fun injectStringAsKeyboardEvents(string: String, tileGrid: InternalTileGrid): UIEventResponse {
    return string.filter {
        TextUtils.isPrintableCharacter(it)
    }.flatMap { char ->
        listOf(KeyboardEventType.KEY_PRESSED, KeyboardEventType.KEY_TYPED, KeyboardEventType.KEY_RELEASED).map { type ->
            tileGrid.process(
                KeyboardEvent(
                    type = type,
                    key = "$char",
                    code = KeyCode.findByCode(char.toInt())
                ), UIEventPhase.TARGET
            )
        }
    }.fold(Pass, UIEventResponse::pickByPrecedence)
}
