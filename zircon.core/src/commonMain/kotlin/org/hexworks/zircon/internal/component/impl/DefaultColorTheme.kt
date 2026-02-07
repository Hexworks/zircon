package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.ColorTheme

data class DefaultColorTheme(
    override val name: String,
    override val primaryForegroundColor: Color,
    override val secondaryForegroundColor: Color,
    override val primaryBackgroundColor: Color,
    override val secondaryBackgroundColor: Color,
    override val accentColor: Color
) : ColorTheme {

    override fun toString() = name
}
