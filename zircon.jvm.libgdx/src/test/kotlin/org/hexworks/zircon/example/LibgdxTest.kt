package org.hexworks.zircon.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.utils.viewport.ExtendViewport
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.impl.GridPosition
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.graphics.DefaultStyleSet
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.hexworks.zircon.internal.renderer.LibgdxRenderer
import java.util.*

private val size = Size.create(80, 40)
private val tileset = CP437TilesetResources.wanderlust16x16()
private val theme = ColorThemes.solarizedLightOrange()

class GdxExample : ApplicationAdapter() {

    private val SCREEN_WIDTH = 1920f
    private val SCREEN_HEIGHT = 1080f

    private val camera = OrthographicCamera()
    private val viewport = ExtendViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera)

    private val tileGrid: TileGrid = RectangleTileGrid(tileset, size)
    private val renderer = LibgdxRenderer(grid = tileGrid)

    private val random = Random()
    private val terminalWidth = size.width
    private val terminalHeight = size.height
    private val layerCount = 1
    private val layerWidth = 15
    private val layerHeight = 15
    private val layerSize = Size.create(layerWidth, layerHeight)
    private val filler = Tiles.defaultTile().withCharacter('x')
    private var layers: List<Layer> = (0..layerCount).map {

        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
                .withSize(layerSize)
                .withTileset(tileset)
                .build()
        layerSize.fetchPositions().forEach {
            imageLayer.setTileAt(it, filler)
        }

        val layer = LayerBuilder.newBuilder()
                .withOffset(Position.create(
                        x = random.nextInt(terminalWidth - layerWidth),
                        y = random.nextInt(terminalHeight - layerHeight)))
                .withTileGraphics(imageLayer)
                .build()

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
        camera.setToOrtho(false, viewport.screenWidth.toFloat(), viewport.screenHeight.toFloat())
        viewport.update(SCREEN_WIDTH.toInt(), SCREEN_HEIGHT.toInt())
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
            val tile = Tiles.newBuilder()
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
        LwjglApplication(GdxExample(), cfg)
    }
}

private fun fillGrid(tileGrid: TileGrid, tile: Tile) {
    (0..tileGrid.size.height).forEach { y ->
        (0..tileGrid.size.width).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
