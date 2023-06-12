package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ChildrenBuilder : Builder<List<Component>> {
    private val children = mutableListOf<Component>()

    operator fun Component.unaryPlus() {
        this@ChildrenBuilder.children.add(this)
    }

    override fun build(): List<Component> {
        return children.toList()
    }
}
