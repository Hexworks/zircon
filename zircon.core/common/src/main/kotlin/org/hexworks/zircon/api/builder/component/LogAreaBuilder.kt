package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class LogAreaBuilder(
        private var logRowHistorySize: Int = 100,
        private val commonComponentProperties: CommonComponentProperties<LogArea> = CommonComponentProperties(
                componentRenderer = DefaultLogAreaRenderer()))
    : BaseComponentBuilder<LogArea, LogAreaBuilder>(commonComponentProperties) {

    override fun build(): LogArea {
        require(size != Size.unknown()) {
            "You must set a size for a Log Area!"
        }
        fillMissingValues()
        return DefaultLogArea(
                componentMetadata = ComponentMetadata(
                        position = fixPosition(size),
                        size = size,
                        tileset = tileset,
                        componentStyleSet = componentStyleSet),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<LogArea>))
    }

    fun withLogRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = LogAreaBuilder()
    }
}
