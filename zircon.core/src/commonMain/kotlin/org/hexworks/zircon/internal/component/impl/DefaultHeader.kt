package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse

class DefaultHeader(
    componentMetadata: ComponentMetadata,
    initialText: String,
    renderingStrategy: ComponentRenderingStrategy<Header>
) : Header, DefaultComponent(
    componentMetadata = componentMetadata,
    renderer = renderingStrategy
),
    TextOverride by TextOverride.create(initialText) {

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toPrimaryContentStyle()
}
