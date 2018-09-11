package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.TileGraphics

interface ComponentRenderer {

    fun render(tileGraphics: TileGraphics, size: Size)
}
