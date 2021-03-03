package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.GroupBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group

fun <T : Component> group(init: GroupBuilder<T>.() -> Unit): Group<T> =
    GroupBuilder<T>().apply(init).build()
