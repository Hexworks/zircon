package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow

class NoOpGenericRenderer<T : Component> : ComponentRenderer<T> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<T>) {
    }
}
