package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.renderer.impl.ButtonSideDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer

data class ButtonBuilder(
        private var text: String = "",
        private var wrapSides: Boolean = true,
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<Button, ButtonBuilder>(commonComponentProperties) {

    override fun title(title: String): ButtonBuilder {
        throw UnsupportedOperationException("You can't set a title for a button")
    }

    fun wrapSides(wrapSides: Boolean) = also {
        this.wrapSides = wrapSides
    }

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Button can't be blank!"
        }
        fillMissingValues()
        var renderers = decorationRenderers()
        if (wrapSides) {
            renderers += ButtonSideDecorationRenderer()
        }
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = renderers,
                componentRenderer = DefaultButtonRenderer())
        val size = if (size().isUnknown()) {
            renderers.map { it.occupiedSize() }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size()
        }
        return DefaultButton(
                text = text,
                renderingStrategy = componentRenderer,
                size = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                tileset = tileset())
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = ButtonBuilder()
    }
}
