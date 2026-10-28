package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultButton

public class DefaultButtonRenderer : ComponentRenderer<DefaultButton> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultButton>) {
        drawWindow.fillWithText(context.component.text, context.currentStyle)
    }

}
