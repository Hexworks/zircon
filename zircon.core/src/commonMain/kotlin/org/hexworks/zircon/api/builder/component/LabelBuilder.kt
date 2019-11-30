package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultLabel
import org.hexworks.zircon.internal.component.renderer.DefaultLabelRenderer
import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class LabelBuilder(
        private var text: String = "",
        override val props: CommonComponentProperties<Label> = CommonComponentProperties(
                componentRenderer = DefaultLabelRenderer()))
    : BaseComponentBuilder<Label, LabelBuilder>() {


    fun withText(text: String) = also {
        this.text = text
                .split(SystemUtils.getLineSeparator())
                .first()
                .split("\n")
                .first()
        contentSize = contentSize
                .withWidth(max(this.text.length, contentSize.width))
    }

    override fun build(): Label {
        return DefaultLabel(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<Label>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = LabelBuilder()
    }
}
