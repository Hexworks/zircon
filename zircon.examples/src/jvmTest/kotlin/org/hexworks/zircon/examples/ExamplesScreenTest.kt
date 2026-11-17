package org.hexworks.zircon.examples

import org.hexworks.zircon.internal.component.InternalComponent
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.ints.shouldBeGreaterThan
import org.hexworks.zircon.api.component.Component
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.api.Palettes
import org.hexworks.zircon.api.component.theme.KanagawaVariant
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceLoader
import org.hexworks.zircon.api.resource.Resources
import java.io.File
import kotlin.test.Test
import io.kotest.matchers.shouldNotBe
import org.hexworks.zircon.internal.fragment.impl.DefaultMatrixTabBar
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.SystemTheme
import org.hexworks.zircon.api.component.theme.SystemThemeDetector
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.config.ZirconDefaults
import kotlin.test.AfterTest

@OptIn(ExamplesTestApi::class)
class ExamplesScreenTest {

    init {
        // The data files next to this module; the launchers have their own loaders.
        Resources.loader = ResourceLoader { resource: Resource ->
            File("resources/files/" + resource.path.trimStart('/')).readBytes()
        }
    }

    private val originalDefaults = ZirconDefaults.current

    @AfterTest
    fun tearDown() {
        ZirconDefaults.current = originalDefaults
        SystemTheme.reset()
    }

    private fun detectorReports(mode: ThemeMode) {
        ZirconDefaults.current = originalDefaults.copy(systemThemeDetector = SystemThemeDetector.fixed(mode))
        SystemTheme.refresh()
    }

    @Test
    fun selectingSystemShowsTheFamilyOfTheLastPaletteInTheDesktopsMode() {
        detectorReports(ThemeMode.LIGHT)
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        shown.paletteChoiceProperty.value shouldBe PaletteChoice.Fixed(DEFAULT_PALETTE)
        shown.paletteChoiceProperty.value = PaletteChoice.System
        shown.paletteProperty.value.name shouldBe Palettes.catppuccin(ThemeMode.LIGHT).name
        detectorReports(ThemeMode.DARK)
        shown.paletteProperty.value.name shouldBe Palettes.catppuccin(ThemeMode.DARK).name
        // Still following: the selector keeps saying System.
        shown.paletteChoiceProperty.value shouldBe PaletteChoice.System
    }

    @Test
    fun aHostPaletteWinsWhileFollowing() {
        detectorReports(ThemeMode.DARK)
        val shown = runBlocking { examplesScreen(MIN_SIZE, isTerminal = true) }
        // A terminal starts on System.
        shown.paletteChoiceProperty.value shouldBe PaletteChoice.System
        val terminal = Palettes.fromAnsi(Color.fromString("#ffffff"), Color.fromString("#000000"), TerminalColors16)
        SystemTheme.update(mode = ThemeMode.DARK, palette = terminal)
        shown.paletteProperty.value shouldBe terminal
        shown.paletteChoiceProperty.value shouldBe PaletteChoice.System
    }

    @Test
    fun selectingAPaletteStopsFollowing() {
        detectorReports(ThemeMode.DARK)
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        shown.paletteChoiceProperty.value = PaletteChoice.System
        val dragon = Palettes.kanagawa(KanagawaVariant.DRAGON)
        shown.paletteChoiceProperty.value = PaletteChoice.Fixed(dragon)
        shown.paletteProperty.value shouldBe dragon
        detectorReports(ThemeMode.LIGHT)
        shown.paletteProperty.value shouldBe dragon
        // System again follows the family picked last: Kanagawa.
        shown.paletteChoiceProperty.value = PaletteChoice.System
        shown.paletteProperty.value.name shouldBe Palettes.kanagawa(ThemeMode.LIGHT).name
    }

    @Test
    fun aPaletteSetByAnExampleShowsInTheSelectorAndStopsFollowing() {
        detectorReports(ThemeMode.DARK)
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        shown.paletteChoiceProperty.value = PaletteChoice.System
        val neo = Palettes.neobrutalism(ThemeMode.LIGHT)
        shown.paletteProperty.value = neo
        shown.paletteChoiceProperty.value shouldBe PaletteChoice.Fixed(neo)
        detectorReports(ThemeMode.LIGHT)
        shown.paletteProperty.value shouldBe neo
    }

