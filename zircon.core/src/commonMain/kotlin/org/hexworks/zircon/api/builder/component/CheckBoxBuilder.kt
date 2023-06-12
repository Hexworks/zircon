package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox.CheckBoxAlignment
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class CheckBoxBuilder : ComponentWithTextBuilder<CheckBox>(
    initialRenderer = DefaultCheckBoxRenderer(),
    initialText = "",
    reservedSpace = DefaultCheckBoxRenderer.DECORATION_WIDTH
) {

    var labelAlignment: CheckBoxAlignment = CheckBoxAlignment.RIGHT

    override fun build(): CheckBox {
        return DefaultCheckBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
            labelAlignment = labelAlignment,
        ).attachListeners()
    }
}

/**
 * Creates a new [CheckBox] using the component builder DSL and returns it.
 */
fun buildCheckBox(init: CheckBoxBuilder.() -> Unit): CheckBox =
    CheckBoxBuilder().apply(init).build()

/**
 * Creates a new [CheckBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [CheckBox].
 */
fun <T : BaseContainerBuilder<*>> T.checkBox(
    init: CheckBoxBuilder.() -> Unit
): CheckBox = buildChildFor(this, CheckBoxBuilder(), init)
