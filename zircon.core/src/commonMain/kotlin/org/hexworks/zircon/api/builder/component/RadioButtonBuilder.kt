package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class RadioButtonBuilder : ComponentWithTextBuilder<RadioButton>(
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
}

/**
 * Creates a new [RadioButton] using the component builder DSL and returns it.
 */
fun buildRadioButton(init: RadioButtonBuilder.() -> Unit): RadioButton =
    RadioButtonBuilder().apply(init).build()

/**
 * Creates a new [RadioButton] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [RadioButton].
 */
fun <T : BaseContainerBuilder<*>> T.radioButton(
    init: RadioButtonBuilder.() -> Unit
): RadioButton = buildChildFor(this, RadioButtonBuilder(), init)
