package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.CharacterTileStringBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultListItem

class DefaultListItemRenderer(private val listItemChar: Char = '-') : ComponentRenderer<DefaultListItem> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultListItem>) {
        val style = context.componentStyle.currentStyle()
        tileGraphics.applyStyle(style)
        CharacterTileStringBuilder.newBuilder()
                .withBackgroundColor(style.backgroundColor)
                .withForegroundColor(style.foregroundColor)
                .withModifiers(*style.modifiers.toTypedArray())
                .withText("$listItemChar ${context.component.text}")
                .withTextWrap(TextWrap.WORD_WRAP)
                .build()
                .drawOnto(tileGraphics)
    }
}
