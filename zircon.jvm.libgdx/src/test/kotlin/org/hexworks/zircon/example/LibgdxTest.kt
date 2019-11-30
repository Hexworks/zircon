package org.hexworks.zircon.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Tile.Companion
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.graphics.DefaultStyleSet
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer
import java.util.*

private val size = Size.create(80, 40)
private val tileset = CP437TilesetResources.wanderlust16x16()
private const val screenWidth = 1920f
private const val screenHeight = 1080f

class LibgdxTest : ApplicationAdapter() {

    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(screenWidth, screenHeight, camera)

    private val tileGrid: InternalTileGrid = ThreadSafeTileGrid(tileset, size)
    private val renderer = LibgdxRenderer(grid = tileGrid)

    private val random = Random()
    private val terminalWidth = size.width
    private val terminalHeight = size.height
    private val layerCount = 1
    private val layerWidth = 15
    private val layerHeight = 15
    private val layerSize = Size.create(layerWidth, layerHeight)
    private val filler = Tile.defaultTile().withCharacter('x')
    private var layers: List<Layer> = (0..layerCount).map {

        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .withSize(layerSize)
                .withTileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.draw(filler, it)
        }

        val layer = LayerBuilder.newBuilder()
                .withOffset(Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)))
                .withTileGraphics(imageLayer)
                .build()

        tileGrid.addLayer(layer)
        layer
    }

    private val chars = listOf('a', 'b')
    private val styles = listOf(DefaultStyleSet(
            foregroundColor = ANSITileColor.RED,
            backgroundColor = ANSITileColor.GREEN),
            DefaultStyleSet(
                    foregroundColor = ANSITileColor.MAGENTA,
                    backgroundColor = ANSITileColor.YELLOW))

    private var currIdx = 0
    private var loopCount = 0

    override fun create() {
        camera.setToOrtho(false, viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())
        viewport.update(screenWidth.toInt(), screenHeight.toInt())
        viewport.apply()
        println(camera.viewportHeight)
        LibgdxApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(terminalWidth, terminalHeight))
                .build())
        renderer.create()
    }

    override fun render() {
        RunTimeStats.addTimedStatFor("debug.render.time") {
            val tile = Tile.newBuilder()
                    .withCharacter(chars[currIdx])
                    .withStyleSet(styles[currIdx])
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
            camera.update()
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
        camera.update()
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
        cfg.height = size.height * tileset.height
        cfg.width = size.width * tileset.width
        LwjglApplication(LibgdxTest(), cfg)
    }
}

private fun fillGrid(tileGrid: TileGrid, tile: Tile) {
    (0..tileGrid.size.height).forEach { y ->
        (0..tileGrid.size.width).forEach { x ->
            tileGrid.draw(tile, GridPosition(x, y))
        }
    }
}
