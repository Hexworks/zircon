package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow

class ComponentRendererStub<T : Component>(
    private val actualRenderer: ComponentRenderer<T> = NoOpGenericRenderer()
) : ComponentRenderer<T> {

    val renderings = mutableListOf<Pair<DrawWindow, ComponentRenderContext<T>>>()

    fun renderedOnce() = renderings.size == 1

    fun clear() = renderings.clear()

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<T>) {
        renderings.add(drawWindow to context)
        actualRenderer.render(drawWindow, context)
    }
}
