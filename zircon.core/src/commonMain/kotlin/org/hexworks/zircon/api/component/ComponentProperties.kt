package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.CanBeDisabled
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.behavior.ComponentPaletteOverride
import org.hexworks.zircon.api.behavior.ComponentThemeOverride

/**
 * This interface contains all the common *properties* that a UI [Component]
 * can have.
 */
public interface ComponentProperties : CanBeDisabled, CanBeHidden, ComponentThemeOverride, ComponentPaletteOverride, TilesetOverride {
    public val name: String

    /**
     * What this component is for a [org.hexworks.zircon.api.component.theme.ComponentTheme].
     */
    public val role: ComponentRole

    /**
     * The semantic weight within [role].
     */
    public val variant: Variant

    /**
     * Which piece of a composite this component is; [Part.NONE] for a standalone one.
     */
    public val part: Part

    /**
     * Which way this component runs.
     */
    public val orientation: Orientation
}
