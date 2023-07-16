package org.hexworks.zircon.api.builder.modifier

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType
import org.hexworks.zircon.internal.dsl.ZirconDsl

/**
 * Builds [Border]s.
 * Defaults:
 * - a simple border
 * - on all sides (top, right, bottom, left)
 */
@ZirconDsl
class BorderBuilder : Builder<Border> {

    var borderType: BorderType = BorderType.SOLID
    var borderColor: TileColor = TileColor.defaultForegroundColor()
    var borderWidth: Int = 2
    var borderPositions: Set<BorderPosition> = BorderPosition.values().toSet()

    override fun build(): Border = Border(
        borderType = borderType,
        borderColor = borderColor,
        borderWidth = borderWidth,
        borderPositions = borderPositions
    )
}

/**
 * Creates a new [BorderBuilder] using the builder DSL and returns it.
 */
fun border(init: BorderBuilder.() -> Unit): Border =
    BorderBuilder().apply(init).build()
