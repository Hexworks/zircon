package org.codetome.zircon.graphics.box

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.graphics.TextImage
import org.codetome.zircon.graphics.box.BoxConnectingMode.CONNECT
import org.codetome.zircon.graphics.box.BoxType.BASIC
import org.codetome.zircon.graphics.style.StyleSet

interface BoxRenderer {

    fun drawBox(textImage: TextImage,
                topLeft: Position,
                size: Size,
                styleToUse: StyleSet,
                boxType: BoxType = BASIC,
                boxConnectingMode: BoxConnectingMode = CONNECT)
}