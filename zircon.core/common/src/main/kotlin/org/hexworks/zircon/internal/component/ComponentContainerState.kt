package org.hexworks.zircon.internal.component

/**
 * Represents the possible states a [InternalComponentContainer] can be in.
 */
enum class ComponentContainerState {
    /**
     * The [InternalComponentContainer] is was just created.
     */
    INITIALIZING,
    /**
     * The [InternalComponentContainer] is active.
     */
    ACTIVE,
    /**
     * The [InternalComponentContainer] is intactive.
     */
    INACTIVE
}
