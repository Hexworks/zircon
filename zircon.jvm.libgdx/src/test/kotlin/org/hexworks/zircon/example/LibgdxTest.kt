package org.hexworks.zircon.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.GridPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.graphics.DefaultLayer
import org.hexworks.zircon.internal.graphics.DefaultStyleSet
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer
import java.util.*

private val size = Size.create(80, 40)
val tileset = BuiltInCP437TilesetResource.WANDERLUST_16X16

class GdxExample : ApplicationAdapter() {

    private val tileGrid: TileGrid = RectangleTileGrid(tileset, size)
    private val renderer = LibgdxRenderer(grid = tileGrid)

    private val random = Random()
    private val terminalWidth = size.xLength
    private val terminalHeight = size.yLength
    private val layerCount = 1
    private val layerWidth = 15
    private val layerHeight = 15
    private val layerSize = Size.create(layerWidth, layerHeight)
    private val filler = Tiles.defaultTile().withCharacter('x')
    private var layers: List<DefaultLayer> = (0..layerCount).map {

        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .size(layerSize)
                .tileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = DefaultLayer(
                position = Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)),
                backend = imageLayer)

        tileGrid.pushLayer(layer)
        layer
    }

    private val chars = listOf('a', 'b')
    private val styles = listOf(DefaultStyleSet(
            foregroundColor = ANSITileColor.RED,
            backgroundColor = ANSITileColor.GREEN),
            DefaultStyleSet(
                    foregroundColor = ANSITileColor.MAGENTA,
                    backgroundColor = ANSITileColor.YELLOW))

    var currIdx = 0
    var loopCount = 0

    override fun create() {
        renderer.create()
    }

    override fun render() {
        RunTimeStats.addTimedStatFor("debug.render.time") {
            val tile = Tiles.newBuilder()
                    .character(chars[currIdx])
                    .styleSet(styles[currIdx])
                    .build()
            fillGrid(tileGrid, tile)
            layers.forEach {
                it.moveTo(Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)))
            }
            currIdx = if (currIdx == 0) 1 else 0
            loopCount++
            renderer.render()
        }
    }

    override fun dispose() {
        renderer.close()
    }
}

object GdxLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val cfg = LwjglApplicationConfiguration()
        cfg.title = "LibGDX Test"
        cfg.height = size.yLength * tileset.height
        cfg.width = size.xLength * tileset.width
        LwjglApplication(GdxExample(), cfg)
    }
}

private fun fillGrid(tileGrid: TileGrid, tile: Tile) {
    (0..tileGrid.size().yLength).forEach { y ->
        (0..tileGrid.size().xLength).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
