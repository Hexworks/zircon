package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap

class DefaultParagraphRenderer : ComponentRenderer<Paragraph> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Paragraph>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        CharacterTileStringBuilder.newBuilder()
                .withBackgroundColor(style.backgroundColor)
                .withForegroundColor(style.foregroundColor)
                .withModifiers(*style.modifiers.toTypedArray())
                .withText(context.component.text)
                .withTextWrap(TextWrap.WORD_WRAP)
                .build()
                .drawOnto(tileGraphics)
    }
}
