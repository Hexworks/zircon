package org.codetome.zircon.api.builder.component

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.internal.component.impl.DefaultColorTheme

data class ColorThemeBuilder(private var brightForegroundColor: TextColor = TextColor.defaultForegroundColor(),
                             private var brightBackgroundColor: TextColor = TextColor.defaultBackgroundColor(),
                             private var darkForegroundColor: TextColor = TextColor.defaultForegroundColor(),
                             private var darkBackgroundColor: TextColor = TextColor.defaultBackgroundColor(),
                             private var accentColor: TextColor = TextColor.defaultForegroundColor()) {


    fun brightForegroundColor(brightForegroundColor: TextColor) = also {
        this.brightForegroundColor = brightForegroundColor
    }

    fun darkForegroundColor(darkForegroundColor: TextColor) = also {
        this.darkForegroundColor = darkForegroundColor
    }

    fun brightBackgroundColor(brightBackgroundColor: TextColor) = also {
        this.brightBackgroundColor = brightBackgroundColor
    }

    fun darkBackgroundColor(darkBackgroundColor: TextColor) = also {
        this.darkBackgroundColor = darkBackgroundColor
    }

    fun accentColor(accentColor: TextColor) = also {
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
