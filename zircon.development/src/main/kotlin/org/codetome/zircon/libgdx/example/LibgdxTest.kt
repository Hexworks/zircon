package org.codetome.zircon.libgdx.example

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.Stats
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.data.*
import org.codetome.zircon.api.grid.TileGrid
import org.codetome.zircon.internal.graphics.DefaultStyleSet
import org.codetome.zircon.internal.grid.ThreadSafeTileGrid
import org.codetome.zircon.internal.graphics.DefaultLayer
import org.codetome.zircon.internal.graphics.MapTileImage
import org.codetome.zircon.libgdx.impl.LibgdxRenderer
import org.codetome.zircon.libgdx.impl.LibgdxTileset
import java.util.*

private val size = Size.create(70, 40)
val tileset = LibgdxTileset.rexPaint16x16()

class GdxExample : ApplicationAdapter() {

    private val tileGrid: TileGrid<Char, TextureRegion> = ThreadSafeTileGrid(tileset, size)
    private val renderer = LibgdxRenderer(grid = tileGrid)

    private val random = Random()
    private val terminalWidth = size.xLength
    private val terminalHeight = size.yLength
    private val layerCount = 1
    private val layerWidth = 15
    private val layerHeight = 15
    private val layerSize = Size.create(layerWidth, layerHeight)
    private var layers : List<DefaultLayer<Char, TextureRegion>> = listOf()

    private val chars = listOf('a', 'b')
    private val styles = listOf(DefaultStyleSet(
            foregroundColor = ANSITextColor.RED,
            backgroundColor = ANSITextColor.GREEN),
            DefaultStyleSet(
                    foregroundColor = ANSITextColor.MAGENTA,
                    backgroundColor = ANSITextColor.YELLOW))

    var currIdx = 0
    var loopCount = 0

    override fun create() {
        renderer.create()
    }

    override fun render() {
        Stats.addTimedStatFor("terminalBenchmark") {
            val tile = CharacterTile(
                    key = chars[currIdx],
                    style = styles[currIdx])
            fillGrid(tileGrid, tile)
            layers.forEach {
                tileGrid.removeLayer(it)
            }
            val filler = CharacterTile('x')
            layers = (0..layerCount).map {

                val imageLayer = MapTileImage(layerSize, tileset)
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
            currIdx = if (currIdx == 0) 1 else 0
            loopCount++
        }
        if (loopCount.rem(100) == 0) {
            Stats.printStats()
        }
        renderer.render()
    }

    override fun dispose() {
        renderer.dispose()
    }
}

object GdxLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val cfg = LwjglApplicationConfiguration()
        cfg.title = "LibGDX Test"
        cfg.height = size.yLength * tileset.height()
        cfg.width = size.xLength * tileset.width()
        LwjglApplication(GdxExample(), cfg)
    }
}

private fun fillGrid(tileGrid: TileGrid<Char, out Any>, tile: Tile<Char>) {
    (0..tileGrid.getBoundableSize().yLength).forEach { y ->
        (0..tileGrid.getBoundableSize().xLength).forEach { x ->
            tileGrid.setTileAt(GridPosition(x, y), tile)
        }
    }
}
