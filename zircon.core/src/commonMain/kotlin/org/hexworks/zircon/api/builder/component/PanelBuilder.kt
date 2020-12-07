package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
class PanelBuilder
    : BaseComponentBuilder<Panel, PanelBuilder>(DefaultPanelRenderer()) {

    override fun build(): Panel {
        return DefaultPanel(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialTitle = title,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = PanelBuilder()
    }
}
