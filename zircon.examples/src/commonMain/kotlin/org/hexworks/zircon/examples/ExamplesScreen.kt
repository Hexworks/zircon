package org.hexworks.zircon.examples

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.builder.fragment.selector
import org.hexworks.zircon.api.builder.fragment.verticalTabBar
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.loadAnimation
import org.hexworks.zircon.api.loadREXFile
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import kotlin.time.TimeSource

/** What the app shows: the selected package and example, and the tileset and theme. */
public data class ExamplesSelection(
    val packageName: String,
    val exampleName: String,
    val tileset: NamedTileset,
    val theme: ColorTheme,
) {
    public companion object {
        public val DEFAULT: ExamplesSelection = ExamplesSelection(
            packageName = examplePackages.first().name,
            exampleName = examplePackages.first().examples.first().name,
            tileset = TILESETS.first { it.name == "rogue yun 16x16" },
            theme = THEMES.first { it.name == "Nord" },
        )
    }
}

/**
 * A built examples screen: the [grid] to render, the live [selection], and [onFrame] to
 * call before each frame.
 */
public class ExamplesScreen(
    public val grid: TileGrid,
    public val screen: Screen,
    public val selectionProperty: Property<ExamplesSelection>,
    public val packageBar: TabBar,
    public val exampleBars: Map<String, TabBar>,
    /** Examples whose build threw, as "package / example" to the error; the app shows the message instead. */
    public val buildFailures: List<Pair<String, Throwable>>,
    /** Errors Zircon contained while running, as source to the error; the latest shows in the footer. */
    public val runtimeErrors: List<Pair<String, Throwable>>,
    public val onFrame: () -> Unit,
) {
    public val selection: ExamplesSelection get() = selectionProperty.value
}

/**
 * The grid size that fills [widthPx] x [heightPx] with tiles of [tileset], at least
 * [MIN_SIZE].
 */
public fun gridSizeFor(tileset: TilesetResource, widthPx: Int, heightPx: Int): Size = Size(
    maxOf(widthPx / tileset.width, MIN_SIZE.width),
    maxOf(heightPx / tileset.height, MIN_SIZE.height),
)

/**
 * Builds the examples app into a grid of [size]: theme (and, when [onTilesetChange] is
 * given, tileset) selectors on top, the packages as horizontal tabs below, each package's
 * examples as vertical tabs, and an fps counter in the corner. Examples are built the
 * first time they are shown. Changing the theme applies at once; changing the tileset
 * calls [onTilesetChange] with the new selection and the launcher rebuilds the grid.
 * With [textOnly] the examples that need graphical tiles are left out.
 */
