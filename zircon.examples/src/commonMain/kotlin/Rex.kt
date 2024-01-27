import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.loadREXFile
import org.hexworks.zircon.api.resource.ResourceType.PROJECT

suspend fun Application.rex() = also {
    loadREXFile("test/xptest.xp", PROJECT).toLayerList(tileGrid.tileset).onEach {
        tileGrid.addLayer(it)
    }

}