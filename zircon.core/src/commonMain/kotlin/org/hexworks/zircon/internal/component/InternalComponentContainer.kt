package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentContainer
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.uievent.UIEventDispatcher

/**
 * Internal API for a [ComponentContainer].
 */
interface InternalComponentContainer : ComponentContainer, UIEventDispatcher {

    /**
     * Tells whether this [InternalComponentContainer] is active or not.
     */
    fun isActive(): Boolean

    /**
     * Activates this [InternalComponentContainer]. It will (re) start listening to
     * its related events.
     */
    fun activate()

    /**
     * Deactivates this [InternalComponentContainer]. It will no longer act on
     * container-related events.
     */
    fun deactivate()

    /**
     * Applies a [ColorTheme] to this component and recursively to all its children (if any).
     * @return the [ComponentStyleSet] which the [ColorTheme] was converted to.
     */
    fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet

    val layerStates: Iterable<LayerState>

}
