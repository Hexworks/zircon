package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentProperties
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultComponentProperties(
        initialDisabled: Boolean,
        initialHidden: Boolean,
        initialTheme: ColorTheme,
        initialTileset: TilesetResource
) : ComponentProperties {

    override val disabledProperty = initialDisabled.toProperty()
    override var isDisabled: Boolean by disabledProperty.asDelegate()

    override val hiddenProperty = initialHidden.toProperty()
    override var isHidden: Boolean by hiddenProperty.asDelegate()

    override val themeProperty = initialTheme.toProperty()
    override var theme: ColorTheme by themeProperty.asDelegate()

    override val tilesetProperty = initialTileset.toProperty {
        tileset.isCompatibleWith(it)
    }
    override var tileset: TilesetResource by tilesetProperty.asDelegate()
}