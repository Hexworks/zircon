import korlibs.korge.Korge
import korlibs.korge.KorgeDisplayMode
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import kotlin.random.Random


const val SCREEN_WIDTH = 1920
const val SCREEN_HEIGHT = 1080
const val TILE_SIZE = 8
const val GRID_WIDTH = 1920.div(TILE_SIZE)
const val GRID_HEIGHT = 1080.div(TILE_SIZE)
val TILESET = BuiltInCP437TilesetResource.values().first { it.width == TILE_SIZE && it.tilesetName == "rex_paint" }
val GAME_SIZE = Size(SCREEN_WIDTH, SCREEN_HEIGHT)

suspend fun main() = Korge(
    virtualSize = GAME_SIZE,
    windowSize = GAME_SIZE,
    displayMode = KorgeDisplayMode.TOP_LEFT_NO_CLIP
) {
    sceneContainer().changeTo({ MyScene() })
}

//class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconGame2)
class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconGame)

fun zirconGame2(screen: Screen) {
    for (y in 0 until 20) {
        for (x in 0 until 32) {
            screen.draw(
                Tile.createCharacterTile(
                    'b' + x + y * 2, StyleSet.defaultStyle()
                        .withForegroundColor(TileColor.create(x * 8, 0, 0, 255))
                        .withBackgroundColor(
                            if (x > 16) TileColor.create(
                                0,
                                x * 8,
                                y * 8,
                                255
                            ) else TileColor.transparent()
                        )
                        .withModifiers(buildSet {
                            if ((x + y) % 4 == 0) add(SimpleModifiers.Blink)
                            if ((x + y) % 5 == 1) add(SimpleModifiers.CrossedOut)
                            if ((x + y) % 6 == 2) add(SimpleModifiers.Hidden)
                            if ((x + y) % 7 == 3) add(SimpleModifiers.HorizontalFlip)
                            if ((x + y) % 8 == 4) add(SimpleModifiers.VerticalFlip)
                            if ((x + y) % 9 == 5) add(SimpleModifiers.Underline)
                        })
                ), Position.create(x, y)
            )
        }
    }

    val NAMES = listOf(
        "Giant ant",
        "Killer bee",
        "Fire ant",
        "Werewolf",
        "Dingo",
        "Hell hound pup",
        "Tiger",
        "Gremlin"
    )

    val RANDOM = Random

    val GRID_WIDTH = 50
    val GRID_HEIGHT = 24
    val TILESET = GraphicalTilesetResources.nethack16x16()


    for (row in 8 until GRID_HEIGHT) {
        for (col in 0 until GRID_WIDTH) {
            val name =
                NAMES[RANDOM.nextInt(NAMES.size)]
            screen.draw(
                Tile.newBuilder()
                    .withName(name)
                    .withTileset(TILESET)
                    .buildGraphicalTile(),
                Position.create(col, row)
            )
        }
    }


    screen.display()
}

fun zirconGame(screen: Screen) {
    screen.apply {
        display()
//        addComponent(buildHbox {
//
//            spacing = 1
//            decorations = listOf(margin(1))
//
//            vbox {
//                header {
//                    +"Hello, KorGE!"
//                }
//                paragraph {
//                    +"This is a paragraph"
//                }
//                button {
//                    +"Yep"
//                }
//            }
//
//            vbox {
//                header {
//                    +"Another box"
//                }
//                paragraph {
//                    +"This is a paragraph"
//                }
//                button {
//                    +"Nope"
//                }
//            }
//
//        })
    }
}
