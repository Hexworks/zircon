import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.TrueTypeFontResources
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.renderer.compose.composeAppConfig
import org.hexworks.zircon.renderer.compose.createComposeApplication
import java.awt.Toolkit

fun main() = runBlocking {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val screenWidth = screenSize.width   // e.g., 1920
    val screenHeight = screenSize.height // e.g., 1080
    val tileset = TrueTypeFontResources.ibmBios(16)
    val tileSize = tileset.width
    val gridCols = screenWidth / tileSize
    val gridRows = screenHeight / tileSize

    try {
        createComposeApplication(composeAppConfig {
            withSize {
                width = gridCols
                height = gridRows
            }
            defaultTileset = tileset
            debugMode = true
            title = "Zircon Compose Example"
        }).benchmark().start()
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
    println("Application ended")
}
