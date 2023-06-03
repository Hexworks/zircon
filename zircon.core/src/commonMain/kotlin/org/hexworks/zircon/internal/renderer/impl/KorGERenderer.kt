package org.hexworks.zircon.internal.renderer.impl

import korlibs.image.bitmap.Bitmaps
import korlibs.korge.render.RenderContext
import korlibs.korge.view.Container
import korlibs.korge.view.View
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

    class TileGridView(val tileGrid: InternalTileGrid) : View() {
        override fun renderInternal(ctx: RenderContext) {
            ctx.useBatcher { batch ->
                batch.drawQuad(ctx.getTex(Bitmaps.white), 0f, 0f, 100f, 100f)
            }
            println("TileGridView.renderInternal")
        }
    }

    override fun create() {
        korgeBaseContainer.addChild(TileGridView(tileGrid))
        //TODO("korgeBaseContainer=$korgeBaseContainer")
        //launch(Dispatchers.Default) {
        //    Korge(
        //        width = tileGrid.widthInPixels,
        //        height = tileGrid.heightInPixels,
        //        bgcolor = Colors.BLACK
        //    ) {
        //    }
        //}
    }

    override fun doRender(now: Long) {
        //println("doRender: $now")
        //TODO("Not yet implemented")
    }

    override fun processInputEvents() {
        //println("processInputEvents")
        //TODO("Not yet implemented")
    }
}