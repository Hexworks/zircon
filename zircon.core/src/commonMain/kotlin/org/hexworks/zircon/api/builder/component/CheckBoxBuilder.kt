package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
class CheckBoxBuilder(
        private var text: String = "",
        private var labelAlignment: CheckBoxAlignment = CheckBoxAlignment.RIGHT)
    : BaseComponentBuilder<CheckBox, CheckBoxBuilder>(DefaultCheckBoxRenderer()) {

    fun withText(text: String) = also {
        this.text = text
        val totalSize =
                if (text == "")
                    DefaultCheckBoxRenderer.BUTTON_WIDTH
                else
                    text.length + DefaultCheckBoxRenderer.DECORATION_WIDTH
        contentSize = contentSize
                .withWidth(max(totalSize, contentSize.width))
    }

    fun withLeftAlignedText() = also {
        labelAlignment = CheckBoxAlignment.LEFT
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                labelAlignment = labelAlignment,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<CheckBox>)).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
            .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder().apply { contentSize = Size.create(DefaultCheckBoxRenderer.BUTTON_WIDTH,1) }
    }
}
