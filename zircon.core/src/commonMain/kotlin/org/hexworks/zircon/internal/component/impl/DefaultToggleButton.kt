package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith

@Suppress("DuplicatedCode")
class DefaultToggleButton(
    componentMetadata: ComponentMetadata,
    initialText: String,
    initialSelected: Boolean,
    renderingStrategy: ComponentRenderingStrategy<ToggleButton>
) : ToggleButton, DefaultComponent(
    componentMetadata = componentMetadata,
    renderer = renderingStrategy
),
    TextOverride by TextOverride.create(initialText),
    Selectable by Selectable.create(initialSelected) {

    override fun activated() = whenEnabledRespondWith {
        isSelected = isSelected.not()
        super.activated()
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toInteractiveStyle()

}
