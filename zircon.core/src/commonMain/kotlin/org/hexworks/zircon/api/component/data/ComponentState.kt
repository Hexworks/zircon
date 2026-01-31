package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.component.Component

/**
 * Represents the states a [Component] can be in.
 */
enum class ComponentState {

    /**
     * Used when the component doesn't have focus and it is not being interacted.
     */
    DEFAULT,

    /**
     * The component is highlighted (the mouse is over it for example).
     */
    HIGHLIGHTED,

    /**
     * This component has focus.
     */
    FOCUSED,

    /**
     * This component is inactive, its features are disabled.
     */
    DISABLED,

    /**
     * The component is active (for example if a Button is being pressed).
     */
    ACTIVE
}
