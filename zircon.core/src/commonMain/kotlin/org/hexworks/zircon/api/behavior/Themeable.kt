package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.behavior.impl.DefaultThemeable

interface Themeable {

    var theme: ColorTheme
    val themeProperty: Property<ColorTheme>

    fun onThemeChanged(fn: (ChangeEvent<ColorTheme>) -> Unit): Subscription {
        return themeProperty.onChange(fn)
    }

    companion object {

        fun create(initialTheme: ColorTheme): Themeable = DefaultThemeable(initialTheme)
    }
}