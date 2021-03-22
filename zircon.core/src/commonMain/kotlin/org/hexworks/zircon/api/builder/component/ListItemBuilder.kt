package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.internal.component.withNewLinesStripped
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class ListItemBuilder(
    private var text: String = ""
) : BaseComponentBuilder<ListItem, ListItemBuilder>(DefaultListItemRenderer()) {

    fun withText(text: String) = also {
        this.text = text.withNewLinesStripped()
        contentSize = contentSize
            .withWidth(max(this.text.length + 2, contentSize.width))
    }

    override fun build(): ListItem {
        val fixedText = text.withNewLinesStripped()
        return DefaultListItem(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            initialText = fixedText,
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withText(text)

    companion object {

        fun newBuilder() = ListItemBuilder()
    }
}
