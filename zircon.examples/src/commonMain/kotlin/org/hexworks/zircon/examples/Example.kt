package org.hexworks.zircon.examples

import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.resource.REXPaintResource
import org.hexworks.zircon.api.screen.Screen

/**
 * One example of the examples app. [build] runs the first time the example is shown and
 * returns what to display; it is given the [ExampleContext] of the area below the tabs.
 * [name] is what the tab shows, so keep it short (13 characters fit).
 */
class Example(
    val name: String,
    /** `false` when the example draws graphical tiles, which a terminal cannot show. */
    val textOnly: Boolean = true,
    val build: (ExampleContext) -> ExampleView,
)

/** A group of examples: one horizontal tab, its examples in the vertical bar. */
class ExampleGroup(
    val name: String,
    val examples: List<Example>
)

/**
 * Where an example draws: the [screen] it lives on and the [contentSize] of its area.
 * [assets] holds the data files every launcher preloads.
 */
class ExampleContext(
    val screen: Screen,
    val contentSize: Size,
    val assets: Assets,
) {
    /** An empty panel of the content size, for examples that draw with layers instead. */
    fun emptyContent(): Component = panel { preferredSize = this@ExampleContext.contentSize }
}

/**
 * What an example shows: its [content] component; [layers] the app adds to the screen while
 * the example is visible and removes when it is not; [onShown] and [onHidden] for anything
 * else that must start and stop with visibility (animations, timers).
 *
 * [onFrame] is called before every frame while the example is visible, on the thread that
 * renders. It is the place for logic that has to keep step with rendering (a game tick,
 * a benchmark that redraws every frame) without a thread or timer of its own: Zircon does
 * not run a loop, the renderer host does, and [ExamplesScreen.onFrame] forwards its tick
 * to the shown example. Keep it cheap, it runs on every frame.
 */
public class ExampleView(
    public val content: Component,
    public val layers: List<Layer> = emptyList(),
    public val onFrame: () -> Unit = {},
    public val onShown: () -> Unit = {},
    public val onHidden: () -> Unit = {},
)

/** The data files the examples use, loaded once by [examplesScreen]. */
public class Assets(
    /** REXPaint files by file name, e.g. `zircon_logo.xp`. */
    public val rexFiles: Map<String, REXPaintResource>,
    /** The skull animation, loaded for the current tileset; copy it before building. */
    public val skull: AnimationBuilder,
)
