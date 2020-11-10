package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [ToggleButton] is an [Component] that's [Selectable] and will
 * visually display its [Selectable.isSelected] state.
 */
interface ToggleButton : Component, Selectable, TextOverride
