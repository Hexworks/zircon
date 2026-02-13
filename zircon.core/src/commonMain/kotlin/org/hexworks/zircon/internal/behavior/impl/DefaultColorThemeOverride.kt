package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.component.ColorTheme

class DefaultColorThemeOverride(
    override val themeProperty: Property<ColorTheme>
) : ColorThemeOverride {
    override var theme: ColorTheme by themeProperty.asDelegate()
}
