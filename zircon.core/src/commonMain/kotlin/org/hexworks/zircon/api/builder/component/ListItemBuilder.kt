package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.builder.base.ComponentWithTextBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultListItem
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ListItemBuilder : ComponentWithTextBuilder<ListItem>(
    initialRenderer = DefaultListItemRenderer(),
    initialText = "",
    reservedSpace = 2
) {

    override fun build(): ListItem {
        return DefaultListItem(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
            textProperty = fixedTextProperty,
        ).attachListeners()
    }
}

/**
 * Creates a new [ListItem] using the component builder DSL and returns it.
 */
fun buildListItem(init: ListItemBuilder.() -> Unit): ListItem =
    ListItemBuilder().apply(init).build()

/**
 * Creates a new [ListItem] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ListItem].
 */
fun <T : BaseContainerBuilder<*>> T.listItem(
    init: ListItemBuilder.() -> Unit
): ListItem = buildChildFor(this, ListItemBuilder(), init)
