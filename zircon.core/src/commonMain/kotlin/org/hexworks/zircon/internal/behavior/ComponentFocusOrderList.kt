package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.internal.component.InternalComponent

/**
 * Stores a doubly linked list of the focusable components of a component tree
 * and allows operations on them. This can be used to implement focus handling
 * for components.
 */
interface ComponentFocusOrderList {

    val focusedComponent: InternalComponent

    fun isFocused(component: InternalComponent) = component == focusedComponent

    /**
     * Returns the next component that can be focused.
     */
    fun findNext(): InternalComponent

    /**
     * Returns the previous component that can be focused.
     */
    fun findPrevious(): InternalComponent

    /**
     * Focuses the given component (if it is possible).
     */
    fun focus(component: InternalComponent)

    /**
     * Rebuilds the focus order list.
     */
    fun refreshFocusables()

    /**
     * Tells whether the given [InternalComponent] is capable of
     * being focused.
     */
    fun canFocus(component: InternalComponent): Boolean
}
