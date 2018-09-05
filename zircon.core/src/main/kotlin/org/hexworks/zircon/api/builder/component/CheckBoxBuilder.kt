package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

data class CheckBoxBuilder(
        private var text: String = "",
        private var width: Int = -1)
    : BaseComponentBuilder<CheckBox, CheckBoxBuilder>() {

    fun text(text: String) = also {
        this.text = text
    }

    fun width(width: Int) = also {
        this.width = width
    }

    override fun build(): CheckBox {
        return DefaultCheckBox(
                text = text,
                width = if (width == -1) text.length + 4 else width,
                position = position(),
                componentStyleSet = componentStyleSet(),
                wrappers = ThreadSafeQueueFactory.create(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = CheckBoxBuilder()
    }
}
