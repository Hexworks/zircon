package org.hexworks.zircon.api.component.theme

import org.hexworks.zircon.api.component.data.ComponentState

/**
 * One slot a [ComponentPalette] may override after the theme derived it: the colors of a
 * component ([role], [variant], [part]) in one [state].
 */
public data class StyleOverrideKey(
    val role: ComponentRole,
    val state: ComponentState,
    val variant: Variant = Variant.DEFAULT,
    val part: Part = Part.NONE,
)
