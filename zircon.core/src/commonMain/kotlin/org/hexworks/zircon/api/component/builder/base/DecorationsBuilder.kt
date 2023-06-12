package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class DecorationsBuilder : Builder<List<ComponentDecorationRenderer>> {
    private val decorations = mutableListOf<ComponentDecorationRenderer>()

    operator fun ComponentDecorationRenderer.unaryPlus() {
        this@DecorationsBuilder.decorations.add(this)
    }

    override fun build(): List<ComponentDecorationRenderer> {
        return decorations.toList()
    }
}
