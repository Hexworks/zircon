package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.theme.ComponentTheme

/**
 * Represents an object that has a (mutable) [ComponentTheme]. `null` means none: the object
 * is drawn the legacy way, from its [ColorTheme][org.hexworks.zircon.api.component.ColorTheme].
 * A container's component theme propagates to its children when they are attached and when
 * it changes, like the color theme does.
 */
public interface ComponentThemeOverride {

    public var componentTheme: ComponentTheme?

    /**
     * A [Property] that wraps [componentTheme] and offers data binding and observability.
     */
    public val componentThemeProperty: Property<ComponentTheme?>
}
