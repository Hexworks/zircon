package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.platform.util.SystemUtils
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
data class ListItemBuilder(
        private var text: String = "",
        override val props: CommonComponentProperties<ListItem> = CommonComponentProperties(
                componentRenderer = DefaultListItemRenderer()))
    : BaseComponentBuilder<ListItem, ListItemBuilder>() {

    // TODO: extract withText to mixin?
    fun withText(text: String) = also {
        this.text = text
        withWidth(max(preferredSize.width, text.length))
    }

    override fun build(): ListItem {
        val fixedText = text
                .split(SystemUtils.getLineSeparator())
                .first()
                .split("\n")
                .first()
        return DefaultListItem(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
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
