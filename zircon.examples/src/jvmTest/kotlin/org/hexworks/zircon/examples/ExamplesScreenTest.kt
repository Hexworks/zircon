package org.hexworks.zircon.examples

import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.use
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.compose.ComposeResourceLoader
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.resources.Res
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

class ExamplesScreenTest {

    @Test
    fun benchmarkFillsAFullHdWindowWith8x8Tiles() {
        val shown = runBlocking { examplesScreen(benchmarkDemo, FULL_HD_WIDTH, FULL_HD_HEIGHT) {} }
        shown.grid.size shouldBe Size(240, 135)
    }

    @Test
    fun selectingAnotherTabReportsIt() {
        var selected: Demo? = null
        val shown = runBlocking { examplesScreen(componentsDemo, FULL_HD_WIDTH, FULL_HD_HEIGHT) { selected = it } }
        shown.tabBar.selectedKey = benchmarkDemo.name
        selected shouldBe benchmarkDemo
    }

    @Test
    fun everyDemoRendersInAFullHdWindow() {
        installResources()
        val out = File("build/demo-renders").apply { mkdirs() }
        demos.forEach { demo ->
            val shown = runBlocking { examplesScreen(demo, FULL_HD_WIDTH, FULL_HD_HEIGHT) {} }
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

    private fun installResources() {
        ComposeResourceLoader.install()
        ComposeResourceLoader.addProjectResources { path -> Res.readBytes(path) }
    }

    companion object {
        const val FULL_HD_WIDTH = 1920
        const val FULL_HD_HEIGHT = 1080
    }
}
