package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonGroupRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class RadioButtonGroupBuilder(
        override val props: CommonComponentProperties<RadioButtonGroup> = CommonComponentProperties(
                componentRenderer = DefaultRadioButtonGroupRenderer()))
    : BaseComponentBuilder<RadioButtonGroup, RadioButtonGroupBuilder>() {

    override fun build(): RadioButtonGroup {
        return DefaultRadioButtonGroup(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<RadioButtonGroup>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
