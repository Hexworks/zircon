package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.platform.util.SystemUtils

data class ListItemBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
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
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                text = fixedText,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = DefaultListItemRenderer()))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = ListItemBuilder()
    }
}
