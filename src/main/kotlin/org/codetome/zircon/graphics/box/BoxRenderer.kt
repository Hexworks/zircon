package org.codetome.zircon.graphics.box

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.graphics.TextGraphics
import org.codetome.zircon.graphics.box.BoxConnectingMode.CONNECT
import org.codetome.zircon.graphics.box.BoxType.BASIC
import org.codetome.zircon.graphics.style.StyleSet
import org.codetome.zircon.terminal.TerminalSize

interface BoxRenderer {

    fun drawBox(target: TextGraphics,
                topLeft: TerminalPosition,
                size: TerminalSize,
                styleToUse: StyleSet,
                boxType: BoxType = BASIC,
                boxConnectingMode: BoxConnectingMode = CONNECT)
}