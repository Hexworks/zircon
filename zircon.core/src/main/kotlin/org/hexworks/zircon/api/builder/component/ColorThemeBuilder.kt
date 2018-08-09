package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.component.impl.DefaultColorTheme

data class ColorThemeBuilder(private var brightForegroundColor: TileColor = TileColor.defaultForegroundColor(),
                             private var brightBackgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                             private var darkForegroundColor: TileColor = TileColor.defaultForegroundColor(),
                             private var darkBackgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                             private var accentColor: TileColor = TileColor.defaultForegroundColor()) {


    fun brightForegroundColor(brightForegroundColor: TileColor) = also {
        this.brightForegroundColor = brightForegroundColor
    }

    fun darkForegroundColor(darkForegroundColor: TileColor) = also {
        this.darkForegroundColor = darkForegroundColor
    }

    fun brightBackgroundColor(brightBackgroundColor: TileColor) = also {
        this.brightBackgroundColor = brightBackgroundColor
    }

    fun darkBackgroundColor(darkBackgroundColor: TileColor) = also {
        this.darkBackgroundColor = darkBackgroundColor
    }

    fun accentColor(accentColor: TileColor) = also {
        this.accentColor = accentColor
    }

    fun build() = DefaultColorTheme(
            brightForegroundColor = brightForegroundColor,
            brightBackgroundColor = brightBackgroundColor,
            darkForegroundColor = darkForegroundColor,
            darkBackgroundColor = darkBackgroundColor,
            accentColor = accentColor)

    companion object {

        fun newBuilder() = ColorThemeBuilder()
    }
}
