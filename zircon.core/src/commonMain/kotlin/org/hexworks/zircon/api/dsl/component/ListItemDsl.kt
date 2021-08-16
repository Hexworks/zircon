package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ListItemBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

/**
 * Creates a new [ListItem] using the component builder DSL and returns it.
 */
fun buildListItem(init: ListItemBuilder.() -> Unit): ListItem =
    ListItemBuilder.newBuilder().apply(init).build()

/**
 * Creates a new [ListItem] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [ListItem].
 */
fun <T : BaseContainerBuilder<*, *>> T.listItem(
    init: ListItemBuilder.() -> Unit
): ListItem = buildChildFor(this, ListItemBuilder.newBuilder(), init)
