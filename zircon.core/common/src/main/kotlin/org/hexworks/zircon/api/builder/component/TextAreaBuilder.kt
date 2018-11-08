package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class TextAreaBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties<TextArea> = CommonComponentProperties(
                componentRenderer = DefaultTextAreaRenderer()))
    : BaseComponentBuilder<TextArea, TextAreaBuilder>(commonComponentProperties) {

    fun withText(text: String) = also {
        this.text = text
    }

    override fun build(): TextArea {
        fillMissingValues()
        val size = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(size, Size::plus)
        return DefaultTextArea(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<TextArea>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = TextAreaBuilder()
    }
}
