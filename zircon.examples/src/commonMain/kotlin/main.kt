import korlibs.korge.Korge
import korlibs.korge.KorgeDisplayMode
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.screen.Screen

val GAME_SIZE = Size(640, 400)

suspend fun main() = Korge(
    virtualSize = GAME_SIZE,
    displayMode = KorgeDisplayMode.TOP_LEFT_NO_CLIP
) {
    sceneContainer().changeTo({ MyScene() })
}

class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconGame2)
//class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconGame)

fun zirconGame2(screen: Screen) {
    for (y in 0 until 20) {
        for (x in 0 until 32) {
            screen.draw(
                Tile.createCharacterTile('b' + x + y * 2, StyleSet.defaultStyle()
                .withForegroundColor(TileColor.create(x * 8, 0, 0, 255))
                .withBackgroundColor(if (x > 16) TileColor.create(0, x * 8, y * 8, 255) else TileColor.transparent())
                .withModifiers(buildSet {
                    if ((x + y) % 4 == 0) add(SimpleModifiers.Blink)
                    if ((x + y) % 5 == 1) add(SimpleModifiers.CrossedOut)
                    if ((x + y) % 6 == 2) add(SimpleModifiers.Hidden)
                    if ((x + y) % 7 == 3) add(SimpleModifiers.HorizontalFlip)
                    if ((x + y) % 8 == 4) add(SimpleModifiers.VerticalFlip)
                    if ((x + y) % 9 == 5) add(SimpleModifiers.Underline)
                })
            ), Position.create(x, y))
        }
    }
    screen.display()
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
