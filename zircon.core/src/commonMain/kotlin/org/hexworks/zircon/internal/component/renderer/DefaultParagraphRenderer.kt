package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.fillWithText
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultParagraph

class DefaultParagraphRenderer(
    private val textWrap: TextWrap = WORD_WRAP
) : ComponentRenderer<DefaultParagraph> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultParagraph>) {
        tileGraphics.fillWithText(context.component.text, context.currentStyle, textWrap)
    }
}
