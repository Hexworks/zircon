package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.renderer.DefaultCheckBoxRenderer

/**
 * Builds a check box: a [BinarySelector] with the `CHECK_BOX` role that can be unselected.
 */
@ZirconDsl
public class CheckBoxBuilder : BinarySelectorBuilder(
    initialRole = ComponentRole.CHECK_BOX,
    reservedSpace = DefaultCheckBoxRenderer.DECORATION_WIDTH,
    allowUnselect = true,
)

/**
 * Creates a new check box using the component builder DSL and returns it.
 */
public fun checkBox(init: CheckBoxBuilder.() -> Unit): BinarySelector =
    CheckBoxBuilder().apply(init).build()

/**
 * Creates a new check box using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns it.
 */
public fun <T : BaseContainerBuilder<*>> T.checkBox(
    init: CheckBoxBuilder.() -> Unit
): BinarySelector = buildChildFor(this, CheckBoxBuilder(), init)
