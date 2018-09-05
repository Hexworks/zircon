package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.impl.wrapping.ButtonWrappingStrategy
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

data class ButtonBuilder(
        private var text: String = "")
    : BaseComponentBuilder<Button, ButtonBuilder>() {

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Button can't be blank!"
        }
        val wrappers = ThreadSafeQueueFactory.create<WrappingStrategy>()
        wrappers.add(ButtonWrappingStrategy())
        return DefaultButton(
                text = text,
                initialSize = Size.create(text.length + 2, 1),
                position = position(),
                componentStyleSet = componentStyleSet(),
                wrappers = wrappers,
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = ButtonBuilder()
    }
}
