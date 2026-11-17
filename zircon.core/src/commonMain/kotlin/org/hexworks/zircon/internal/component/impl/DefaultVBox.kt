package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy

public class DefaultVBox internal constructor(
    componentMetadata: ComponentMetadata,
    initialTitle: String,
    spacing: Int,
    renderingStrategy: ComponentRenderingStrategy<VBox>
) : VBox, DefaultLinearBox(
    componentMetadata = componentMetadata,
    initialTitle = initialTitle,
    spacing = spacing,
    axis = Orientation.VERTICAL,
    renderingStrategy = renderingStrategy
)
