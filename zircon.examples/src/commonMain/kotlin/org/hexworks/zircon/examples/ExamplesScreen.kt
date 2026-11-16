package org.hexworks.zircon.examples

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.fragment.matrixTabBar
import org.hexworks.zircon.api.builder.fragment.selector
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.grid.DEFAULT_TILESET
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.error
import org.hexworks.zircon.api.component.theme.primary
import org.hexworks.zircon.api.component.theme.surface
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.fragment.MatrixTabBar
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.loadAnimation
import org.hexworks.zircon.api.loadREXFile
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.screen.TileGridScreen
import kotlin.time.TimeSource


/**
 * A built examples screen. A launcher needs three things: the [grid] to render, [onFrame]
 * to call before every frame, and [tileSizeProperty] to know when the grid has to be
 * rebuilt. Everything else is a test hook, see [ExamplesTestApi].
 */
class ExamplesScreen(
    val grid: TileGrid,
    /**
     * The cell size the user picked. Changing it is the one selection that cannot be
     * applied to a running grid: the number of cells that fit the window changes with it, so
     * the launcher builds a new screen (passing this one as `previous` to keep the rest of
     * the selection). Tileset and palette changes within the same cell size are applied in
     * place.
     */
    val tileSizeProperty: ObservableValue<Size>,
    /**
     * Called by the renderer right before every frame, on the thread that renders. Zircon
     * has no loop of its own: the host (Compose's frame clock, the terminal's tick) drives
     * the frames and this is the app's hook into that loop. Here it counts the frame for
     * the fps display and forwards to the shown example's [ExampleView.onFrame], where
     * per-frame logic (game ticks, benchmarks) lives.
     */
    val onFrame: () -> Unit,
    /** The tileset the grid and its components draw with; carried over to a rebuilt screen. */
    internal val tilesetProperty: Property<TilesetResource>,
    /** The palette; carried over to a rebuilt screen. */
    internal val paletteProperty: Property<ComponentPalette>,
    /** The groups the app shows (with `isTerminal`, only those with a text-only example). */
    @ExamplesTestApi val packages: List<ExampleGroup>,
    /** Groups across the top, the selected group's examples down the left. */
    @ExamplesTestApi val tabBar: MatrixTabBar,
    /** Examples whose build threw, as "group / example" to the error; the app shows the message instead. */
    @ExamplesTestApi val buildFailures: List<Pair<String, Throwable>>,
    /** Errors Zircon contained while running, as source to the error; the latest shows in the footer. */
    @ExamplesTestApi val runtimeErrors: List<Pair<String, Throwable>>,
)

/**
 * The grid size that fills [widthPx] x [heightPx] with cells of [tileSize], at least
 * [MIN_SIZE].
 */
fun gridSizeFor(tileSize: Size, widthPx: Int, heightPx: Int): Size = Size(
    maxOf(widthPx / tileSize.width, MIN_SIZE.width),
    maxOf(heightPx / tileSize.height, MIN_SIZE.height),
)

/**
 * Builds the examples app into a grid of [size]: tile size, tileset and palette selectors on
 * top (only the palette in a terminal), then a matrix tab bar with the groups as horizontal
 * tabs and the selected group's examples as vertical tabs, an fps counter in the corner
 * and the latest contained error in the bottom row. Examples are built the first time
 * they are shown.
 *
 * Picking a palette or a tileset of the same cell size applies at once. Picking another
 * cell size only updates [ExamplesScreen.tileSizeProperty]; the launcher then builds a
 * new screen with the previous one as [previous], which starts from the previous one's
 * tile size, tileset, palette and selected example. With [isTerminal] the examples that
 * need graphical tiles and the tileset selectors are left out. [startAt] names the
 * example to show first; it wins over [previous].
 */
