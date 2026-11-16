package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.theme.ComponentPalette

/**
 * Represents an object that has a (mutable) [ComponentPalette]: the colors its
 * [ComponentTheme][org.hexworks.zircon.api.component.theme.ComponentTheme] draws with. There
 * is always one. A container's palette propagates to its children when they are attached and
 * when it changes, except to a child whose palette was set explicitly.
 */
public interface ComponentPaletteOverride {

    public var componentPalette: ComponentPalette

    /**
     * A [Property] that wraps [componentPalette] and offers data binding and observability.
     */
    public val componentPaletteProperty: Property<ComponentPalette>
}
