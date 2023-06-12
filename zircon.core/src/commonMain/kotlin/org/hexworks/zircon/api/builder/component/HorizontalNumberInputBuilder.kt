package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultHorizontalNumberInput
import org.hexworks.zircon.internal.component.renderer.DefaultNumberInputRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class HorizontalNumberInputBuilder :
    NumberInputBuilder<NumberInput>(
        initialRenderer = DefaultNumberInputRenderer()
    ) {

    override fun calculateContentSize(): Size {
        val length = this.maxValue.toString().length + 1
        return if (contentWidth < length) {
            Size.create(length, 1)
        } else preferredContentSize
    }

    override fun build(): NumberInput = DefaultHorizontalNumberInput(
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
fun buildHorizontalNumberInput(
    init: HorizontalNumberInputBuilder.() -> Unit
): NumberInput =
    HorizontalNumberInputBuilder().apply(init).build()

/**
 * Creates a new [NumberInput] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [NumberInput].
 */
fun <T : BaseContainerBuilder<*>> T.horizontalNumberInput(
    init: HorizontalNumberInputBuilder.() -> Unit
): NumberInput = buildChildFor(this, HorizontalNumberInputBuilder(), init)