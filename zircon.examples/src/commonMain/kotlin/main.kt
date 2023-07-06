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
    val gridWidth = 1920 / tileSize
    val gridHeight = 1080 / tileSize

    fun ANSITileColor.Companion.random(): ANSITileColor =
        ANSITileColor.values()[random.nextInt(0, ANSITileColor.values().size)]

    val app = Applications.createApplication(appConfig {
        size = Size.create(gridWidth, gridHeight)
        defaultTileset = BuiltInCP437TilesetResource.valueOf("REX_PAINT_${tileSize}X${tileSize}")
        debugMode = true
    })
    val grid = app.tileGrid

    app.beforeRender {
        for (x in 0 until gridWidth) {
            for (y in 1 until gridHeight) {
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