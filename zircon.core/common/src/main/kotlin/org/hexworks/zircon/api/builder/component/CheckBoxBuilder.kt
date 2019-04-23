package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class CheckBoxBuilder(
        private var text: String = "",
        private var width: Int = -1,
        private val commonComponentProperties: CommonComponentProperties<CheckBox> = CommonComponentProperties(
                componentRenderer = DefaultCheckBoxRenderer()))
    : BaseComponentBuilder<CheckBox, CheckBoxBuilder>(commonComponentProperties) {

    fun withText(text: String) = also {
        this.text = text
    }

    fun withWidth(width: Int) = also {
        this.width = width
    }

    override fun build(): CheckBox {
        fillMissingValues()
        val finalSize = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(Size.zero(), Size::plus)
                .plus(Size.create(if (width == -1) text.length + 4 else width, 1))
        return DefaultCheckBox(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = fixPosition(finalSize),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<CheckBox>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}
