package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class RadioButtonGroupBuilder(
        private val commonComponentProperties: CommonComponentProperties<RadioButtonGroup> = CommonComponentProperties(
                componentRenderer = DefaultRadioButtonGroupRenderer()))
    : BaseComponentBuilder<RadioButtonGroup, RadioButtonGroupBuilder>(commonComponentProperties) {

    override fun build(): RadioButtonGroup {
        fillMissingValues()
        val size = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(size, Size::plus)
        return DefaultRadioButtonGroup(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<RadioButtonGroup>))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
