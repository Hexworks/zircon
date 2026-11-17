package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

public open class DefaultHBox(
    componentMetadata: ComponentMetadata,
    initialTitle: String,
    spacing: Int,
    renderingStrategy: ComponentRenderingStrategy<HBox>
) : HBox, DefaultLinearBox(
    componentMetadata = componentMetadata,
    initialTitle = initialTitle,
    spacing = spacing,
    axis = Orientation.HORIZONTAL,
    renderingStrategy = renderingStrategy
)
