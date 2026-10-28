package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.behavior.impl.DefaultColorThemeOverride

/**
 * Represents an object that has a [ColorTheme] that can be changed.
 */
public interface ColorThemeOverride : HasColorTheme {

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
    public val themeProperty: Property<ColorTheme>

    public companion object {

        /**
         * Creates a new [ColorThemeOverride] using [initialTheme] for the initial [ColorThemeOverride.theme].
         */
        public fun create(initialTheme: ColorTheme): ColorThemeOverride = create(initialTheme.toProperty())

        public fun create(themeProperty: Property<ColorTheme>): ColorThemeOverride = DefaultColorThemeOverride(themeProperty)
    }
}
