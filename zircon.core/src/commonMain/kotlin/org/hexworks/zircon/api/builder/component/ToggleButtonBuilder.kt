package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.renderer.DefaultToggleButtonRenderer

/**
 * Builds a toggle button: a [BinarySelector] with the `TOGGLE_BUTTON` role that can be unselected.
 */
@ZirconDsl
public class ToggleButtonBuilder : BinarySelectorBuilder(
    initialRole = ComponentRole.TOGGLE_BUTTON,
    reservedSpace = DefaultToggleButtonRenderer.DECORATION_WIDTH,
    allowUnselect = true,
)

/**
 * Creates a new toggle button using the component builder DSL and returns it.
 */
public fun toggleButton(init: ToggleButtonBuilder.() -> Unit): BinarySelector =
    ToggleButtonBuilder().apply(init).build()

/**
 * Creates a new toggle button using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns it.
 */
public fun <T : BaseContainerBuilder<*>> T.toggleButton(
    init: ToggleButtonBuilder.() -> Unit
): BinarySelector = buildChildFor(this, ToggleButtonBuilder(), init)
