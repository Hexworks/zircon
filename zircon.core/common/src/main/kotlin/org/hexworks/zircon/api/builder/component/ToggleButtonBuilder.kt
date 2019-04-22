package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.ToggleButton
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.ButtonSideDecorationRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultToggleButton
import org.hexworks.zircon.internal.component.renderer.DefaultButtonRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class ToggleButtonBuilder(
        private var text: String = "",
        private var wrapSides: Boolean = true,
        private var isSelected:Boolean = false,
        private val commonComponentProperties: CommonComponentProperties<ToggleButton> = CommonComponentProperties(
                componentRenderer = DefaultToggleButtonRenderer()))
    : BaseComponentBuilder<ToggleButton, ToggleButtonBuilder>(commonComponentProperties) {

    override fun withTitle(title: String): ToggleButtonBuilder {
        throw UnsupportedOperationException("You can't set a title for a button")
    }

    fun wrapSides(wrapSides: Boolean) = also {
        this.wrapSides = wrapSides
    }

    fun withText(text: String) = also {
        this.text = text
    }

    fun withIsSelected(isSelected: Boolean) = also {
        this.isSelected = isSelected
    }

    override fun build(): ToggleButton {
        fillMissingValues()
        var renderers = decorationRenderers
        if (wrapSides) {
            renderers += ButtonSideDecorationRenderer()
        }
        val componentRenderer = DefaultComponentRenderingStrategy(
                decorationRenderers = renderers,
                componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<ToggleButton>)
        val finalSize = if (size.isUnknown()) {
            renderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        return DefaultToggleButton(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                initialSelected = isSelected,
                renderingStrategy = componentRenderer)
    }

    override fun withDecorationRenderers(vararg renderers: ComponentDecorationRenderer): ToggleButtonBuilder {
        wrapSides(false)
        return super.withDecorationRenderers(*renderers)
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = ToggleButtonBuilder()
    }
}
