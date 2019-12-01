package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.behavior.impl.DefaultThemeable
import kotlin.jvm.JvmStatic

/**
 * Represents an object which can be themed using [ColorTheme]s.
 */
interface Themeable {

    var theme: ColorTheme
    val themeProperty: Property<ColorTheme>

    companion object {

        @JvmStatic
        fun create(initialTheme: ColorTheme): Themeable = DefaultThemeable(initialTheme)
    }
}