package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse

class DefaultParagraph(
    componentMetadata: ComponentMetadata,
    initialText: String,
    renderingStrategy: ComponentRenderingStrategy<Paragraph>
) : Paragraph, TextOverride by TextOverride.create(initialText), DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toSecondaryContentStyle()

}
