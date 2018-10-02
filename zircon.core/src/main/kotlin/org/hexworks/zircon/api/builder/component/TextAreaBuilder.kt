package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer

data class TextAreaBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<TextArea, TextAreaBuilder>(commonComponentProperties) {

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): TextArea {
        fillMissingValues()
        val size = decorationRenderers().asSequence()
                .map { it.occupiedSize() }
                .fold(size, Size::plus)
        return DefaultTextArea(
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultTextAreaRenderer()),
                size = size,
                position = position,
                componentStyleSet = componentStyleSet(),
                tileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextAreaBuilder()
    }
}
