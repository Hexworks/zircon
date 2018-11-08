package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.component.impl.DefaultColorTheme

data class ColorThemeBuilder(private var primaryForegroundColor: TileColor = TileColor.defaultForegroundColor(),
                             private var secondaryForegroundColor: TileColor = TileColor.defaultForegroundColor(),
                             private var primaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                             private var secondaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                             private var accentColor: TileColor = TileColor.defaultForegroundColor()) {


    fun withPrimaryForegroundColor(primaryForegroundColor: TileColor) = also {
        this.primaryForegroundColor = primaryForegroundColor
    }

    fun withSecondaryForegroundColor(secondaryForegroundColor: TileColor) = also {
        this.secondaryForegroundColor = secondaryForegroundColor
    }

    fun withPrimaryBackgroundColor(primaryBackgroundColor: TileColor) = also {
        this.primaryBackgroundColor = primaryBackgroundColor
    }

    fun withSecondaryBackgroundColor(secondaryBackgroundColor: TileColor) = also {
        this.secondaryBackgroundColor = secondaryBackgroundColor
    }

    fun withAccentColor(accentColor: TileColor) = also {
        this.accentColor = accentColor
    }

    fun build() = DefaultColorTheme(
            primaryForegroundColor = primaryForegroundColor,
            primaryBackgroundColor = primaryBackgroundColor,
            secondaryForegroundColor = secondaryForegroundColor,
            secondaryBackgroundColor = secondaryBackgroundColor,
            accentColor = accentColor)

    companion object {

        fun newBuilder() = ColorThemeBuilder()
    }
}
