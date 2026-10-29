package org.hexworks.zircon.examples.compose

import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.use
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.demos
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

class ExamplesAppTest {

    @Test
    fun everyDemoRendersInAFullHdWindow() {
        installResources()
        val out = File("build/demo-renders").apply { mkdirs() }
        demos.forEach { demo ->
            val shown = runBlocking { examplesScreen(demo, gridSizeFor(demo.tileset, FULL_HD_WIDTH, FULL_HD_HEIGHT)) {} }
            ImageComposeScene(width = FULL_HD_WIDTH, height = FULL_HD_HEIGHT) {
                TileGridView(shown.grid, beforeFrame = shown.onFrame)
            }.use { scene ->
                var png: ByteArray? = null
                repeat(10) { frame ->
                    val image = scene.render(nanoTime = frame * 16_000_000L)
                    if (frame == 9) png = image.encodeToData(EncodedImageFormat.PNG)?.bytes
                }
                File(out, "${demo.name.lowercase().replace(' ', '-')}.png").writeBytes(checkNotNull(png))
            }
        }
    }

    companion object {
        const val FULL_HD_WIDTH = 1920
        const val FULL_HD_HEIGHT = 1080
    }
}
