package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

data class RadioButtonGroupBuilder(
        private var size: Size = Size.one())
    : BaseComponentBuilder<RadioButtonGroup, RadioButtonGroupBuilder>() {

    fun size(size: Size) = also {
        this.size = size
    }

    override fun build(): RadioButtonGroup {
        return DefaultRadioButtonGroup(
                wrappers = ThreadSafeQueueFactory.create(),
                size = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
