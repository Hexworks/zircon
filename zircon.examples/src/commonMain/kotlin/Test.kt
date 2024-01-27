import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.component.builder.base.withPreferredContentSize
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.Symbols


suspend fun Application.test() = also {
    val screen = tileGrid.toScreen()

    screen.addComponent(buildTextArea {
        withPreferredContentSize {
            width = 20
            height = 10
        }
    })

    screen.display()
}