package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.internal.component.InternalComponent

interface ComponentFocusHandler {

    val focusedComponent: InternalComponent

    fun isFocused(component: InternalComponent) = component == focusedComponent

    /**
     * Returns the next component to focus (if any).
     */
    fun findNext(): Maybe<InternalComponent>

    /**
     * Returns the previous component to focus (if any).
     */
    fun findPrevious(): Maybe<InternalComponent>

    /**
     * Focuses the given component (if it is possible).
     */
    fun focus(component: InternalComponent)

    fun refreshFocusables()

    /**
     * Tells whether the given [InternalComponent] is capable of
     * being focused.
     */
    fun canFocus(component: InternalComponent): Boolean
}
