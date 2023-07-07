import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.util.CP437Utils
import kotlin.random.Random

var currIdx = 0
val random = Random(13513516895)
fun ANSITileColor.Companion.random(): ANSITileColor =
    ANSITileColor.values()[random.nextInt(0, ANSITileColor.values().size)]

fun Application.benchmark() = also {
    val grid = tileGrid

    beforeRender {
        for (x in 0 until grid.width) {
            for (y in 1 until grid.height) {
                grid.draw(
                    Tile.newBuilder()
                        .withCharacter(CP437Utils.convertCp437toUnicode(random.nextInt(0, 255)))
                        .withBackgroundColor(ANSITileColor.random())
                        .withForegroundColor(ANSITileColor.random())
                        .buildCharacterTile(), Position.create(x, y)
                )
            }
        }
        currIdx = if (currIdx == 0) 1 else 0
    }
}