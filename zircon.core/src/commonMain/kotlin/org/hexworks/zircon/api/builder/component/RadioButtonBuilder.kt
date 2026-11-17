package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.renderer.DefaultRadioButtonRenderer

/**
 * Builds a radio button: a [BinarySelector] with the `RADIO_BUTTON` role that is only
 * unselected by its [org.hexworks.zircon.api.component.ExclusiveSelectionGroup].
 */
@ZirconDsl
public class RadioButtonBuilder : BinarySelectorBuilder(
    initialRole = ComponentRole.RADIO_BUTTON,
    reservedSpace = DefaultRadioButtonRenderer.DECORATION_WIDTH,
    allowUnselect = false,
)

/**
 * Creates a new radio button using the component builder DSL and returns it.
 */
public fun radioButton(init: RadioButtonBuilder.() -> Unit): BinarySelector =
    RadioButtonBuilder().apply(init).build()

/**
 * Creates a new radio button using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns it.
 */
public fun <T : BaseContainerBuilder<*>> T.radioButton(
    init: RadioButtonBuilder.() -> Unit
): BinarySelector = buildChildFor(this, RadioButtonBuilder(), init)