    @Test
    fun aRebuiltScreenKeepsFollowing() {
        detectorReports(ThemeMode.DARK)
        val first = runBlocking { examplesScreen(MIN_SIZE) }
        first.paletteChoiceProperty.value = PaletteChoice.System
        val second = runBlocking { examplesScreen(MIN_SIZE, previous = first) }
        second.paletteChoiceProperty.value shouldBe PaletteChoice.System
        detectorReports(ThemeMode.LIGHT)
        second.paletteProperty.value.name shouldBe Palettes.catppuccin(ThemeMode.LIGHT).name
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
        val dragon = Palettes.kanagawa(KanagawaVariant.DRAGON)
        first.paletteProperty.value = dragon
        first.tilesetProperty.value = tilesetsFor(Size(16, 16)).first { it.title == "VGA 8x16" || it.title == "Zilk 16x16" }
        val second = runBlocking { examplesScreen(MIN_SIZE, previous = first) }
        second.tabBar.selectedHorizontalKey shouldBe "dialogs"
        second.tabBar.selectedVerticalKey shouldBe "Nested modals"
        second.paletteProperty.value.name shouldBe dragon.name
        second.grid.tileset.title shouldBe "Zilk 16x16"
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
        val zilk = tilesetsFor(Size(16, 16)).first { it.title == "Zilk 16x16" }
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

    @Test
    fun everyExampleSurvivesTheNeobrutalismThemeAndBackInTheSmallestGrid() {
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        // Show everything under the stock theme, then switch the shape app-wide and back:
        // every built component is re-themed in place, none may throw or fail to build.
        EXAMPLE_GROUPS.forEach { pkg ->
            shown.tabBar.selectedHorizontalKey = pkg.name
            pkg.examples.forEach { example -> shown.tabBar.selectedVerticalKey = example.name }
        }
        COMPONENT_THEMES.forEach { theme ->
            shown.themeProperty.value = theme
            EXAMPLE_GROUPS.forEach { pkg ->
                shown.tabBar.selectedHorizontalKey = pkg.name
                pkg.examples.forEach { example ->
                    shown.tabBar.selectedVerticalKey = example.name
                    // The content panel must keep taking the demos under the heavy frames.
                    (shown.tabBar as DefaultMatrixTabBar).shownContent shouldNotBe null
                    ((shown.tabBar as DefaultMatrixTabBar).shownContent as InternalComponent).hasParent.value shouldBe true
                    shown.onFrame()
                    (shown.grid as InternalTileGrid).renderables.forEach { it.render() }
                }
            }
        }
        shown.themeProperty.value = COMPONENT_THEMES.first()
        shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
        shown.runtimeErrors.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
    }

    @Test
    fun anExampleFirstOpenedUnderTheNeobrutalismThemeFitsAndSitsAtTheContentOrigin() {
        val shown = runBlocking { examplesScreen(MIN_SIZE) }
        val tabBar = shown.tabBar as DefaultMatrixTabBar
        // The demos are built on first visit: switch the shape before visiting anything, then
        // walk every cell under Neobrutalism, the default and Neobrutalism again.
        val themes = COMPONENT_THEMES.drop(1) + COMPONENT_THEMES.first() + COMPONENT_THEMES.drop(1)
        themes.forEach { theme ->
            shown.themeProperty.value = theme
            EXAMPLE_GROUPS.forEach { pkg ->
                shown.tabBar.selectedHorizontalKey = pkg.name
                pkg.examples.forEach { example ->
                    shown.tabBar.selectedVerticalKey = example.name
                    val placeholder = tabBar.shownContent as InternalComponent
                    placeholder.hasParent.value shouldBe true
                    // The placeholder is layout only: the example root fills it from its origin.
                    val root = (placeholder as org.hexworks.zircon.api.component.Container).children.single()
                    root.position shouldBe placeholder.position
                    root.size shouldBe placeholder.size
                    shown.onFrame()
                    (shown.grid as InternalTileGrid).renderables.forEach { it.render() }
                }
            }
        }
        shown.buildFailures.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
        shown.runtimeErrors.map { (name, error) -> "$name: ${error.message}" } shouldBe emptyList()
    }
}

/** Sixteen flat grey slots: enough for a palette the tests can tell apart from the bundled ones. */
private val TerminalColors16: List<Color> = List(16) { Color.create(it * 16, it * 16, it * 16) }
