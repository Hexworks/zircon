package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultIcon

class DefaultIconRenderer : ComponentRenderer<DefaultIcon> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultIcon>) {
        drawWindow.draw(context.component.icon, Position.zero())
    }
}
