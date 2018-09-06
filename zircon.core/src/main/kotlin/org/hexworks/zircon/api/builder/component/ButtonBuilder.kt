package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.DefaultButton
import org.hexworks.zircon.internal.component.impl.wrapping.BorderComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.BoxComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.ButtonComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowComponentDecorationRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

data class ButtonBuilder(
        private var text: String = "",
        private var boxType: BoxType = BoxType.SINGLE,
        private var wrapWithBox: Boolean = false,
        private var wrapWithShadow: Boolean = false,
        private var wrapSides: Boolean = true,
        private var border: Maybe<Border> = Maybe.empty(),
        private var wrappers: MutableList<ComponentDecorationRenderer> = mutableListOf())
    : BaseComponentBuilder<Button, ButtonBuilder>() {

    fun wrapWithBox(wrapWithBox: Boolean) = also {
        this.wrapWithBox = wrapWithBox
    }

    fun boxType(boxType: BoxType) = also {
        this.boxType = boxType
    }

    fun wrapWithShadow(wrapWithShadow: Boolean) = also {
        this.wrapWithShadow = wrapWithShadow
    }

    fun wrapSides(wrapSides: Boolean) = also {
        this.wrapSides = wrapSides
    }

    fun wrapper(decorationRenderer: ComponentDecorationRenderer) = also {
        wrappers.add(decorationRenderer)
    }

    fun addBorder(border: Border) = also {
        this.border = Maybe.of(border)
    }

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Button {
        require(text.isNotBlank()) {
            "A Button can't be blank!"
        }
        val finalWrappers = ThreadSafeQueueFactory.create<ComponentDecorationRenderer>()
        if(wrappers.isNotEmpty()) {
            finalWrappers.addAll(wrappers)
        } else {
            if (wrapSides) {
                finalWrappers.add(ButtonComponentDecorationRenderer())
            }
            if (wrapWithBox) {
                finalWrappers.add(BoxComponentDecorationRenderer(
                        boxType = boxType))
            }
            if (border.isPresent) {
                finalWrappers.add(BorderComponentDecorationRenderer(border.get()))
            }
            if (wrapWithShadow) {
                finalWrappers.add(ShadowComponentDecorationRenderer())
            }
        }
        val size = finalWrappers.map { it.getOccupiedSize() }
                .fold(Size.create(text.length, 1), Size::plus)
        return DefaultButton(
                text = text,
                initialSize = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                wrappers = finalWrappers,
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = ButtonBuilder()
    }
}
