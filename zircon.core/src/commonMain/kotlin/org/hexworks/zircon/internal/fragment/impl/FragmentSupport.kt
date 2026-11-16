package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Size

/**
 * The cells the builder's current decorations take, so a fragment can size its content for
 * the theme's frame.
 */
internal val BaseComponentBuilder<*>.decorationsSize: Size
    get() = with(this) { decorationRenderers.occupiedSize }

/**
 * `true` when [role] gets no look from this theme (or there is no theme), so the fragment
 * applies its own default look instead.
 */
internal fun ComponentTheme?.lacks(
    role: ComponentRole,
    variant: Variant = Variant.DEFAULT,
    part: Part = Part.NONE,
    kind: ComponentRole = role,
): Boolean = this?.resolveEntry(role, variant, part, kind) == null
