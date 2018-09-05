package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultLabel

data class LabelBuilder(private var text: String = "")
    : BaseComponentBuilder<Label, LabelBuilder>() {

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Label {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        return DefaultLabel(
                text = text,
                initialSize = Size.create(text.length, 1),
                initialTileset = tileset(),
                position = position(),
                componentStyleSet = componentStyleSet())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = LabelBuilder()
    }
}
