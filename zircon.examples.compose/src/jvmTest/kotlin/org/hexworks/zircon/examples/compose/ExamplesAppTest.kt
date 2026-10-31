package org.hexworks.zircon.examples.compose

import androidx.compose.ui.ImageComposeScene
import androidx.compose.ui.use
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.ExamplesSelection
import org.hexworks.zircon.examples.examplePackages
import org.hexworks.zircon.examples.examplesScreen
import org.hexworks.zircon.examples.gridSizeFor
import org.jetbrains.skia.EncodedImageFormat
import java.io.File
import kotlin.test.Test

class ExamplesAppTest {

    /** Builds every example once and renders each package's first one, so a broken example fails here. */
    @Test
    fun everyExampleBuildsAndEveryPackageRendersInAFullHdWindow() {
        installResources()
        val out = File("build/demo-renders").apply { mkdirs() }
        examplePackages.forEach { pkg ->
            val selection = ExamplesSelection.DEFAULT.copy(packageName = pkg.name, exampleName = pkg.examples.first().name)
            val shown = runBlocking {
                examplesScreen(selection, gridSizeFor(selection.tileset.resource, FULL_HD_WIDTH, FULL_HD_HEIGHT))
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
