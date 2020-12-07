package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.CanBeDisabled
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.behavior.TilesetOverride

/**
 * This interface contains all the common *properties* that a UI [Component]
 * can have.
 */
interface ComponentProperties : CanBeDisabled, CanBeHidden, ColorThemeOverride, TilesetOverride
