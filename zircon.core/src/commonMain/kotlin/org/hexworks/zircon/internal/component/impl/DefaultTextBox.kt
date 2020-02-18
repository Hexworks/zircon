package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse

class DefaultTextBox(
        componentMetadata: ComponentMetadata,
        renderingStrategy: ComponentRenderingStrategy<TextBox>
) : TextBox, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    init {
        render()
    }

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toSecondaryContentStyle()

}
