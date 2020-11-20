package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.component.ColorTheme

class DefaultColorThemeOverride(initialTheme: ColorTheme) : ColorThemeOverride {

    override val themeProperty = createPropertyFrom(initialTheme)
    override var theme: ColorTheme by themeProperty.asDelegate()
}
