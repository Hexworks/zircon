package org.codetome.zircon.component

/**
 * Represents the possible states a [ContainerHandler] can be in.
 */
enum class ContainerHandlerState {
    /**
     * The [ContainerHandler] is was just created.
     */
    UNKNOWN,
    /**
     * The [ContainerHandler] is active.
     */
    ACTIVE,
    /**
     * The [ContainerHandler] is intactive.
     */
    DEACTIVATED
}