import korlibs.io.file.std.openAsZip
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.loadResource

suspend fun main() {
    val tileSize = 16
    val gridCols = 1920 / tileSize
    val gridRows = 1080 / tileSize

    loadResource(Resource.create("rex_files/xptest.xp", ResourceType.PROJECT)).openAsZip()

    println("ok")

//    Applications.createApplication(appConfig {
//        size = Size.create(gridCols, gridRows)
//        defaultTileset = BuiltInCP437TilesetResource.valueOf("REX_PAINT_${tileSize}X${tileSize}")
//        debugMode = true
//    }).rex().start()

}