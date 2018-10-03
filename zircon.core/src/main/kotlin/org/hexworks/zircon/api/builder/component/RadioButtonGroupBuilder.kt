package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer

data class RadioButtonGroupBuilder(
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<RadioButtonGroup, RadioButtonGroupBuilder>(commonComponentProperties) {

    override fun build(): RadioButtonGroup {
        fillMissingValues()
        val size = decorationRenderers().asSequence()
                .map { it.occupiedSize }
                .fold(size, Size::plus)
        return DefaultRadioButtonGroup(
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultRadioButtonGroupRenderer()),
                size = size,
                position = position,
                componentStyleSet = componentStyleSet,
                tileset = tileset())
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
