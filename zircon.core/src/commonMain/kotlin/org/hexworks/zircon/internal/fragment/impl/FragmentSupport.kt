package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Size

/**
 * The cells the builder's current decorations take, so a fragment can size its content for
 * the theme's frame.
 */
internal val BaseComponentBuilder<*>.decorationsSize: Size
    get() = with(this) { decorationRenderers.occupiedSize }
