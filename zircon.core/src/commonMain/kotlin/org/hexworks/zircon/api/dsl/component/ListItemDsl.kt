package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ListItemBuilder
import org.hexworks.zircon.api.component.ListItem

fun listItem(init: ListItemBuilder.() -> Unit): ListItem =
    ListItemBuilder().apply(init).build()