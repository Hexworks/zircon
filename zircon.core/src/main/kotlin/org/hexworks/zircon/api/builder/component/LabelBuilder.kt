package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer

data class LabelBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<Label, LabelBuilder>(commonComponentProperties) {

    override fun title(title: String) = also { }

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Label {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        fillMissingValues()
        val size = if (size().isUnknown()) {
            decorationRenderers().map { it.occupiedSize() }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size()
        }
        return DefaultLabel(
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultLabelRenderer()),
                initialSize = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = LabelBuilder()
    }
}
