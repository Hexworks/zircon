package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalContainer
import kotlin.jvm.Synchronized

class RootContainer(
        componentMetadata: ComponentMetadata,
        renderingStrategy: ComponentRenderingStrategy<RootContainer>
) : Container, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = renderingStrategy) {

    init {
        render()
    }

    // the Root Container is always attached
    override val isAttached: Boolean
        get() = true

    override fun acceptsFocus() = true

    override fun focusGiven() = Processed

    override fun focusTaken() = Processed

    override fun calculatePathFromRoot() = listOf<InternalContainer>(this)

    @Synchronized
    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSetBuilder.newBuilder()
                    .withForegroundColor(colorTheme.secondaryForegroundColor)
                    .withBackgroundColor(colorTheme.secondaryBackgroundColor)
                    .build())
            .build()

}
