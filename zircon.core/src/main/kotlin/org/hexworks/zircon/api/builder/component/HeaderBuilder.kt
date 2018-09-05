package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultHeader

data class HeaderBuilder(
        private var text: String = "")
    : BaseComponentBuilder<Header, HeaderBuilder>() {

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Header {
        require(text.isNotBlank()) {
            "A Header can't be blank!"
        }
        return DefaultHeader(
                text = text,
                initialSize = Size.create(text.length, 1),
                position = position(),
                componentStyleSet = componentStyleSet(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = HeaderBuilder()
    }
}
