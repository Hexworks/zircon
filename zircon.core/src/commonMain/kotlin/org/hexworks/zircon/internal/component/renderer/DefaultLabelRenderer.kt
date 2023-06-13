package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultLabel

class DefaultLabelRenderer : ComponentRenderer<DefaultLabel> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultLabel>) {
        drawWindow.fillWithText(context.component.text, context.currentStyle)
    }
}
