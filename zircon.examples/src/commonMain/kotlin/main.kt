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
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.application.AppConfig
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
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.grid.InternalTileGrid
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

class KorgeTilegrid(override val config: AppConfig) : TileGrid, InternalTileGrid {
    override var backend: Layer
        get() = TODO("Not yet implemented")
        set(value) {}
    override var layerable: InternalLayerable
        get() = TODO("Not yet implemented")
        set(value) {}
    override var animationHandler: InternalAnimationRunner
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cursorHandler: InternalCursorHandler
        get() = TODO("Not yet implemented")
        set(value) {}
    override var application: InternalApplication
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun delegateTo(tileGrid: InternalTileGrid) {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun asInternal(): InternalTileGrid = this

    override fun start(animation: Animation): AnimationHandle {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override val closedValue: ObservableValue<Boolean>
        get() = TODO("Not yet implemented")

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun draw(tile: Tile, drawPosition: Position) {
        TODO("Not yet implemented")
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        TODO("Not yet implemented")
    }

    override fun draw(tileMap: Map<Position, Tile>) {
        TODO("Not yet implemented")
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        TODO("Not yet implemented")
    }

    override fun draw(tileComposite: TileComposite) {
        TODO("Not yet implemented")
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        TODO("Not yet implemented")
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        TODO("Not yet implemented")
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        TODO("Not yet implemented")
    }

    override fun applyStyle(styleSet: StyleSet) {
        TODO("Not yet implemented")
    }

    override fun fill(filler: Tile) {
        TODO("Not yet implemented")
    }

    override val tiles: Map<Position, Tile>
        get() = TODO("Not yet implemented")
    override val size: Size
        get() = TODO("Not yet implemented")
    override var tileset: TilesetResource
        get() = TODO("Not yet implemented")
        set(value) {}
    override val tilesetProperty: Property<TilesetResource>
        get() = TODO("Not yet implemented")
    override val layers: ObservableList<out InternalLayer>
        get() = TODO("Not yet implemented")

    override fun getLayerAtOrNull(level: Int): LayerHandle? {
        TODO("Not yet implemented")
    }

    override fun addLayer(layer: Layer): LayerHandle {
        TODO("Not yet implemented")
    }

    override fun insertLayerAt(level: Int, layer: Layer): LayerHandle {
        TODO("Not yet implemented")
    }

    override fun setLayerAt(level: Int, layer: Layer): LayerHandle {
        TODO("Not yet implemented")
    }

    override fun onShutdown(listener: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun putTile(tile: Tile) {
        TODO("Not yet implemented")
    }

    override var isCursorVisible: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    override var cursorPosition: Position
        get() = TODO("Not yet implemented")
        set(value) {}
    override val isCursorAtTheEndOfTheLine: Boolean
        get() = TODO("Not yet implemented")
    override val isCursorAtTheStartOfTheLine: Boolean
        get() = TODO("Not yet implemented")
    override val isCursorAtTheFirstRow: Boolean
        get() = TODO("Not yet implemented")
    override val isCursorAtTheLastRow: Boolean
        get() = TODO("Not yet implemented")

    override fun moveCursorForward() {
        TODO("Not yet implemented")
    }

    override fun moveCursorBackward() {
        TODO("Not yet implemented")
    }

    override fun handleMouseEvents(
        eventType: MouseEventType,
        handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        TODO("Not yet implemented")
    }

    override fun processMouseEvents(
        eventType: MouseEventType,
        handler: (event: MouseEvent, phase: UIEventPhase) -> Unit
    ): Subscription {
        TODO("Not yet implemented")
    }

    override fun handleKeyboardEvents(
        eventType: KeyboardEventType,
        handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse
    ): Subscription {
        TODO("Not yet implemented")
    }

    override fun processKeyboardEvents(
        eventType: KeyboardEventType,
        handler: (event: KeyboardEvent, phase: UIEventPhase) -> Unit
    ): Subscription {
        TODO("Not yet implemented")
    }

    override fun dock(view: org.hexworks.zircon.api.view.View) {
        TODO("Not yet implemented")
    }

    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        TODO("Not yet implemented")
    }

    override fun stop(animation: InternalAnimation) {
        TODO("Not yet implemented")
    }

    override fun removeLayer(layer: Layer): Boolean {
        TODO("Not yet implemented")
    }

    override val renderables: List<Renderable>
        get() = TODO("Not yet implemented")

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        TODO("Not yet implemented")
    }

}

class KorgeTilegridView(val config: AppConfig) : View() {
    val tilegrid = KorgeTilegrid(config)
    override fun renderInternal(ctx: RenderContext) {
    }
}

class ZirconKorgeScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        val screen = KorgeTilegridView(AppConfig.newBuilder()
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .build()).addTo(this).tilegrid.toScreen()

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
    }
}

suspend fun main() = Korge {
    sceneContainer().changeTo({ ZirconKorgeScene() })
}
