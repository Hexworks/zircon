package org.codetome.zircon.poc.drawableupgrade


// A TileGrid uses a TileImage as a backend for all operations
// which greatly simplifies the work which is needed for this
// abstraction to work
class TileGrid(width: Int,
               height: Int,
               private val backend: TileImage = TileImage(width, height))
    : DrawSurface by backend
