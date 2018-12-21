package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.platform.util.SystemUtils

@Suppress("UNCHECKED_CAST")
data class ListItemBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties<ListItem> = CommonComponentProperties(
                componentRenderer = DefaultListItemRenderer()))
    : BaseComponentBuilder<ListItem, ListItemBuilder>(commonComponentProperties) {

    override fun withTitle(title: String) = also { }

    fun withText(text: String) = also {
        this.text = text
    }

    override fun build(): ListItem {
        require(text.isNotBlank()) {
            "A list item can't be blank!"
        }
        fillMissingValues()
        val finalSize = if (size.isUnknown()) {
            decorationRenderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        val fixedText = text
                .split(SystemUtils.getLineSeparator())
                .first()
                .split("\n")
                .first()
        return DefaultListItem(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = fixPosition(finalSize),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = fixedText,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<ListItem>))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = ListItemBuilder()
    }
}
