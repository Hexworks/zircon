package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap

class DefaultHeaderRenderer : ComponentRenderer<Header>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<Header>) {
        val style = context.componentStyle().currentStyle()
        tileGraphics.applyStyle(style)
        CharacterTileStringBuilder.newBuilder()
                .backgroundColor(style.backgroundColor())
                .foregroundColor(style.foregroundColor())
                .modifiers(*style.modifiers().toTypedArray())
                .text(context.component.text())
                .textWrap(TextWrap.WORD_WRAP)
                .build()
                .drawOnto(tileGraphics)
    }
}
