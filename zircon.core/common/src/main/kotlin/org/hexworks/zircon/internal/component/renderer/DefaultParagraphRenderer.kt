package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultParagraph

class DefaultParagraphRenderer : ComponentRenderer<DefaultParagraph> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<DefaultParagraph>) {
        tileGraphics.fillWithText(context.component.text, context.currentStyle)
    }
}
