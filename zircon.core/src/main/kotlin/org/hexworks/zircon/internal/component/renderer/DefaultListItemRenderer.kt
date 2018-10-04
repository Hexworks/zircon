package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap

class DefaultListItemRenderer(private val listItemChar: Char = '-') : ComponentRenderer<ListItem>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<ListItem>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        CharacterTileStringBuilder.newBuilder()
                .backgroundColor(style.backgroundColor)
                .foregroundColor(style.foregroundColor)
                .modifiers(*style.modifiers.toTypedArray())
                .text("$listItemChar ${context.component.text}")
                .textWrap(TextWrap.WORD_WRAP)
                .build()
                .drawOnto(tileGraphics)
    }
}
