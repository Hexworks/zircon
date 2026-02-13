import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.util.convertCp437toUnicode
import kotlin.random.Random

private var currIdx = 0
private val random = Random(13513516895)

private fun ANSIColor.Companion.random(): Color =
    DefaultAnsiPalette[ANSIColor.entries[random.nextInt(0, ANSIColor.entries.size)]]

/**
 * Benchmark extension that fills the grid with random characters and colors
 * on each frame to stress-test the renderer.
 */
fun Application.benchmark() = also {
    val grid = tileGrid

    beforeRender {
        for (x in 0 until grid.width) {
            for (y in 1 until grid.height) {
                grid.draw(
                    characterTile {
                        +random.nextInt(0, 255).convertCp437toUnicode()
                        withStyleSet {
                            backgroundColor = ANSIColor.random()
                            foregroundColor = ANSIColor.random()
                        }
                    }, Position.create(x, y)
                )
            }
        }
        currIdx = if (currIdx == 0) 1 else 0
    }
}
