import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.application.appConfig
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

suspend fun main() {
    val tileSize = 16
    val gridCols = 1920 / tileSize
    val gridRows = 1080 / tileSize

    Applications.createApplication(appConfig {
        size = Size.create(gridCols, gridRows)
        defaultTileset = BuiltInCP437TilesetResource.valueOf("REX_PAINT_${tileSize}X${tileSize}")
        debugMode = true
    }).components().start()

}