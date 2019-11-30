package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.component.ColorTheme

class DefaultThemeable(initialTheme: ColorTheme) : Themeable {

    override val themeProperty = createPropertyFrom(initialTheme)
    override var theme: ColorTheme by themeProperty.asDelegate()
}
