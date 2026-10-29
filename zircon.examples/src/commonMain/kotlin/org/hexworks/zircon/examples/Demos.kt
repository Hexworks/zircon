package org.hexworks.zircon.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import kotlinx.coroutines.delay
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeHeight
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.compose.ComposeResourceLoader
import org.hexworks.zircon.compose.TileGridView
import org.hexworks.zircon.examples.resources.Res
import kotlin.time.TimeSource

/**
 * One demo of the examples app. Every demo gets its own full-window grid in its own
 * [tileset]; [show] builds the demo into the area below the tab bar. It suspends because
 * some demos load archives (REXPaint files, animations).
 */
public class Demo(
    public val name: String,
    public val tileset: TilesetResource,
    public val show: suspend (DemoContext) -> DemoView,
)

/**
 * Where a demo draws: the [screen] of its grid, and the [contentPosition] and
 * [contentSize] of the area below the tab bar.
 */
public class DemoContext(
    public val screen: Screen,
    public val contentPosition: Position,
    public val contentSize: Size,
) {
    /** An empty panel of the content size, for demos that draw with layers instead. */
    public fun emptyContent(): Component = panel { preferredSize = contentSize }
}

/**
 * What a demo shows: the [content] component of its tab, and [onFrame], called before
 * every frame is rendered.
 */
public class DemoView(public val content: Component, public val onFrame: () -> Unit = {})

/**
 * All demos, in tab order.
 */
public val demos: List<Demo> = listOf(componentsDemo, gameAreaDemo, rexPaintDemo, animationDemo, benchmarkDemo)

/**
 * The examples app, built entirely from Zircon components: a horizontal tab bar on top,
 * the selected demo below, in a grid that fills the available space. Switching tabs or
 * resizing the window rebuilds the grid, since each demo has its own tileset.
 */
@Composable
public fun ExamplesApp() {
    var selected by remember { mutableStateOf(demos.first()) }
    BoxWithConstraints(Modifier.fillMaxSize().background(ComposeColor.Black)) {
        val widthPx = constraints.maxWidth
        val heightPx = constraints.maxHeight
        val shown by produceState<ExamplesScreen?>(null, selected, widthPx, heightPx) {
            delay(RESIZE_DEBOUNCE_MS) // a window drag reports many sizes; build the last one
            // The demos' own Compose resources (REXPaint files, animations) go through core's
            // resource loader, so they have to be registered next to Zircon's bundled ones.
            ComposeResourceLoader.install()
            ComposeResourceLoader.addProjectResources { path -> Res.readBytes(path) }
            value = examplesScreen(selected, widthPx, heightPx) { selected = it }
        }
        shown?.let { TileGridView(it.grid, beforeFrame = it.onFrame) }
    }
}

/**
 * A built examples screen: the [grid] to render, its [tabBar] and the callback to run
 * before each frame.
 */
public class ExamplesScreen(public val grid: TileGrid, public val tabBar: TabBar, public val onFrame: () -> Unit)

/**
 * Builds the grid for [demo] with as many tiles of its tileset as fit into
 * [widthPx] x [heightPx]: a tab bar for every demo on top, the demo's content below and
 * an fps counter in the top right corner. Clicking another tab calls [onSelect].
 */
public suspend fun examplesScreen(demo: Demo, widthPx: Int, heightPx: Int, onSelect: (Demo) -> Unit): ExamplesScreen {
    val tileset = demo.tileset
    val size = Size(
        maxOf(widthPx / tileset.width, MIN_SIZE.width),
        maxOf(heightPx / tileset.height, MIN_SIZE.height),
    )
    val grid = tileGrid {
        this.size = size
        this.tileset = tileset
    }
    val screen = grid.toScreen()
    screen.theme = ColorThemes.arc()

    val contentSize = size.withRelativeHeight(-TAB_BAR_HEIGHT)
    val view = demo.show(DemoContext(screen, Position(0, TAB_BAR_HEIGHT), contentSize))

    val tabBar = horizontalTabBar {
        this.size = size
        defaultSelected = demo.name
        demos.forEach { other ->
            tab {
                key = other.name
                label = other.name
                content = if (other === demo) view.content else panel { preferredSize = contentSize }
            }
        }
    }
    screen.addComponent(tabBar.root)
    tabBar.selectedKeyProperty.onChange { (_, key) ->
        demos.firstOrNull { it.name == key && it !== demo }?.let(onSelect)
    }

    // A layer, not a label: components can't overlap, and the tab bar spans the screen.
    val fps = layer {
        this.size = Size(FPS_WIDTH, 1)
        offset = Position(size.width - FPS_WIDTH, 1)
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

    screen.display()
    return ExamplesScreen(grid, tabBar) {
        counter.frame()?.let(::showFps)
        view.onFrame()
    }
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

private const val TAB_BAR_HEIGHT = 3
private const val FPS_WIDTH = 8
private const val RESIZE_DEBOUNCE_MS = 150L
private val MIN_SIZE = Size(60, 20)
