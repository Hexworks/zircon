package org.hexworks.zircon.examples

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.hexworks.zircon.api.component.Component
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.Resources
import java.io.File
import kotlin.test.Test

@OptIn(ExamplesTestApi::class)
class ExamplesScreenTest {

    init {
        // The data files next to this module; the launchers have their own loaders.
        Resources.loader = ResourceLoader { resource: Resource ->
            File("resources/files/" + resource.path.trimStart('/')).readBytes()
        }
    }

    @Test
    fun benchmarkTilesetFillsAFullHdWindow() {
        gridSizeFor(Size(8, 8), 1920, 1080) shouldBe Size(240, 135)
        gridSizeFor(Size(16, 16), 800, 600) shouldBe MIN_SIZE
    }

    @Test
    fun selectingAGroupShowsItsFirstExample() {
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        shown.tabBar.selectedHorizontalKey shouldBe "animations"
        shown.tabBar.selectedVerticalKey shouldBe "Skull"
        shown.tabBar.selectedHorizontalKey = "dialogs"
        shown.tabBar.selectedVerticalKey shouldBe "Dialog"
    }

    @Test
    fun aRebuiltScreenKeepsTheSelection() {
        val first = runBlocking { examplesScreen(MIN_SIZE) }
        first.tabBar.selectedHorizontalKey = "dialogs"
        first.tabBar.selectedVerticalKey = "Nested modals"
        first.themeProperty.value = THEMES.first { it.name == "Tron" }
        first.tilesetProperty.value = tilesetsFor(Size(16, 16)).first { it.title == "VGA 8×16" || it.title == "Zilk 16×16" }
        val second = runBlocking { examplesScreen(MIN_SIZE, previous = first) }
        second.tabBar.selectedHorizontalKey shouldBe "dialogs"
        second.tabBar.selectedVerticalKey shouldBe "Nested modals"
        second.themeProperty.value.name shouldBe "Tron"
        second.grid.tileset.title shouldBe "Zilk 16×16"
        second.grid.tileset shouldBe second.tilesetProperty.value
    }

    @Test
    fun aNewTileSizeStartsFromTheFirstTilesetOfThatSize() {
        val first = runBlocking { examplesScreen(MIN_SIZE) }
        (first.tileSizeProperty as Property<Size>).value = Size(8, 16)
        val second = runBlocking { examplesScreen(MIN_SIZE, previous = first) }
        second.grid.tileset.size shouldBe Size(8, 16)
        second.grid.tileset shouldBe tilesetsFor(Size(8, 16)).first()
    }

    @Test
    fun startAtOpensThatExample() {
        val shown = runBlocking { examplesScreen(MIN_SIZE, startAt = "Nested modals") }
        shown.tabBar.selectedHorizontalKey shouldBe "dialogs"
        shown.tabBar.selectedVerticalKey shouldBe "Nested modals"
    }

    @Test
    fun aTilesetChangeReachesTheGridAndTheComponents() {
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        val zilk = tilesetsFor(Size(16, 16)).first { it.title == "Zilk 16×16" }
        shown.tilesetProperty.value = zilk
        shown.grid.tileset shouldBe zilk
        // The displayed screen delegates the grid's renderables to its own: the components among them must follow.
        val components = (shown.grid as InternalTileGrid).renderables.filterIsInstance<Component>()
        components.size shouldBeGreaterThan 10
        components.map { it.tileset }.distinct() shouldBe listOf(zilk)
    }

    @Test
    fun textOnlyLeavesOutGraphicalExamples() {
        val shown = runBlocking { examplesScreen(MIN_SIZE, isTerminal = true) }
        shown.packages.map { it.name } shouldContain "animations"
        (shown.packages.any { it.name == "game" } && EXAMPLE_GROUPS.first { it.name == "game" }.examples.all { !it.textOnly }) shouldBe false
    }

    @Test
    fun everyExampleBuildsInTheSmallestGrid() {
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        EXAMPLE_GROUPS.forEach { pkg ->
            shown.tabBar.selectedHorizontalKey = pkg.name
            pkg.examples.forEach { example ->
                shown.tabBar.selectedVerticalKey = example.name
                shown.onFrame()
            }
        }
        shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
    }
}
