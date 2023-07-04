import korlibs.korge.Korge
import korlibs.korge.KorgeDisplayMode
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Utils
import kotlin.random.Random

//fun zirconBenchmark(app: Application, screen: Screen) {
//    var BENCHMARK_CURR_IDX = 0
//    val BENCHMARK_RANDOM = Random(13513516895)
//
//    fun ANSITileColor.Companion.random(): ANSITileColor =
//        ANSITileColor.values()[BENCHMARK_RANDOM.nextInt(0, ANSITileColor.values().size)]
//
//    app.beforeRender {
//        //println("BEFORE RENDER")
//        for (x in 0 until GRID_WIDTH) {
//            for (y in 1 until GRID_HEIGHT) {
//                screen.draw(
//                    Tile.newBuilder()
//                        .withCharacter(CP437Utils.convertCp437toUnicode(BENCHMARK_RANDOM.nextInt(0, 255)))
//                        .withBackgroundColor(ANSITileColor.random())
//                        .withForegroundColor(ANSITileColor.random())
//                        .buildCharacterTile(), Position.create(x, y)
//                )
//            }
//        }
//        BENCHMARK_CURR_IDX = if (BENCHMARK_CURR_IDX == 0) 1 else 0
//        screen.display()
//    }
//}
