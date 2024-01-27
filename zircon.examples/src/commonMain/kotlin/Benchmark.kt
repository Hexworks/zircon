import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.util.convertCp437toUnicode
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
                    characterTile {
                        +random.nextInt(0, 255).convertCp437toUnicode()
                        withStyleSet {
                            backgroundColor = ANSITileColor.random()
                            foregroundColor = ANSITileColor.random()
                        }

                    }, Position.create(x, y)
                )
            }
        }
        currIdx = if (currIdx == 0) 1 else 0
    }
}