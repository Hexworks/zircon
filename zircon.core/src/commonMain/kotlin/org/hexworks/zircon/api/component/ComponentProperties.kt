package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.CanBeDisabled
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.behavior.ComponentThemeOverride

/**
 * This interface contains all the common *properties* that a UI [Component]
 * can have.
 */
public interface ComponentProperties : CanBeDisabled, CanBeHidden, ColorThemeOverride, ComponentThemeOverride, TilesetOverride {
    public val name: String

    /**
     * What this component is for a [org.hexworks.zircon.api.component.theme.ComponentTheme].
     */
    public val role: ComponentRole
}
