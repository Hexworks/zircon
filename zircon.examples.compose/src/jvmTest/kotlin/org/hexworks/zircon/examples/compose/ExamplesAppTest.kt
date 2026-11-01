package org.hexworks.zircon.examples.compose

import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.use
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.api.builder.grid.DEFAULT_TILESET
import org.hexworks.zircon.examples.EXAMPLE_GROUPS
import org.hexworks.zircon.examples.ExamplesTestApi
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

@OptIn(ExamplesTestApi::class)
class ExamplesAppTest {

    /** Builds every example once and renders each package's first one, so a broken example fails here. */
    @Test
    fun everyExampleBuildsAndEveryPackageRendersInAFullHdWindow() {
        installResources()
        val out = File("build/demo-renders").apply { mkdirs() }
        EXAMPLE_GROUPS.forEach { pkg ->
            val shown = runBlocking {
                examplesScreen(gridSizeFor(DEFAULT_TILESET.size, FULL_HD_WIDTH, FULL_HD_HEIGHT), startAt = pkg.examples.first().name)
            }
            pkg.examples.forEach { shown.tabBar.selectedVerticalKey = it.name }
            shown.tabBar.selectedVerticalKey = pkg.examples.first().name
            shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
            shown.runtimeErrors.map { (source, error) -> "$source: $error" } shouldBe emptyList()
            ImageComposeScene(width = FULL_HD_WIDTH, height = FULL_HD_HEIGHT) {
                TileGridView(shown.grid, beforeFrame = shown.onFrame)
            }.use { scene ->
                var png: ByteArray? = null
                repeat(10) { frame ->
                    val image = scene.render(nanoTime = frame * 16_000_000L)
                    if (frame == 9) png = image.encodeToData(EncodedImageFormat.PNG)?.bytes
                }
                File(out, "${pkg.name.replace('+', '-')}.png").writeBytes(checkNotNull(png))
            }
        }
    }

    companion object {
        const val FULL_HD_WIDTH = 1920
        const val FULL_HD_HEIGHT = 1080
    }
}
