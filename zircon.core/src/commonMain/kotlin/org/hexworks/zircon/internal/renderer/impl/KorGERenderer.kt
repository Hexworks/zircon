package org.hexworks.zircon.internal.renderer.impl

import korlibs.io.async.launch
import korlibs.korge.Korge
import korlibs.korge.view.Container
import kotlinx.coroutines.Dispatchers
import org.hexworks.zircon.api.application.AppConfigKey
import org.hexworks.zircon.api.application.KorGEApplication
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.korge.KorGECP437DrawSurface

object KORGE_CONTAINER : AppConfigKey<Container>

class KorGERenderer(
    val korgeBaseContainer: Container,
    tileGrid: InternalTileGrid,
    tilesetLoader: TilesetLoader<KorGECP437DrawSurface>,
) : BaseRenderer<KorGECP437DrawSurface, KorGEApplication>(tileGrid, tilesetLoader) {

    override fun create() {
        TODO("korgeBaseContainer=$korgeBaseContainer")
        //launch(Dispatchers.Default) {
        //    Korge(
        //        width = tileGrid.widthInPixels,
        //        height = tileGrid.heightInPixels,
        //        bgcolor = Colors.BLACK
        //    ) {
//
        //    }
        //}
    }

    override fun doRender(now: Long) {
        TODO("Not yet implemented")
    }

    override fun processInputEvents() {
        TODO("Not yet implemented")
    }

}