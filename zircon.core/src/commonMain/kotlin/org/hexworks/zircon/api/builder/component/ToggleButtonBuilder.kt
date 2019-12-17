package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class ToggleButtonBuilder(
        private var text: String = "",
        private var wrapSides: Boolean = true,
        private var isSelected: Boolean = false,
        override val props: CommonComponentProperties<ToggleButton> = CommonComponentProperties(
                componentRenderer = DefaultToggleButtonRenderer()))
    : BaseComponentBuilder<ToggleButton, ToggleButtonBuilder>() {

    fun withText(text: String) = also {
        this.text = text.withNewLinesStripped()
        contentSize = contentSize
                .withWidth(max(this.text.length, contentSize.width))
    }

    fun withIsSelected(isSelected: Boolean) = also {
        this.isSelected = isSelected
    }

    override fun build(): ToggleButton {
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = decorationRenderers,
                componentRenderer = componentRenderer as ComponentRenderer<ToggleButton>)
        return DefaultToggleButton(
                componentMetadata = ComponentMetadata(
                        relativePosition = position,
                        size = size,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                initialSelected = isSelected,
                renderingStrategy = componentRenderer).also {
            if(colorTheme !== ColorThemes.default()) {
                it.theme = colorTheme
            }
        }
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = ToggleButtonBuilder()
    }
}
