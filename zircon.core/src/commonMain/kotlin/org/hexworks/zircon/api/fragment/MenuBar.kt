package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.events.api.CallbackResult
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.fragment.menu.MenuSelection

/**
 * A [MenuBar] contains a list of buttons (usually positioned at the top of
 * the screen horizontally) that open a modal with additional selection options
 * when clicked.
 */
@Beta
interface MenuBar<T : Any> : Fragment {

    /**
     * Adds a listener to this [MenuBar] that will be fired whenever
     * a menu item is selected (a button is pressed in a dropdown menu).
     */
    fun onMenuItemSelected(
        handler: (menuSelection: MenuSelection<T>) -> CallbackResult
    ): Subscription
}
