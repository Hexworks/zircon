package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class SizeBuilder : Builder<Size> {

    var width: Int = 0
    var height: Int = 0

    override fun build() = Size.create(width, height)
}

/**
 * Creates a new [Size] using the builder DSL and returns it.
 */
fun size(init: SizeBuilder.() -> Unit = {}): Size =
    SizeBuilder().apply(init).build()