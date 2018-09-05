package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextArea

data class TextAreaBuilder(
        private var text: String = "",
        private var size: Size = Size.one())
    : BaseComponentBuilder<TextArea, TextAreaBuilder>() {

    fun text(text: String) = also {
        this.text = text
    }

    fun size(size: Size) = also {
        this.size = size
    }

    override fun build(): TextArea {
        return DefaultTextArea(
                text = text,
                initialSize = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextAreaBuilder()
    }
}
