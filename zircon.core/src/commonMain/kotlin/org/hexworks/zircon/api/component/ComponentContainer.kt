package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.behavior.ComponentPaletteOverride
import org.hexworks.zircon.api.behavior.ComponentThemeOverride

/**
 * Represents an object that can contain gui [Component]s and also maintains the
 * [componentTheme] and [componentPalette] its child [Component]s follow.
 * Note that a [ComponentContainer] won't enforce consistency: a child set explicitly
 * keeps its own, the rest are overwritten whenever the [ComponentContainer]'s change.
 *
 * @see Component
 * @see ComponentThemeOverride
 * @see ComponentPaletteOverride
 */
public interface ComponentContainer : ComponentThemeOverride, ComponentPaletteOverride {

    /**
     * Adds a child [Component] to this [ComponentContainer]. It can either be
     * a leaf component (like a label) or a [Container] which can itself
     * contain components within itself.
     */
    public fun addComponent(component: Component): AttachedComponent

    /**
     * Detaches all child components and returns them.
     */
    public fun detachAllComponents(): List<Component>

}
