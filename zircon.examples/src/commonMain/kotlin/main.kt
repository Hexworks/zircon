import korlibs.image.bitmap.Bitmaps
import korlibs.time.*
import korlibs.korge.*
import korlibs.korge.view.*
import korlibs.image.color.*
import korlibs.korge.animate.*
import korlibs.korge.input.*
import korlibs.io.async.*
import korlibs.io.lang.*
import korlibs.korge.render.RenderContext
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import kotlinx.coroutines.*
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.cobalt.databinding.internal.binding.ListBinding
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.fragment.buildMenuBar
import org.hexworks.zircon.api.dsl.fragment.dropdownMenu
import org.hexworks.zircon.api.dsl.fragment.menuItem
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.grid.ThreadSafeTileGrid
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.internal.renderer.impl.KORGE_CONTAINER
import org.hexworks.zircon.internal.renderer.impl.KorGERenderer
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

class BasicInternalApplication(
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

class ZirconKorgeScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val config = AppConfig.newBuilder()
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .withProperty(KORGE_CONTAINER, this)
            .build()
        //Applications.startApplication()
        val application = BasicInternalApplication(config)
        val tileGrid = application.tileGrid as InternalTileGrid
        val screen = tileGrid.toScreen()

        renderableView {
            this.ctx.useBatcher { batch ->
                println(tileGrid.getTileAtOrNull(Position.create(0, 0)))
                val tex = this.ctx.getTex(Bitmaps.white)
                batch.drawQuad(tex, 0f, 0f, 100f, 100f)
            }
        }

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
    }
}

suspend fun main() = Korge {
    sceneContainer().changeTo({ ZirconKorgeScene() })
}
