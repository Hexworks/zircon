package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultVerticalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultVerticalNumberInputRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class VerticalNumberInputBuilder : NumberInputBuilder<NumberInput>(
    initialRenderer = DefaultVerticalNumberInputRenderer()
) {

    override fun calculateContentSize(): Size {
        val length = this.maxValue.toString().length + 1
        return if (contentWidth < length) {
            Size.create(1, length)
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
fun buildVerticalNumberInput(
    init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = VerticalNumberInputBuilder().apply(init).build()

/**
 * Creates a new [NumberInput] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [NumberInput].
 */
fun <T : BaseContainerBuilder<*>> T.verticalNumberInput(
    init: VerticalNumberInputBuilder.() -> Unit
): NumberInput = buildChildFor(this, VerticalNumberInputBuilder(), init)