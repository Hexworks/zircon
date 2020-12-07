package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.CharacterTileStrings
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.graphics.TileGraphics

/**
 * A [ComponentRenderer] is responsible for rendering a component's contents onto a [TileGraphics].
 */
interface ComponentRenderer<T : Component> : DecorationRenderer<ComponentRenderContext<T>> {

    /**
     * Fills this [TileGraphics] with the given [text] and [style].
     * Overwrites any existing content.
     */
    fun TileGraphics.fillWithText(
        text: String,
        style: StyleSet,
        textWrap: TextWrap = TextWrap.WRAP
    ) {
        clear()
        draw(
            CharacterTileStrings
                .newBuilder()
                .withText(text)
                .withSize(size)
                .withTextWrap(textWrap)
                .build()
        )
        applyStyle(style)
    }
}
