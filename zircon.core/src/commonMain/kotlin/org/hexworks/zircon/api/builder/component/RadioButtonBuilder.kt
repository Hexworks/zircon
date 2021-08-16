package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class RadioButtonBuilder private constructor() : ComponentWithTextBuilder<RadioButton, RadioButtonBuilder>(
    initialRenderer = DefaultRadioButtonRenderer(),
    initialText = "",
    reservedSpace = DefaultRadioButtonRenderer.DECORATION_WIDTH
) {

    var key: String = ""

    fun withKey(key: String) = also {
        this.key = key
    }

    override fun build(): RadioButton {
        require(key.isNotBlank()) {
            "Can't create a Radio Button without a key."
        }
        return DefaultRadioButton(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = text,
            key = key,
        ).attachListeners()
    }

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withText(text)
        .withKey(key)

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonBuilder()
    }
}
