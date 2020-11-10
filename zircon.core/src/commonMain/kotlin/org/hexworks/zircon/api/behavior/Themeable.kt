package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.behavior.impl.DefaultThemeOverride
import kotlin.jvm.JvmStatic

/**
 * Represents an object that has a [ColorTheme] that can be changed.
 */
// TODO: mention in the release notes that HasColorTheme + ColorThemeOverride was created
@Deprecated("This interface was renamed to ThemeOverride, Themeable will be removed in the next release.")
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Themeable : HasColorTheme {

    override var theme: ColorTheme

    val themeProperty: Property<ColorTheme>

    companion object {

        @JvmStatic
        fun create(initialTheme: ColorTheme): ThemeOverride = DefaultThemeOverride(initialTheme)
    }
}
