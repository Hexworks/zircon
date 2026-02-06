package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultHeader

class DefaultHeaderRenderer : ComponentRenderer<DefaultHeader> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultHeader>) {
        drawWindow.fillWithText(context.component.text, context.currentStyle, textWrap = WORD_WRAP)
    }
}
