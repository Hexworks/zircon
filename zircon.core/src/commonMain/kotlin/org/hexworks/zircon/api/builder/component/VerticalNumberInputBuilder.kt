package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalNumberInputRenderer

@ZirconDsl
public class VerticalNumberInputBuilder : NumberInputBuilder<NumberInput>(
    initialRenderer = DefaultVerticalNumberInputRenderer()
) {

    override fun calculateContentSize(): Size {
        val length = this.maxValue.toString().length + 1
        return if (contentWidth < length) {
            Size(1, length)
        } else preferredContentSize
    }

    override fun build(): NumberInput = DefaultVerticalNumberInput(
        componentMetadata = createMetadata(),
        renderingStrategy = createRenderingStrategy(),
        initialValue = initialValue,
        minValue = minValue,
        maxValue = maxValue,
    ).attachListeners()
}

/**
 * Creates a new [NumberInput] using the component builder DSL and returns it.
 */
public fun verticalNumberInput(
    init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = VerticalNumberInputBuilder().apply(init).build()

/**
 * Creates a new [NumberInput] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [NumberInput].
 */
public fun <T : BaseContainerBuilder<*>> T.verticalNumberInput(
    init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = buildChildFor(this, VerticalNumberInputBuilder(), init)