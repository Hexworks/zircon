package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import kotlin.jvm.JvmStatic

data class CheckBoxBuilder(
        private var text: String = "",
        private var width: Int = -1,
        private val commonComponentProperties: CommonComponentProperties<CheckBox> = CommonComponentProperties())
    : BaseComponentBuilder<CheckBox, CheckBoxBuilder>(commonComponentProperties) {

    fun withText(text: String) = also {
        this.text = text
    }

    fun withWidth(width: Int) = also {
        this.width = width
    }

    override fun build(): CheckBox {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        fillMissingValues()
        val size = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(Size.zero(), Size::plus)
                .plus(Size.create(if (width == -1) text.length + 4 else width, 1))
        return DefaultCheckBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = DefaultCheckBoxRenderer()))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = CheckBoxBuilder()
    }
}
