package org.codetome.zircon.graphics.box

import org.codetome.zircon.Position
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.box.BoxConnectingMode.CONNECT
import org.codetome.zircon.graphics.box.BoxType.BASIC
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.terminal.Size

interface BoxRenderer {

    fun drawBox(textGraphics: TextGraphics,
                topLeft: Position,
                size: Size,
                styleToUse: StyleSet,
                boxType: BoxType = BASIC,
                boxConnectingMode: BoxConnectingMode = CONNECT)
}