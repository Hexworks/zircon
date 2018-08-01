package org.codetome.zircon.internal.component

/**
 * Represents the possible states a [InternalContainerHandler] can be in.
 */
enum class ContainerHandlerState {
    /**
     * The [InternalContainerHandler] is was just created.
     */
    UNKNOWN,
    /**
     * The [InternalContainerHandler] is active.
     */
    ACTIVE,
    /**
     * The [InternalContainerHandler] is intactive.
     */
    DEACTIVATED
}