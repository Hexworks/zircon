package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.theme.ComponentTheme

/**
 * Represents an object that has a (mutable) [ComponentTheme]. There is always one:
 * [ComponentThemes.default()][org.hexworks.zircon.api.ComponentThemes.default] is the stock
 * look. A container's component theme propagates to its children when they are attached and
 * when it changes, like the color theme does, except to a child whose theme was set
 * explicitly.
 */
public interface ComponentThemeOverride {

    public var componentTheme: ComponentTheme

    /**
     * A [Property] that wraps [componentTheme] and offers data binding and observability.
     */
    public val componentThemeProperty: Property<ComponentTheme>
}
