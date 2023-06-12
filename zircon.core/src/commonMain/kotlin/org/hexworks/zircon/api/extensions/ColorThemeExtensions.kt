package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.component.ColorTheme

fun ColorTheme.copy(fn: ColorThemeBuilder.() -> Unit) = ColorThemeBuilder().apply {
    this.primaryForegroundColor = this@copy.primaryForegroundColor
    this.primaryBackgroundColor = this@copy.primaryBackgroundColor
    this.secondaryForegroundColor = this@copy.secondaryForegroundColor
    this.secondaryBackgroundColor = this@copy.secondaryBackgroundColor
    this.accentColor = this@copy.accentColor
    fn(this)
}.build()