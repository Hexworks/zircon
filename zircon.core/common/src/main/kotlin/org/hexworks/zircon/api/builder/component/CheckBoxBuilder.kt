package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class CheckBoxBuilder(
        private var text: String = "",
        override val props: CommonComponentProperties<CheckBox> = CommonComponentProperties(
                componentRenderer = DefaultCheckBoxRenderer()))
    : BaseComponentBuilder<CheckBox, CheckBoxBuilder>() {

    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
                .withWidth(max(text.length + DefaultCheckBoxRenderer.DECORATION_WIDTH, contentSize.width))
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<CheckBox>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}
