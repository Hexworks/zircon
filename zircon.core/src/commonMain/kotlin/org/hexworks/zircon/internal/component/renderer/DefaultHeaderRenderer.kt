package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.fillWithText
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultHeader

class DefaultHeaderRenderer : ComponentRenderer<DefaultHeader> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultHeader>) {
        tileGraphics.fillWithText(context.component.text, context.currentStyle, textWrap = WORD_WRAP)
    }
}
