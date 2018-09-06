package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.impl.wrapping.BorderComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.BoxComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowComponentDecorationRenderer

data class PanelBuilder(private var boxType: BoxType = BoxType.SINGLE,
                        private var title: String = "",
                        private var size: Size = Size.unknown(),
                        private var drawBox: Boolean = false,
                        private var drawShadow: Boolean = false,
                        private var border: Maybe<Border> = Maybe.empty())
    : BaseComponentBuilder<Panel, PanelBuilder>() {

    fun wrapWithBox() = also {
        drawBox = true
    }

    fun wrapWithShadow() = also {
        drawShadow = true
    }

    fun addBorder(border: Border) = also {
        this.border = Maybe.of(border)
    }

    fun boxType(boxType: BoxType) = also {
        this.boxType = boxType
    }

    fun size(size: Size) = also {
        this.size = size
    }

    fun title(title: String) = also {
        this.title = title
    }

    override fun build(): Panel {
        require(size != Size.unknown()) {
            "You must set a size for a Panel!"
        }
        val wrappers = mutableListOf<ComponentDecorationRenderer>()
        if (drawBox) {
            wrappers.add(BoxComponentDecorationRenderer(
                    boxType = boxType,
                    title = if (title.isNotBlank()) Maybe.of(title) else Maybe.empty()))
        }
        if (border.isPresent) {
            wrappers.add(BorderComponentDecorationRenderer(border.get()))
        }
        if (drawShadow) {
            wrappers.add(ShadowComponentDecorationRenderer())
        }
        return DefaultPanel(
                title = title,
                initialSize = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                wrappers = wrappers,
                initialTileset = tileset())
    }

    override fun createCopy() = this.copy()

    companion object {

        fun newBuilder() = PanelBuilder()
    }
}
