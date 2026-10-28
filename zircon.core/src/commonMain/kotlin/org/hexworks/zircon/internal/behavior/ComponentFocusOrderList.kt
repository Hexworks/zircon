package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.internal.component.InternalComponent

/**
 * Stores a doubly linked list of the focusable components of a component tree
 * and allows operations on them. This can be used to implement focus handling
 * for components.
 */
public interface ComponentFocusOrderList {

    public val focusedComponent: InternalComponent

    public fun isFocused(component: InternalComponent): Boolean = component == focusedComponent

    /**
     * Returns the next component that can be focused.
     */
    public fun findNext(): InternalComponent

    /**
     * Returns the previous component that can be focused.
     */
    public fun findPrevious(): InternalComponent

    /**
     * Focuses the given component (if it is possible).
     */
    public fun focus(component: InternalComponent)

    /**
     * Rebuilds the focus order list.
     */
    public fun refreshFocusables()

    /**
     * Tells whether the given [InternalComponent] is capable of
     * being focused.
     */
    public fun canFocus(component: InternalComponent): Boolean
}
