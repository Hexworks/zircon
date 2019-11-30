package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class ListItemBuilder(
        private var text: String = "",
        override val props: CommonComponentProperties<ListItem> = CommonComponentProperties(
                componentRenderer = DefaultListItemRenderer()))
    : BaseComponentBuilder<ListItem, ListItemBuilder>() {

    fun withText(text: String) = also {
        this.text = text.withNewLinesStripped()
        contentSize = contentSize
                .withWidth(max(this.text.length, contentSize.width))
    }

    override fun build(): ListItem {
        val fixedText = text.withNewLinesStripped()
        return DefaultListItem(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = fixedText,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<ListItem>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        fun newBuilder() = ListItemBuilder()
    }
}
