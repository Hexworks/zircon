package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.ButtonSideDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class ButtonBuilder(
        private var text: String = "",
        private var wrapSides: Boolean = true,
        private val commonComponentProperties: CommonComponentProperties<Button> = CommonComponentProperties(
                componentRenderer = DefaultButtonRenderer()))
    : BaseComponentBuilder<Button, ButtonBuilder>(commonComponentProperties) {

    override fun withTitle(title: String): ButtonBuilder {
        throw UnsupportedOperationException("You can't set a title for a button")
    }

    fun wrapSides(wrapSides: Boolean) = also {
        this.wrapSides = wrapSides
    }

    fun withText(text: String) = also {
        this.text = text
    }

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Button can't be blank!"
        }
        fillMissingValues()
        var renderers = decorationRenderers
        if (wrapSides) {
            renderers += ButtonSideDecorationRenderer()
        }
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = renderers,
                componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<Button>)
        val finalSize = if (size.isUnknown()) {
            renderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        return DefaultButton(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                text = text,
                renderingStrategy = componentRenderer)
    }

    override fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): ButtonBuilder {
        wrapSides(false)
        return super.withDecorationRenderers(*renderers)
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}
