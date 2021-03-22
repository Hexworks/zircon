package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class RadioButtonBuilder(
    private var text: String = "",
    private var key: Maybe<String> = Maybe.empty()
) : BaseComponentBuilder<RadioButton, RadioButtonBuilder>(DefaultRadioButtonRenderer()) {

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
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
            key = key.get(),
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text).apply {
            key.map { actualKey ->
                withKey(actualKey)
            }
        }

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonBuilder()
    }
}
