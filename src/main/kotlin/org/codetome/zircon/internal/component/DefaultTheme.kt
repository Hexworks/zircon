package org.codetome.zircon.internal.component

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.component.Theme

data class DefaultTheme(private val brightForegroundColor: TextColor,
                        private val brightBackgroundColor: TextColor,
                        private val darkForegroundColor: TextColor,
                        private val darkBackgroundColor: TextColor,
                        private val accentColor: TextColor): Theme {

    override fun getBrightForegroundColor() = brightForegroundColor

    override fun getDarkForegroundColor() = darkForegroundColor

    override fun getBrightBackgroundColor() = brightBackgroundColor

    override fun getDarkBackgroundColor() = darkBackgroundColor

    override fun getAccentColor() = accentColor
}