@OptIn(ExamplesTestApi::class)
suspend fun examplesScreen(
    size: Size,
    isTerminal: Boolean = false,
    previous: ExamplesScreen? = null,
    startAt: String? = null,
): ExamplesScreen {
    require(size.width >= MIN_SIZE.width && size.height >= MIN_SIZE.height) {
        "The examples need at least ${MIN_SIZE.width}x${MIN_SIZE.height} cells, got ${size.width}x${size.height}"
    }

    val packages = EXAMPLE_GROUPS
        .map { pkg ->
            ExampleGroup(
                name = pkg.name,
                examples = pkg.examples.filter { !isTerminal || it.textOnly }
            )
        }.filter { it.examples.isNotEmpty() }

    // The previous screen's tileset is kept when its size is the chosen one; after a size
    // change the first tileset of the new size is used.
    val tileSize = previous?.tileSizeProperty?.value ?: DEFAULT_TILESET.size
    val initialTileset = previous?.tilesetProperty?.value?.takeIf { it.size == tileSize } ?: tilesetsFor(tileSize).first()
    val initialPalette = previous?.paletteProperty?.value ?: DEFAULT_PALETTE

    val grid = tileGrid {
        this.size = size
        tileset = initialTileset
    }
    val screen = grid.toScreen()
    screen.componentPalette = initialPalette
    val assets = loadAssets(grid.tileset)

    val tileSizeProperty = tileSize.toProperty()
    val tilesetProperty = initialTileset.toProperty()
    val paletteProperty = initialPalette.toProperty()

    paletteProperty.onChange { (_, changed) -> screen.componentPalette = changed }
    // A screen's tileset and its component tree's are separate: `Screen.tileset` is the
    // grid's (the base layer and the cursor), the components follow their root container.
    // The root is only reachable through the screen implementation, so this reaches in.
    val root = (screen as TileGridScreen).root
    tilesetProperty.onChange { (_, changed) ->
        screen.tileset = changed
        root.tileset = changed
    }

    // Header: selectors. The selectors share the properties above, so a pick lands there.
    val header = hBox {
        preferredSize = Size(size.width, HEADER_HEIGHT)
        componentRenderer = NoOpComponentRenderer()
        decorations { +margin(1, 1) }
        spacing = 2
        if (isTerminal.not()) {
            hBox {
                spacing = 1
                label { +"Tile size" }
                selector {
                    width = TILE_SIZE_SELECTOR_WIDTH
                    valueList = TILE_SIZES
                    selectedProperty = tileSizeProperty
                    toStringMethod = { "${it.width}x${it.height}" }
                }
            }

            hBox {
                spacing = 1
                label { +"Tileset" }
                selector {
                    width = SELECTOR_WIDTH
                    valueList = tilesetsFor(tileSize)
                    selectedProperty = tilesetProperty
                    toStringMethod = { it.title }
                }
            }
        }
        // Colors only: every demo keeps its shape, so switching here is safe. Component themes
        // (shape) are picked on the themes page, whose components are sized for them.
        hBox {
            spacing = 1
            label { +"Palette" }
            selector {
                width = SELECTOR_WIDTH
                valueList = PALETTES
                selectedProperty = paletteProperty
                toStringMethod = { it.name }
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
    }

    // Start where asked, else where the previous screen was, else at the first example.
    val initialExample = startAt?.let { name -> packages.flatMap { it.examples }.firstOrNull { it.name == name } }
    val initialPackage = packages.firstOrNull { pkg -> initialExample != null && initialExample in pkg.examples }
        ?: packages.firstOrNull { it.name == previous?.tabBar?.selectedHorizontalKey }
        ?: packages.first()
    val initialVertical = initialExample?.name
        ?: initialPackage.examples.firstOrNull { it.name == previous?.tabBar?.selectedVerticalKey }?.name
        ?: initialPackage.examples.first().name
    val tabBar = matrixTabBar {
        this.size = bodySize
        position = Position(0, HEADER_HEIGHT)
        tabWidth = EXAMPLE_TAB_WIDTH
        defaultHorizontal = initialPackage.name
        defaultVertical = initialVertical
        packages.forEach { pkg ->
            horizontalTab { key = pkg.name; label = pkg.name }
        }
        // Example names are unique across packages; the vertical strip shows only the selected package's.
        packages.flatMap { pkg -> pkg.examples.map { it.name } }.distinct().forEach { name ->
            verticalTab { key = name; label = name }
        }
        packages.forEach { pkg ->
            pkg.examples.forEach { example ->
                val placeholder = panel { preferredSize = exampleContentSize }
                placeholders[pkg.name to example.name] = placeholder
                view {
                    horizontal = pkg.name
                    vertical = example.name
                    content = placeholder
                }
            }
        }
    }
    screen.addComponent(tabBar.root)

    // Both keys change when a package without the current example is selected; the pair is
    // read when either changes and shown once it names an example.
    fun showSelected() {
        val key = tabBar.selectedHorizontalKey to tabBar.selectedVerticalKey
        if (placeholders.containsKey(key)) show(key)
    }
    tabBar.selectedHorizontalKeyProperty.onChange { showSelected() }
    tabBar.selectedVerticalKeyProperty.onChange { showSelected() }

    // A layer, not a label: components can't overlap, and the tab bar spans the screen.
    val fps = layer {
        this.size = Size(FPS_WIDTH, 1)
        tileset = initialTileset
        offset = Position(size.width - FPS_WIDTH - 1, HEADER_HEIGHT + 1)
    }
    screen.addLayer(fps)
    val counter = FpsCounter()
    tilesetProperty.onChange { (_, changed) -> fps.tileset = changed }

    fun showFps(value: Int) {
        "$value fps".padStart(FPS_WIDTH).forEachIndexed { x, char ->
            fps.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = screen.componentPalette.primary
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
        tileset = initialTileset
        offset = Position(0, size.height - 1)
    }
    screen.addLayer(errorLine)
    tilesetProperty.onChange { (_, changed) -> errorLine.tileset = changed }
    val runtimeErrors = ArrayList<Pair<String, Throwable>>()
    fun showError(source: String, error: Throwable) {
        val text = "$source: ${error::class.simpleName}: ${error.message ?: ""}"
        text.take(size.width).padEnd(size.width).forEachIndexed { x, char ->
            errorLine.draw(
                characterTile {
                    character = char
                    withStyleSet {
                        foregroundColor = screen.componentPalette.error
                        backgroundColor = screen.componentPalette.surface
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
    showSelected()
    return ExamplesScreen(
        grid = grid,
        tileSizeProperty = tileSizeProperty,
        onFrame = {
            counter.frame()?.let(::showFps)
            shownKey?.let { views[it]?.onFrame?.invoke() }
        },
        tilesetProperty = tilesetProperty,
        paletteProperty = paletteProperty,
        packages = packages,
        tabBar = tabBar,
        buildFailures = buildFailures,
        runtimeErrors = runtimeErrors,
    )
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
val MIN_SIZE: Size = Size(100, 40)

private const val HEADER_HEIGHT = 3
private const val SELECTOR_WIDTH = 26
private const val TILE_SIZE_SELECTOR_WIDTH = 9
private const val FPS_WIDTH = 8
