import korlibs.korge.Korge
import korlibs.korge.KorgeDisplayMode
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Utils
import kotlin.random.Random

const val SCREEN_WIDTH = 1920
const val SCREEN_HEIGHT = 1080
//const val SCREEN_WIDTH = 1280
//const val SCREEN_HEIGHT = 720
const val TILE_SIZE = 8
//const val TILE_SIZE = 16
const val GRID_WIDTH = SCREEN_WIDTH.div(TILE_SIZE)
const val GRID_HEIGHT = SCREEN_HEIGHT.div(TILE_SIZE)
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
//class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconGame)
class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconBenchmark)
//class MyScene : ZirconKorgeScene(GAME_SIZE, ::zirconImageDictionaryTilesetResources)

fun zirconImageDictionaryTilesetResources(app: Application, screen: Screen) {
}

fun zirconBenchmark(app: Application, screen: Screen) {
    var BENCHMARK_CURR_IDX = 0

    val BENCHMARK_RANDOM = Random(13513516895)

    val BENCHMARK_LAYER_WIDTH = GRID_WIDTH.div(2)
    val BENCHMARK_LAYER_HEIGHT = GRID_HEIGHT.div(2)
    val BENCHMARK_LAYER_COUNT = 20
    val BENCHMARK_LAYER_SIZE = org.hexworks.zircon.api.data.Size.create(BENCHMARK_LAYER_WIDTH, BENCHMARK_LAYER_HEIGHT)
    val BENCHMARK_FILLER = Tile.defaultTile().withCharacter('x')

    val BENCHMARK_LAYERS = (0..BENCHMARK_LAYER_COUNT).map {
        val imageLayer = DrawSurfaces.tileGraphicsBuilder()
            .withSize(BENCHMARK_LAYER_SIZE)
            .withTileset(CP437TilesetResources.rexPaint16x16())
            .build()
        BENCHMARK_LAYER_SIZE.fetchPositions().forEach {
            imageLayer.draw(BENCHMARK_FILLER, it)
        }
        LayerBuilder.newBuilder()
            .withOffset(
                Position.create(
                    x = BENCHMARK_RANDOM.nextInt(GRID_WIDTH - BENCHMARK_LAYER_WIDTH),
                    y = BENCHMARK_RANDOM.nextInt(GRID_HEIGHT - BENCHMARK_LAYER_HEIGHT)
                )
            )
            .withTileGraphics(imageLayer)
            .build()
    }

    fun ANSITileColor.Companion.random(): ANSITileColor =
        ANSITileColor.values()[BENCHMARK_RANDOM.nextInt(0, ANSITileColor.values().size)]

    app.beforeRender {
        //println("BEFORE RENDER")
        for (x in 0 until GRID_WIDTH) {
            for (y in 1 until GRID_HEIGHT) {
                screen.draw(
                    Tile.newBuilder()
                        .withCharacter(CP437Utils.convertCp437toUnicode(BENCHMARK_RANDOM.nextInt(0, 255)))
                        .withBackgroundColor(ANSITileColor.random())
                        .withForegroundColor(ANSITileColor.random())
                        .buildCharacterTile(), Position.create(x, y)
                )
            }
        }

        BENCHMARK_LAYERS.forEach {
            it.asInternalLayer().moveTo(
                Position.create(
                    x = BENCHMARK_RANDOM.nextInt(GRID_WIDTH - BENCHMARK_LAYER_WIDTH),
                    y = BENCHMARK_RANDOM.nextInt(GRID_HEIGHT - BENCHMARK_LAYER_HEIGHT)
                )
            )
        }
        BENCHMARK_CURR_IDX = if (BENCHMARK_CURR_IDX == 0) 1 else 0
        screen.display()
    }
}

fun zirconGame2(app: Application, screen: Screen) {
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

    val imageDictionary = ImageDictionaryTilesetResources.loadTilesetFromFilesystem("../zircon.jvm.examples/src/main/resources/image_dictionary")

    val imageTile = Tile.newBuilder()
        .withTileset(imageDictionary)
        .withName("hexworks_logo.png")
        .buildImageTile()
    screen.draw(imageTile, Position.create(10, 10))

    val ttf1 = TrueTypeFontResources.amstrad(16)
    val ttf2 = TrueTypeFontResources.vtech(16)

    screen.drawText(
        "HELLO WORLD!\nfrom amstrad!",
        Position.create(32, 24),
        StyleSet.defaultStyle()
            .withForegroundColor(TileColor.create(255, 0, 0))
            .withBackgroundColor(TileColor.create(0, 0, 255)),
        ttf1
    )

    screen.drawText(
        "HELLO WORLD!\nfrom vtech!",
        Position.create(32, 27),
        StyleSet.defaultStyle()
            .withForegroundColor(TileColor.create(255, 0, 0))
            .withBackgroundColor(TileColor.create(0, 0, 255)),
        ttf2
    )

    screen.display()
}

fun DrawSurface.drawText(
    text: String,
    drawPosition: Position,
    styleSet: StyleSet,
    tileSet: TilesetResource
) {
    var pos = drawPosition
    for (c in text) {
        if (c == '\n') {
            pos = Position.create(drawPosition.x, pos.y + 1)
            continue
        }
        draw(Tile.newBuilder()
            .withTileset(tileSet)
            .withCharacter(c)
            .withStyleSet(styleSet)
            .buildCharacterTile(), pos
        )
        pos = Position.create(pos.x + 1, pos.y)
    }
}

fun zirconGame(app: Application, screen: Screen) {
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
