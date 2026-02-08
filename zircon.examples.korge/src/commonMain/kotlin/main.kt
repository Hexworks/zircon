import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.renderer.korge.createKorgeApplication

suspend fun main() {
    val tileSize = 20
    val gridCols = 1920 / tileSize / 2
    val gridRows = 1080 / tileSize / 2

    try {
        createKorgeApplication(appConfig {
            withSize {
                width = gridCols
                height = gridRows
            }
            defaultTileset = CP437TilesetResources.rexPaint20x20()
            debugMode = true
        }).benchmark().start()
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
    println("Application ended")
}