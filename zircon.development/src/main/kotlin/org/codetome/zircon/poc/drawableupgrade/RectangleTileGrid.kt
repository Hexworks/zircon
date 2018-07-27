package org.codetome.zircon.poc.drawableupgrade

import org.codetome.zircon.poc.drawableupgrade.drawables.*


class RectangleTileGrid(width: Int,
                        height: Int,
                        private val backend: TileImage = ThreadedTileImage(width, height))
    : TileGrid, DrawSurface by backend
