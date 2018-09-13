package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap

class DefaultLabelRenderer : ComponentRenderer<Label>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Label>) {
        CharacterTileStringBuilder.newBuilder()
                .text(context.component.getText())
                .textWrap(TextWrap.WORD_WRAP)
                .build()
                .drawOnto(tileGraphics)
    }
}
