package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder

internal fun <T : Component, B : ComponentBuilder<T, B>> buildChildFor(
        parent: BaseContainerBuilder<*, *>,
        builder: B,
        init: B.() -> Unit
): T {
    val result = builder.apply(init).build()
    parent.withAddedChildren(result)
    return result
}