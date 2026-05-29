import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.api.graphics.nerd.MaterialDesignGlyphs
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.renderer.korge.createKorgeApplication
import org.hexworks.zircon.renderer.korge.korgeAppConfig

suspend fun main() {
    val tileSize = 8
    val gridCols = 3440 / tileSize
    val gridRows = 1440 / tileSize

    try {
        createKorgeApplication(korgeAppConfig {
            withSize {
                width = gridCols
                height = gridRows
            }
            defaultTileset = TrueTypeFontResources.ibmBios(tileSize)
            debugMode = true
        }).benchmark().start()
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
    println("Application ended")
}