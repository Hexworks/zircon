import korlibs.korge.Korge
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.screen.Screen


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
                    //this.withComponentStyleSet(ComponentStyleSetBuilder.newBuilder().withDefaultStyle(StyleSet.defaultStyle().withModifiers(SimpleModifiers.Blink)).build())
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
