package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.api.graphics.DrawWindow
import org.hexworks.zircon.internal.component.impl.DefaultParagraph

public class DefaultParagraphRenderer(
    private val textWrap: TextWrap = WORD_WRAP
) : ComponentRenderer<DefaultParagraph> {

    override fun render(drawWindow: DrawWindow, context: ComponentRenderContext<DefaultParagraph>) {
        drawWindow.fillWithText(context.component.text, context.currentStyle, textWrap)
    }
}
