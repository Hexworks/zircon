package org.hexworks.zircon.api.component.data

/**
 * Represents the states a [org.hexworks.zircon.api.component.Component] can be in.
 */
enum class ComponentState {

    DEFAULT,
    /**
     * The mouse is over this component.
     */
    MOUSE_OVER,
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
