package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TextHolder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse

class DefaultListItem(
        componentMetadata: ComponentMetadata,
        initialText: String,
        renderingStrategy: ComponentRenderingStrategy<ListItem>
) : ListItem, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy),
        TextHolder by TextHolder.create(initialText) {

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toSecondaryContentStyle()

}
