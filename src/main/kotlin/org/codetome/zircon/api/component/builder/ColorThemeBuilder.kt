package org.codetome.zircon.api.component.builder

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.internal.component.DefaultColorTheme

data class ColorThemeBuilder(private var brightForegroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
                             private var brightBackgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
                             private var darkForegroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
                             private var darkBackgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
                             private var accentColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR) {


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

        @JvmStatic
        fun newBuilder() = ColorThemeBuilder()
    }
}