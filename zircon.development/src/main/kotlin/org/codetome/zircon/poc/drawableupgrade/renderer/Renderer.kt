package org.codetome.zircon.poc.drawableupgrade.renderer

import org.codetome.zircon.poc.drawableupgrade.drawables.TileGrid

interface Renderer<T> {

    fun render(grid: TileGrid)
}
