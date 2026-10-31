package org.hexworks.zircon.examples

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.Resources
import java.io.File
import kotlin.test.Test

class ExamplesScreenTest {

    init {
        // The data files next to this module; the launchers have their own loaders.
        Resources.loader = ResourceLoader { resource: Resource ->
            File("resources/files/" + resource.path.trimStart('/')).readBytes()
        }
    }

    @Test
    fun benchmarkTilesetFillsAFullHdWindow() {
        gridSizeFor(CP437TilesetResources.rexPaint8x8(), 1920, 1080) shouldBe Size(240, 135)
        gridSizeFor(CP437TilesetResources.rexPaint16x16(), 800, 600) shouldBe MIN_SIZE
    }

    @Test
    fun selectingTabsUpdatesTheSelection() {
        val shown = runBlocking { examplesScreen(ExamplesSelection.DEFAULT, MIN_SIZE) }
        shown.selection.packageName shouldBe "animations"
        shown.selection.exampleName shouldBe "Skull"
        shown.tabBar.selectedHorizontalKey = "dialogs"
        shown.selection.packageName shouldBe "dialogs"
        shown.selection.exampleName shouldBe "Dialog"
        shown.tabBar.selectedVerticalKey = "Nested modals"
        shown.selection.exampleName shouldBe "Nested modals"
    }

    @Test
    fun textOnlyLeavesOutGraphicalExamples() {
        val shown = runBlocking { examplesScreen(ExamplesSelection.DEFAULT, MIN_SIZE, textOnly = true) }
        shown.packages.map { it.name } shouldContain "animations"
        (shown.packages.any { it.name == "game" } && examplePackages.first { it.name == "game" }.examples.all { !it.textOnly }) shouldBe false
    }

    @Test
    fun everyExampleBuildsInTheSmallestGrid() {
        val shown = runBlocking { examplesScreen(ExamplesSelection.DEFAULT, MIN_SIZE) }
        examplePackages.forEach { pkg ->
            shown.tabBar.selectedHorizontalKey = pkg.name
            pkg.examples.forEach { example ->
                shown.tabBar.selectedVerticalKey = example.name
                shown.onFrame()
            }
        }
        shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
    }
}