public suspend fun examplesScreen(
    selection: ExamplesSelection,
    size: Size,
    textOnly: Boolean = false,
    onTilesetChange: ((ExamplesSelection) -> Unit)? = null,
): ExamplesScreen {
    require(size.width >= MIN_SIZE.width && size.height >= MIN_SIZE.height) {
        "The examples need at least ${MIN_SIZE.width}x${MIN_SIZE.height} cells, got ${size.width}x${size.height}"
    }
    val packages = examplePackages
        .map { pkg -> ExamplePackage(pkg.name, pkg.examples.filter { !textOnly || it.textOnly }) }
        .filter { it.examples.isNotEmpty() }
    val grid = tileGrid {
        this.size = size
        this.tileset = selection.tileset.resource
    }
    val screen = grid.toScreen()
    screen.theme = selection.theme
    val assets = loadAssets(grid.tileset)

    val selectionProperty = selection.toProperty()
    fun update(fn: ExamplesSelection.() -> ExamplesSelection) {
        selectionProperty.value = selectionProperty.value.fn()
    }

    // Header: selectors.
    val header = hBox {
        preferredSize = Size(size.width, HEADER_HEIGHT)
        componentRenderer = NoOpComponentRenderer()
        decorations { +margin(1, 1) }
        if (onTilesetChange != null) {
            label { +"Tileset" }
            selector<NamedTileset> {
                width = SELECTOR_WIDTH
                valueList = TILESETS
                defaultSelected = selection.tileset
            }.selectedProperty.onChange { (_, tileset) ->
                if (tileset != null && tileset != selectionProperty.value.tileset) {
                    update { copy(tileset = tileset) }
                    onTilesetChange(selectionProperty.value)
                }
            }
            label { +"  " }
        }
        label { +"Theme" }
        selector<ColorTheme> {
            width = SELECTOR_WIDTH
            valueList = THEMES
            defaultSelected = selection.theme
            toStringMethod = { it.name }
        }.selectedProperty.onChange { (_, theme) ->
            if (theme != null) {
                screen.theme = theme
                update { copy(theme = theme) }
            }
        }
    }
    screen.addComponent(header)

    // Body: packages across, examples down, the example's content to the right.
    val bodySize = Size(size.width, size.height - HEADER_HEIGHT)
    // Named apart from the builders' own `contentSize`, which would shadow it inside their lambdas.
    val exampleContentSize = Size(size.width - EXAMPLE_TAB_WIDTH, bodySize.height - 3)
    val context = ExampleContext(screen, exampleContentSize, assets)

    val views = HashMap<Pair<String, String>, ExampleView>()
    val placeholders = HashMap<Pair<String, String>, Panel>()
    val buildFailures = ArrayList<Pair<String, Throwable>>()
    var shownKey: Pair<String, String>? = null
    var shownLayers: List<LayerHandle> = emptyList()

    fun show(key: Pair<String, String>) {
        if (key == shownKey) return
        shownKey?.let { views[it]?.onHidden() }
        shownLayers.forEach { it.removeLayer() }
        val (pkg, name) = key
        val example = packages.first { it.name == pkg }.examples.first { it.name == name }
        val view = views.getOrPut(key) {
            // A broken example must not take the app (or the tab bar's subscription) down.
            val built = try {
                example.build(context)
            } catch (e: Exception) {
                buildFailures += "$pkg / $name" to e
                ExampleView(failurePanel(exampleContentSize, e))
            }
            built.also { placeholders.getValue(key).addComponent(it.content) }
        }
        shownLayers = view.layers.map { screen.addLayer(it) }
        view.onShown()
        shownKey = key
        update { copy(packageName = pkg, exampleName = name) }
    }

    val exampleBars = LinkedHashMap<String, TabBar>()
    val packageBar = horizontalTabBar {
        this.size = bodySize
        position = Position(0, HEADER_HEIGHT)
        defaultSelected = packages.firstOrNull { it.name == selection.packageName }?.name ?: packages.first().name
        packages.forEach { pkg ->
            val initial = pkg.examples.firstOrNull { it.name == selection.exampleName }?.name
                ?: pkg.examples.first().name
            val bar = verticalTabBar {
                this.size = Size(bodySize.width, bodySize.height - 3)
                tabWidth = EXAMPLE_TAB_WIDTH
                defaultSelected = initial
                pkg.examples.forEach { example ->
                    val placeholder = panel { preferredSize = exampleContentSize }
                    placeholders[pkg.name to example.name] = placeholder
                    tab {
                        key = example.name
                        label = example.name
                        content = placeholder
                    }
                }
            }
            exampleBars[pkg.name] = bar
            tab {
                key = pkg.name
                label = pkg.name
                content = bar.root
            }
        }
    }
    screen.addComponent(packageBar.root)

    exampleBars.forEach { (pkg, bar) ->
        bar.selectedKeyProperty.onChange { (_, name) ->
            if (packageBar.selectedKey == pkg) show(pkg to name)
        }
    }
    packageBar.selectedKeyProperty.onChange { (_, pkg) ->
        show(pkg to exampleBars.getValue(pkg).selectedKey)
    }

    // A layer, not a label: components can't overlap, and the tab bar spans the screen.
    val fps = layer {
        this.size = Size(FPS_WIDTH, 1)
        offset = Position(size.width - FPS_WIDTH - 1, HEADER_HEIGHT + 1)
    }
    screen.addLayer(fps)
    val counter = FpsCounter()
    fun showFps(value: Int) {
        "$value fps".padStart(FPS_WIDTH).forEachIndexed { x, char ->
            fps.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = screen.theme.accentColor
                        backgroundColor = Color.TRANSPARENT
                    }
                },
                Position(x, 0)
            )
        }
    }

    // Footer: the latest error Zircon contained (a handler, animation or renderer that threw),
    // in the bottom row, so a broken example is visible without a log. Also a layer, see above.
    val errorLine = layer {
        this.size = Size(size.width, 1)
        offset = Position(0, size.height - 1)
    }
    screen.addLayer(errorLine)
    val runtimeErrors = ArrayList<Pair<String, Throwable>>()
    fun showError(source: String, error: Throwable) {
        val text = "$source: ${error::class.simpleName}: ${error.message ?: ""}"
        text.take(size.width).padEnd(size.width).forEachIndexed { x, char ->
            errorLine.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = screen.theme.accentColor
                        backgroundColor = screen.theme.primaryBackgroundColor
                    }
                },
                Position(x, 0)
            )
        }
    }
    // Process-wide, like a logger: chained so a handler the launcher installed still runs.
    val previousHandler = ZirconDefaults.current.onError
    ZirconDefaults.current = ZirconDefaults.current.copy(onError = ErrorHandler { source, error ->
        runtimeErrors += source to error
        showError(source, error)
        previousHandler.onError(source, error)
    })

    screen.display()
    show(packageBar.selectedKey to exampleBars.getValue(packageBar.selectedKey).selectedKey)
    return ExamplesScreen(grid, screen, selectionProperty, packageBar, exampleBars, buildFailures, runtimeErrors) {
        counter.frame()?.let(::showFps)
        shownKey?.let { views[it]?.onFrame?.invoke() }
    }
}

private fun failurePanel(size: Size, error: Throwable) = vBox {
    preferredSize = size
    decorations { +margin(1) }
    header { +"This example failed to build" }
    paragraph {
        preferredSize = Size(size.width - 2, size.height - 4)
        +(error.message ?: error.toString())
    }
}

private suspend fun loadAssets(tileset: TilesetResource): Assets {
    val rexFiles = listOf("zircon_logo.xp", "Buildings.xp", "cp437_table.xp").associateWith { name ->
        loadREXFile(Resource.create("/rex_files/$name", ResourceType.PROJECT))
    }
    val skull = loadAnimation(Resource.create("/animations/skull.zap", ResourceType.PROJECT), tileset)
    return Assets(rexFiles, skull)
}

/**
 * Counts frames; [frame] returns the frames per second once a second, `null` otherwise.
 */
private class FpsCounter {
    private var windowStart = TimeSource.Monotonic.markNow()
    private var frames = 0

    fun frame(): Int? {
        frames++
        if (windowStart.elapsedNow().inWholeMilliseconds < 1000) return null
        val fps = frames
        frames = 0
        windowStart = TimeSource.Monotonic.markNow()
        return fps
    }
}

/** The smallest grid the app lays out in. */
public val MIN_SIZE: Size = Size(100, 40)

private const val HEADER_HEIGHT = 3
private const val SELECTOR_WIDTH = 26
private const val FPS_WIDTH = 8
