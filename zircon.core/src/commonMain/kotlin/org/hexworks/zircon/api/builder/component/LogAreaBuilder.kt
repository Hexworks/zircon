package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class LogAreaBuilder(
        private var logRowHistorySize: Int = 100,
        override val props: CommonComponentProperties<LogArea> = CommonComponentProperties(
                componentRenderer = DefaultLogAreaRenderer()))
    : BaseComponentBuilder<LogArea, LogAreaBuilder>() {

    fun withLogRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    override fun build(): LogArea {
        return DefaultLogArea(
                componentMetadata = ComponentMetadata(
                        position = position,
                        size = size,
                        tileset = tileset,
                        componentStyleSet = componentStyleSet),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<LogArea>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = LogAreaBuilder()
    }
}
