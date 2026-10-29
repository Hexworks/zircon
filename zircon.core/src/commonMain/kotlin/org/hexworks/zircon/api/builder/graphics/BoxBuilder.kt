package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.Box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.config.ZirconDefaults
import org.hexworks.zircon.internal.graphics.DefaultBox

@ZirconDsl
public class BoxBuilder : Builder<Box> {

    public var size: Size = Size(3, 3)
    public var style: StyleSet = StyleSet.defaultStyle()
    public var boxType: BoxType = BoxType.BASIC
    public var tileset: TilesetResource = ZirconDefaults.current.tileset

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
public fun box(init: BoxBuilder.() -> Unit): Box =
    BoxBuilder().apply(init).build()


public fun BoxBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit): BoxBuilder = apply {
    style = StyleSetBuilder().apply(init).build()
}