package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.core.api.behavior.DisposeState
import org.hexworks.cobalt.core.api.behavior.NotDisposed
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.SystemTheme
import org.hexworks.zircon.api.component.theme.ThemeMode
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * Creates a [PanelBuilder] with a no-op renderer and the size of the
 * [Screen] and [Position] zero, and applies the builder function ([fn]) to it.
 * Note that the size is fixed, and you cannot change it.
 */
public fun Screen.useComponentBuilder(fn: PanelBuilder.() -> Unit) {
    val builder = PanelBuilder().apply {
        preferredSize = size
        position = Position.ZERO
    }
    fn(builder)
    // we need to make sure that these were not tampered with
    builder.apply {
        componentRenderer = NoOpComponentRenderer()
        preferredSize = size
        position = Position.ZERO
    }

    addComponent(builder.build())
}

/**
 * Keeps this screen's `componentPalette` on what the host wants: [SystemTheme.palette] when the
 * host reports colors (a terminal), else [fallback] of [SystemTheme.mode] (a light / dark pair
 * of your choice, `Palettes.catppuccin(it)` say). Applies now and again whenever either changes.
 *
 * Setting `componentPalette` to anything this binding did not produce ends the follow, the way
 * an explicitly set palette ends inheritance from a parent; so does closing the screen, or
 * disposing the returned [Subscription]. `ZirconDefaults { followSystemPalette = ... }` makes
 * every new screen follow from the start.
 */
public fun Screen.followSystemPalette(fallback: (ThemeMode) -> ComponentPalette): Subscription =
    SystemPaletteFollow(this, fallback)

private class SystemPaletteFollow(
    private val screen: Screen,
    private val fallback: (ThemeMode) -> ComponentPalette,
) : Subscription {

    private var applied: ComponentPalette? = null
    private val subscriptions = ArrayList<Subscription>()

    override var disposeState: DisposeState = NotDisposed
        private set

    init {
        subscriptions += SystemTheme.paletteProperty.onChange { apply() }
        subscriptions += SystemTheme.modeProperty.onChange { apply() }
        subscriptions += screen.componentPaletteProperty.onChange { (_, changed) ->
            if (changed !== applied) dispose()
        }
        apply()
        disposeWhen(screen.closedProperty)
    }

    private fun apply() {
        if (disposed) return
        val next = SystemTheme.palette ?: fallback(SystemTheme.mode)
        applied = next
        screen.componentPalette = next
    }

    override fun dispose(disposeState: DisposeState) {
        if (disposed) return
        this.disposeState = disposeState
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
    }
}
