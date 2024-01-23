import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.api.createApplication
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

suspend fun main() {
    val tileSize = 16
    val gridCols = 1920 / tileSize
    val gridRows = 1080 / tileSize

    createApplication(appConfig {
        withSize {
            width = gridCols
            height = gridRows
        }
        defaultTileset = BuiltInCP437TilesetResource.valueOf("REX_PAINT_${tileSize}X${tileSize}")
        debugMode = true
    }).rex().start()

}