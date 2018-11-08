package org.hexworks.zircon.internal.component

/**
 * Represents the possible states a [InternalComponentContainer] can be in.
 */
enum class ContainerHandlerState {
    /**
     * The [InternalComponentContainer] is was just created.
     */
    UNKNOWN,
    /**
     * The [InternalComponentContainer] is active.
     */
    ACTIVE,
    /**
     * The [InternalComponentContainer] is intactive.
     */
    DEACTIVATED
}
