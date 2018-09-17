package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.ComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.impl.wrapping.BoxComponentDecorationRenderer
import org.hexworks.zircon.internal.component.impl.wrapping.ShadowComponentDecorationRenderer

data class PanelBuilder(private var title: String = "")
    : BaseComponentBuilder<Panel, PanelBuilder>() {

    fun title(title: String) = also {
        this.title = title
    }

    override fun build(): Panel {
        require(size() != Size.unknown()) {
            "You must set a size for a Panel!"
        }
        val wrappers = mutableListOf<ComponentDecorationRenderer>()
        if (wrapWithBox()) {
            wrappers.add(BoxComponentDecorationRenderer(
                    boxType = boxType(),
                    title = if (title.isNotBlank()) Maybe.of(title) else Maybe.empty()))
        }
        if (wrapWithShadow()) {
            wrappers.add(ShadowComponentDecorationRenderer())
        }
        return DefaultPanel(
                title = title,
                initialSize = size(),
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
