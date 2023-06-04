import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.slice
import korlibs.image.color.Colors
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.Korge
import korlibs.korge.annotations.KorgeExperimental
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.korge.view.SContainer
import korlibs.korge.view.renderableView
import korlibs.math.geom.Size
import korlibs.math.geom.SizeInt
import korlibs.math.geom.slice.splitInRows
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.StackedTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.RenderableContainer
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.KORGE_CONTAINER
import org.hexworks.zircon.internal.tileset.impl.korge.toRGBA


suspend fun main() = Korge(virtualSize = Size(640, 400)) {
    sceneContainer().changeTo({ ZirconKorgeScene() })
}

fun zirconGame(screen: Screen) {
    screen.apply {
        display()
        theme = ColorThemes.arc()
        addComponent(buildHbox {

            spacing = 1
            decoration = margin(1)

            vbox {
                header {
                    +"Hello, KorGE!"
                }
                paragraph {
                    +"This is a paragraph"
                }
                button {
                    +"Yep"
                }
            }

            vbox {
                header {
                    +"Another box"
                }
                paragraph {
                    +"This is a paragraph"
                }
                button {
                    +"Nope"
                }
            }

        })
    }
}

//class ZirconKorgeScene : PixelatedScene(128, 128, sceneScaleMode = ScaleMode.FILL) {
@OptIn(KorgeExperimental::class)
class ZirconKorgeScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val config = AppConfig.newBuilder()
            .withProperty(KORGE_CONTAINER, this)
            .build()

        //Applications.startApplication()
        val application = KorgeBasicInternalApplication(config)
        val tileGrid = application.tileGrid as InternalTileGrid
        val screen = tileGrid.toScreen()

        val tileWidth = 16
        val tileHeight = 16
        val tileSize = SizeInt(tileWidth, tileHeight)
        val tilemapBitmap = resourcesVfs["cp_437_tilesets/rex_paint_16x16.png"].readBitmap().toBMP32()
        val bgTile = Bitmap32(tileWidth, tileHeight) { _, _ -> Colors.WHITE }.slice()
        val tiles = tilemapBitmap.slice().splitInRows(tileSize.width, tileSize.height)

        //image(tilemapBitmapInverted.slice())
        //image(tiles[1])

        renderableView {
            this.ctx.useBatcher { batch ->
                //println("tileGrid.tiles=${tileGrid.tiles}")

                val tileWidthF = tileWidth.toFloat()
                val tileHeightF = tileHeight.toFloat()

                val bgTex = this.ctx.getTex(bgTile)

                tileGrid.renderAllTiles { pos, tile, tileset ->
                    val px = (pos.x * tileWidthF)
                    val py = (pos.y * tileHeightF)

                    when (tile) {
                        is CharacterTile -> {
                            batch.drawQuad(
                                bgTex,
                                px, py, tileWidthF, tileHeightF,
                                colorMul = tile.backgroundColor.toRGBA()
                            )
                            batch.drawQuad(
                                this.ctx.getTex(tiles[tile.character.code]),
                                px, py, tileWidthF, tileHeightF,
                                colorMul = tile.foregroundColor.toRGBA()
                            )
                        }
                    }
                }

            }
        }

//        for (y in 0 until 20) {
//            for (x in 0 until 32) {
//                tileGrid.draw(Tile.createCharacterTile('b' + x + y, StyleSet.defaultStyle()
//                    .withForegroundColor(TileColor.create(x * 8, 0, 0, 255))
//                    .withBackgroundColor(if (x > 16) TileColor.create(0, x * 8, y * 8, 255) else TileColor.transparent())
//                ), Position.create(x, y))
//            }
//        }

        zirconGame(screen)
    }
}

class KorgeBasicInternalApplication(
    override val config: AppConfig
) : InternalApplication {
    override val eventBus: EventBus = EventBus.create()
    override val eventScope: ZirconScope = ZirconScope()
    override val tileGrid: TileGrid = Applications.createTileGrid(config, eventBus).asInternal().also {
        it.application = this
    }

    override fun beforeRender(listener: (RenderData) -> Unit): Subscription {
        TODO("Not yet implemented")
    }

    override fun afterRender(listener: (RenderData) -> Unit): Subscription {
        TODO("Not yet implemented")
    }

    override fun asInternal(): InternalApplication = this

    override val closedValue: ObservableValue<Boolean> get() = TODO()

    override fun close() {
    }

}

private fun InternalTileGrid.renderAllTiles(
    renderTile: (position: Position, tile: Tile?, tileset: TilesetResource) -> Unit
) {
    val layers = fetchLayers()
    val gridPositions = size.fetchPositions().toList()
    val tiles = mutableListOf<Pair<Tile, TilesetResource>>()
    gridPositions.forEach { pos ->
        tiles@ for (i in layers.size - 1 downTo 0) {
            val (layerPos, layer) = layers[i]
            val toRender = layer.getTileAtOrNull(pos - layerPos)?.tiles() ?: listOf()
            for (j in toRender.size - 1 downTo 0) {
                val tile = toRender[j]
                val tileset = tile.finalTileset(layer)
                tiles.add(0, tile to tileset)
                if (tile.isOpaque) {
                    break@tiles
                }
            }
        }

        var idx = 1
        for ((tile, tileset) in tiles) {

            renderTile(
                pos,
                tile,
                tileset
            )
            idx++
        }
        tiles.clear()
    }
}

private fun RenderableContainer.fetchLayers(): List<Pair<Position, TileGraphics>> {
    return renderables.map { renderable ->
        val tg = FastTileGraphics(
            initialSize = renderable.size,
            initialTileset = renderable.tileset,
        )
        if (!renderable.isHidden) {
            renderable.render(tg)
        }
        renderable.position to tg
    }
}

private fun Tile.tiles(): List<Tile> = if (this is StackedTile) {
    tiles.flatMap { it.tiles() }
} else listOf(this)

private fun Tile.finalTileset(graphics: TileGraphics): TilesetResource {
    return if (this is TilesetHolder) {
        tileset
    } else graphics.tileset
}
