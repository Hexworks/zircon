package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.decoration.SideDecorationRenderer
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class ButtonBuilder(
        private var text: String = "",
        private var wrapSides: Boolean = true,
        override val props: CommonComponentProperties<Button> = CommonComponentProperties(
                decorationRenderers = listOf(SideDecorationRenderer()),
                componentRenderer = DefaultButtonRenderer()))
    : BaseComponentBuilder<Button, ButtonBuilder>() {


    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
                .withWidth(max(text.length, contentSize.width))
    }

    override fun build(): Button {
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = decorationRenderers,
                componentRenderer = componentRenderer as ComponentRenderer<Button>)
        return DefaultButton(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = componentRenderer)
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = ButtonBuilder()
    }
}
