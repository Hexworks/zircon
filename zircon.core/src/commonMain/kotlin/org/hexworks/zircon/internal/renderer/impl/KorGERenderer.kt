package org.hexworks.zircon.internal.renderer.impl

import com.soywiz.korge.Korge
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.color.Colors
import com.soywiz.korio.async.launch
import kotlinx.coroutines.Dispatchers
import org.hexworks.zircon.api.application.KorGEApplication
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.korge.KorGECP437DrawSurface

class KorGERenderer(
    tileGrid: InternalTileGrid,
    tilesetLoader: TilesetLoader<KorGECP437DrawSurface>,
) : BaseRenderer<KorGECP437DrawSurface, KorGEApplication>(tileGrid, tilesetLoader) {

    override fun create() {
        launch(Dispatchers.Default) {
            Korge(
                width = tileGrid.widthInPixels,
                height = tileGrid.heightInPixels,
                bgcolor = Colors.BLACK
            ) {

            }
        }
    }

    override fun doRender(now: Long) {
        TODO("Not yet implemented")
    }

    override fun processInputEvents() {
        TODO("Not yet implemented")
    }

}