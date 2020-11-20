package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.*

/**
 * This interface contains all the common *properties* that a UI [Component]
 * can have.
 */
interface ComponentProperties : CanBeDisabled, CanBeHidden, ColorThemeOverride, TilesetOverride
