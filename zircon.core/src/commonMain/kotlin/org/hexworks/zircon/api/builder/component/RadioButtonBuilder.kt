package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class RadioButtonBuilder(
        private var text: String = "",
        private var key: Maybe<String> = Maybe.empty(),
        override val props: CommonComponentProperties<RadioButton> = CommonComponentProperties(
                componentRenderer = DefaultRadioButtonRenderer()))
    : BaseComponentBuilder<RadioButton, RadioButtonBuilder>() {

    fun withKey(key: String) = also {
        this.key = Maybe.of(key)
    }

    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
                .withWidth(max(text.length + DefaultRadioButtonRenderer.DECORATION_WIDTH, contentSize.width))
    }

    override fun build(): RadioButton {
        require(key.isPresent) {
            "Can't create a Radio Button without a key."
        }
        return DefaultRadioButton(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                key = key.get(),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<RadioButton>)).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonBuilder()
    }
}
