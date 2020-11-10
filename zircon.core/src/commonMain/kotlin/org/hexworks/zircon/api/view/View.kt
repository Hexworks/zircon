package org.hexworks.zircon.api.view

import org.hexworks.zircon.api.behavior.ThemeOverride
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.component.ColorTheme

/**
 * A [View] is similar to a [Fragment], but instead of being a reusable group of [Component]s with additional
 * UI logic they represent reusable [Screen]s with additional UI logic. Each [View] has its own [screen]
 * that's used to display the contents of the [View]. Whenever a [View] is [dock]ed [Screen.display]
 * will be called under the hood which will effectively replace any previously [dock]ed [View].
 *
 * [View]s add some lifecycle methods to the mix:
 *
 * - [onDock] will be called whenever a [View] is [dock]ed
 * - [onUndock] will be called on a [dock]ed [View] when [dock] is called on another [View].
 *
 * Docking works like this:
 *
 * - if there is a currently [dock]ed [View] is is undocked ([onUndock] is called on it)
 * - [onDock] is called on the [View] that's being [dock]ed
 * - the [View] that's being [dock]ed is displayed on the [Screen]
 *
 * Whenever you create a [View] you have to extend [BaseView] and supply a [TileGrid]
 * and a [ColorTheme]. For an example that uses best practices take a look at the included
 * sample or a full example in the `zircon.jvm.examples` project.
 *
 * @see TileGrid
 * @see ColorTheme
 *
 * @sample org.hexworks.zircon.samples.WelcomeView
 */
interface View : ThemeOverride {

    /**
     * The [Screen] that's used to display the contents of this [View].
     */
    val screen: Screen

    /**
     * Docks this [View] to the [screen].
     */
    fun dock()

    /**
     * Replaces this [View] with [view]. Same as calling [dock] on [view].
     */
    fun replaceWith(view: View)

    /**
     * Callback that will be executed whenever this [View] is [dock]ed.
     */
    fun onDock() {}

    /**
     * Callback that will be executed whenever another [View] is [dock]ed.
     */
    fun onUndock() {}

}
