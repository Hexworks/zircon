package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class PositionBuilder : Builder<Position> {

    var x: Int = 0
    var y: Int = 0

    override fun build() = Position.create(x, y)
}

/**
 * Creates a new [Position] using the builder DSL and returns it.
 */
fun position(init: PositionBuilder.() -> Unit = {}): Position =
    PositionBuilder().apply(init).build()