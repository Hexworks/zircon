import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.application.appConfig
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.internal.util.CP437Utils
import kotlin.random.Random

suspend fun main() {
    var currIdx = 0
    val random = Random(13513516895)
    val tileSize = 8
    val gridCols = 1920 / tileSize
    val gridRows = 1080 / tileSize

    fun ANSITileColor.Companion.random(): ANSITileColor =
        ANSITileColor.values()[random.nextInt(0, ANSITileColor.values().size)]

    val app = Applications.createApplication(appConfig {
        size = Size.create(gridCols, gridRows)
        defaultTileset = BuiltInCP437TilesetResource.valueOf("REX_PAINT_${tileSize}X${tileSize}")
        debugMode = true
    })
    val grid = app.tileGrid

    println("grid size is: ${grid.size}")

    app.beforeRender {
        for (x in 0 until gridCols) {
            for (y in 1 until gridRows) {
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

    app.start()
}