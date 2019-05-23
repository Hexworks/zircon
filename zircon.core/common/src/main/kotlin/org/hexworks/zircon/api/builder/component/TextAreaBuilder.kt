package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultTextArea
import org.hexworks.zircon.internal.component.renderer.DefaultTextAreaRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class TextAreaBuilder(
        private var text: String = "",
        override val props: CommonComponentProperties<TextArea> = CommonComponentProperties(
                componentRenderer = DefaultTextAreaRenderer()))
    : BaseComponentBuilder<TextArea, TextAreaBuilder>() {

    fun withText(text: String) = also {
        this.text = text
        withWidth(max(preferredSize.width, text.length))
    }

    override fun build(): TextArea {
        return DefaultTextArea(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<TextArea>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = TextAreaBuilder()
    }
}
