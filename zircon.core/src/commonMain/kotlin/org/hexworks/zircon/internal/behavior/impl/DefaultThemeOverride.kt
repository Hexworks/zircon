package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.behavior.ThemeOverride
import org.hexworks.zircon.api.component.ColorTheme

class DefaultThemeOverride(initialTheme: ColorTheme) : ThemeOverride {

    override val themeProperty = createPropertyFrom(initialTheme)
    override var theme: ColorTheme by themeProperty.asDelegate()
}
