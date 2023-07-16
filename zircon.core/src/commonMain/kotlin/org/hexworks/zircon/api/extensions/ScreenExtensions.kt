package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * Creates a [PanelBuilder] with a no-op renderer and the size of the
 * [Screen] and [Position] zero, and applies the builder function ([fn]) to it.
 * Note that the size is fixed, and you cannot change it.
 */
fun Screen.useComponentBuilder(fn: PanelBuilder.() -> Unit) {
    val builder = PanelBuilder().apply {
        preferredSize = size
        position = Position.defaultPosition()
    }
    fn(builder)
    // we need to make sure that these were not tampered with
    builder.apply {
        componentRenderer = NoOpComponentRenderer()
        preferredSize = size
        position = Position.defaultPosition()
    }

    addComponent(builder.build())
}
