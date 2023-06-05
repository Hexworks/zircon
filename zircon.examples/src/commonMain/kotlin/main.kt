import korlibs.event.Key
import korlibs.event.KeyEvent
import korlibs.event.MouseEvent
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.slice
import korlibs.image.color.Colors
import korlibs.image.format.readBitmap
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.Korge
import korlibs.korge.annotations.KorgeExperimental
import korlibs.korge.input.MouseEvents
import korlibs.korge.input.keys
import korlibs.korge.input.mouse
import korlibs.korge.input.onMouseDrag
import korlibs.korge.scene.Scene
import korlibs.korge.scene.sceneContainer
import korlibs.korge.view.SContainer
import korlibs.korge.view.renderableView
import korlibs.math.geom.Size
import korlibs.math.geom.SizeInt
import korlibs.math.geom.slice.splitInRows
import korlibs.math.geom.toFloat
import korlibs.math.geom.toInt
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
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.RenderableContainer
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.KORGE_CONTAINER
import org.hexworks.zircon.internal.tileset.impl.korge.toRGBA


suspend fun main() = Korge(virtualSize = Size(640, 400)) {
    sceneContainer().changeTo({ MyScene() })
}

class MyScene : ZirconKorgeScene(::zirconGame)

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
