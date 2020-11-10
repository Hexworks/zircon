package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.behavior.impl.DefaultThemeOverride
import kotlin.jvm.JvmStatic

/**
 * Represents an object that has a [ColorTheme] that can be changed.
 */
// TODO: mention in the release notes that HasColorTheme + ColorThemeOverride was created
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface ThemeOverride : Themeable, HasColorTheme {

    /**
     * The (mutable) [ColorTheme].
     */
    override var theme: ColorTheme

    /**
     * A [Property] that wraps the [theme] and offers data binding and
     * observability features.
     *
     * @see Property
     */
    override val themeProperty: Property<ColorTheme>

    companion object {

        /**
         * Creates a new [ThemeOverride] using [initialTheme] for the initial [ThemeOverride.theme].
         */
        @JvmStatic
        fun create(initialTheme: ColorTheme): ThemeOverride = DefaultThemeOverride(initialTheme)
    }
}
