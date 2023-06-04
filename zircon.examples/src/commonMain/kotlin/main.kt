import korlibs.image.bitmap.Bitmaps
import korlibs.image.bitmap.slice
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.*
import korlibs.korge.scene.PixelatedScene
import korlibs.korge.view.*
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.ScaleMode
import korlibs.math.geom.Size
import korlibs.math.geom.SizeInt
import korlibs.math.geom.slice.splitInRows
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.fragment.buildMenuBar
import org.hexworks.zircon.api.dsl.fragment.dropdownMenu
import org.hexworks.zircon.api.dsl.fragment.menuItem
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.component.modal.EmptyModalResult
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.impl.KORGE_CONTAINER
import org.hexworks.zircon.internal.tileset.impl.korge.toRGBA


suspend fun main() = Korge(virtualSize = Size(640, 400)) {
    sceneContainer().changeTo({ ZirconKorgeScene() })
}

fun zirconGame(screen: Screen) {

    /*
    screen.display()
    screen.theme = ColorThemes.arc()

    val menuBar = buildMenuBar<String> {
        this.screen = screen
        theme = ColorThemes.arc()
        tileset = screen.tileset
        width = screen.width

        dropdownMenu {
            label = "Left"
            menuItem {
                label = "File listing"
                key = "left.list"
            }
        }

        dropdownMenu {
            label = "File"

            menuItem {
                label = "View"
                key = "file.view"
            }

            menuItem {
                label = "Edit"
                key = "file.edit"
            }
        }

        dropdownMenu {
            label = "Command"

            menuItem {
                label = "User menu"
                key = "command.usermenu"
            }

            menuItem {
                label = "Directory tree"
                key = "command.tree"
            }
        }

        dropdownMenu {
            label = "Options"

            menuItem {
                label = "Configuration"
                key = "options.configuration"
            }
        }

        dropdownMenu {
            label = "Right"

            menuItem {
                label = "File listing"
                key = "right.list"
            }

            menuItem {
                label = "Quick view"
                key = "right.quickview"
            }
        }

        onMenuItemSelected = { item ->
            println("Item selected: $item")
            KeepSubscription
        }
    }

    screen.addFragment(menuBar)
    screen.display()

     */


    val theme = ColorThemes.adriftInDreams()
    val panel = Components.panel()
        .withPreferredSize(25, 16)
        .withDecorations(ComponentDecorations.box(title = "Modal"))
        .build()


    panel.addComponent(
        Components.panel()
            .withPreferredSize(23, 13)
            .withColorTheme(theme)
            .build()
    )

    val modal = ModalBuilder.newBuilder<EmptyModalResult>()
        .withCenteredDialog(true)
        .withPreferredSize(screen.size)
        // Note that a modal *must* have its own theme and tileset
        .withColorTheme(theme)
        .withTileset(screen.tileset)
        .withComponent(panel)
        .build()

    panel.addComponent(
        Components.button()
        .withText("Close")
        .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_CENTER)
        .build().apply {
            processComponentEvents(ComponentEventType.ACTIVATED) {
                modal.close(EmptyModalResult)
            }
        })

    screen.display()
    screen.theme = theme

    screen.openModal(modal)
    screen.display()
}

//class ZirconKorgeScene : PixelatedScene(128, 128, sceneScaleMode = ScaleMode.FILL) {
class ZirconKorgeScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val config = AppConfig.newBuilder()
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .withProperty(KORGE_CONTAINER, this)
            .build()

        //Applications.startApplication()
        val application = KorgeBasicInternalApplication(config)
        val tileGrid = application.tileGrid as InternalTileGrid
        val screen = tileGrid.toScreen()

        val TILE_WIDTH = 16
        val TILE_HEIGHT = 16
        val tileSize = SizeInt(TILE_WIDTH, TILE_HEIGHT)
        val tileset = resourcesVfs["cp_437_tilesets/rex_paint_16x16.png"].readBitmap().slice()
        val tiles = tileset.splitInRows(tileSize.width, tileSize.height)

        //image(tiles[1])

        renderableView {
            this.ctx.useBatcher { batch ->
                //println("tileGrid.tiles=${tileGrid.tiles}")

                val TILE_WIDTHf = TILE_WIDTH.toFloat()
                val TILE_HEIGHTf = TILE_HEIGHT.toFloat()

                // @TODO:
                //for (layer in tileGrid.layers) {
                for (n in 0 until 2) {
                    tileGrid.tiles.forEach { (pos, tile) ->
                        //println("pos=$pos, tile=$tile")
                        when (tile) {
                            is CharacterTile -> {
                                val tex = this.ctx.getTex(tiles[tile.character.code])
                                batch.drawQuad(
                                    tex,
                                    (pos.x * TILE_WIDTHf) + n * 2,
                                    (pos.y * TILE_HEIGHTf) + n * 2,
                                    TILE_WIDTHf,
                                    TILE_HEIGHTf,
                                    colorMul = tile.foregroundColor.toRGBA()
                                )
                            }
                        }
                    }
                }
                //}

            }
        }

        for (y in 0 until 20) {
            for (x in 0 until 32) {
                tileGrid.draw(Tile.createCharacterTile('b' + x + y, StyleSet.defaultStyle().withForegroundColor(
                    TileColor.create(x * 8, 0, 0, 255))), Position.create(x, y))
            }
        }

        //zirconGame(screen)
    }
}

class KorgeBasicInternalApplication(
    override val config: AppConfig
) : InternalApplication {
    override val eventBus: EventBus = EventBus.create()
    override val eventScope: ZirconScope = ZirconScope()
    override val tileGrid: TileGrid = ThreadSafeTileGrid(config).also {
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
        //closedValue.value = true
    }

}
