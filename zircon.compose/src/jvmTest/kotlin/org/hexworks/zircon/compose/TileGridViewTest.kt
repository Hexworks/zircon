package org.hexworks.zircon.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.use
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.jetbrains.skia.EncodedImageFormat
import kotlin.test.Test

/**
 * Drives the real composable headlessly: composition, the frame loop and the canvas draw.
 */
@OptIn(ExperimentalComposeUiApi::class)
class TileGridViewTest {

    @Test
    fun composableRendersTheGridAfterAFewFrames() {
        val grid = createTileGrid(
            appConfig {
                size = Size(24, 6)
                defaultTileset = CP437TilesetResources.wanderlust16x16()
            }
        )
        val screen = grid.toScreen()
        screen.theme = ColorThemes.arc()
        val box = vBox { preferredSize = Size(24, 6) }
        screen.addComponent(box)
        box.addComponent(header { +"TileGridView" })
        box.addComponent(button { +"Press me" })
        screen.display()

        ImageComposeScene(width = grid.widthInPixels, height = grid.heightInPixels) {
            TileGridView(grid)
        }.use { scene ->
            // A few frames: tileset loading happens in the first ones.
            var png: ByteArray? = null
            repeat(10) { frame ->
                val image = scene.render(nanoTime = frame * 16_000_000L)
                if (frame == 9) png = image.encodeToData(EncodedImageFormat.PNG)?.bytes
            }
            assertMatchesGolden("composable-wanderlust", checkNotNull(png))
        }
    }
}
