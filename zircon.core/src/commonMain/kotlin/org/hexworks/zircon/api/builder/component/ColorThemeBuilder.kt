package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.component.impl.DefaultColorTheme
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@ZirconDsl
class ColorThemeBuilder : Builder<ColorTheme> {

    var name: String = "anonymous"
    var primaryForegroundColor: TileColor = TileColor.defaultForegroundColor()
    var secondaryForegroundColor: TileColor = TileColor.defaultForegroundColor()
    var primaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor()
    var secondaryBackgroundColor: TileColor = TileColor.defaultBackgroundColor()
    var accentColor: TileColor = TileColor.defaultForegroundColor()

    fun withName(name: String) = also {
        this.name = name
    }

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

    override fun build(): ColorTheme = DefaultColorTheme(
        name = name,
        primaryForegroundColor = primaryForegroundColor,
        primaryBackgroundColor = primaryBackgroundColor,
        secondaryForegroundColor = secondaryForegroundColor,
        secondaryBackgroundColor = secondaryBackgroundColor,
        accentColor = accentColor
    )

    override fun createCopy() = newBuilder()
        .withPrimaryForegroundColor(primaryForegroundColor)
        .withPrimaryBackgroundColor(primaryBackgroundColor)
        .withSecondaryForegroundColor(secondaryForegroundColor)
        .withSecondaryBackgroundColor(secondaryBackgroundColor)
        .withAccentColor(accentColor)


    companion object {

        @JvmStatic
        fun newBuilder() = ColorThemeBuilder()
    }
}
