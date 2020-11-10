package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

open class DefaultPanel(
        componentMetadata: ComponentMetadata,
        initialTitle: String,
        renderingStrategy: ComponentRenderingStrategy<Panel>
) : Panel, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy), TitleOverride by TitleOverride.create(initialTitle) {

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toContainerStyle()

}
