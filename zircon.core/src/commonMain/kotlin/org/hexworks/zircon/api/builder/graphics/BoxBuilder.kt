package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.graphics.DefaultBox

@ZirconDsl
class BoxBuilder : Builder<Box> {

    var size: Size = Size.create(3, 3)
    var style: StyleSet = StyleSet.defaultStyle()
    var boxType: BoxType = BoxType.BASIC
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset

    override fun build(): Box = DefaultBox(
        size = size,
        style = style,
        boxType = boxType,
        tileset = tileset
    )
}

/**
 * Creates a new [BoxBuilder] using the builder DSL and returns it.
 */
fun box(init: BoxBuilder.() -> Unit): Box =
    BoxBuilder().apply(init).build()

fun BoxBuilder.withSize(init: SizeBuilder.() -> Unit) = apply {
    size = SizeBuilder().apply(init).build()
}

fun BoxBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit) = apply {
    style = StyleSetBuilder().apply(init).build()
}