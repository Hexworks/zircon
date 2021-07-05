package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ListItemBuilder : ComponentWithTextBuilder<ListItem, ListItemBuilder>(
        initialRenderer = DefaultListItemRenderer(),
        initialText = "",
        reservedSpace = 2
) {

    override fun build(): ListItem {
        val fixedText = text.withNewLinesStripped()
        return DefaultListItem(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialText = fixedText,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = ListItemBuilder()
    }
}
