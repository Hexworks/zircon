package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.builder.Builder

/**
 * Represents an object that can contain gui [Component]s and also maintains
 * a [theme] property that's synchronized between its child [Component]s.
 * Note that a [ComponentContainer] won't enforce consistency: the child
 * themes can be changed individually, but they will be overwritten whenever
 * the [ComponentContainer]'s theme changes.
 *
 * @see Component
 * @see ColorThemeOverride
 */
//! TODO: Add a parameter to ignore theme override?
interface ComponentContainer : ColorThemeOverride {

    /**
     * Adds a child [Component] to this [ComponentContainer]. It can either be
     * a leaf component (like a label) or a [Container] which can itself
     * contain components within itself.
     */
    fun addComponent(component: Component): AttachedComponent

    /**
     * Detaches all child components and returns them.
     */
    fun detachAllComponents(): List<Component>

}
