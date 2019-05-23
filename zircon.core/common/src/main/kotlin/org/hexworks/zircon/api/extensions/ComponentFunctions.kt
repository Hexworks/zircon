package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.alignment.AlignmentStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.modifier.Border

/**
 * Can be used to draw a border around a [Component].
 */
fun border(border: Border = BorderBuilder.newBuilder().build()) = ComponentDecorations.border(border)

/**
 * Can be used to draw a box (using box drawing characters) around a [Component].
 * **Note that** a `title` will only be displayed for a [Component] if it is wrapped
 * with a box.
 */
fun box(boxType: BoxType = BoxType.SINGLE,
        title: String = "") = ComponentDecorations.box(boxType, title)

/**
 * Wraps a [Component] on the left and the right sides with the given
 * [leftSideCharacter] and [rightSideCharacter].
 */
fun side(leftSideCharacter: Char = '[',
         rightSideCharacter: Char = ']') = ComponentDecorations.side(leftSideCharacter, rightSideCharacter)

fun halfBlock() = ComponentDecorations.halfBlock()

/**
 * Can be used to draw a shadow around a [Component]. The shadow is drawn
 * around the bottom and the right sides.
 */
fun shadow() = ComponentDecorations.shadow()

/**
 * Calculates the [Position] of the resulting [Component] within
 * the given [tileGrid] using the given [ComponentAlignment].
 *
 * E.g. `TOP_LEFT` will align this [Component] to the top left
 * corner of the [tileGrid].
 */
fun alignmentWithin(tileGrid: TileGrid,
                    alignmentType: ComponentAlignment) = ComponentAlignments.alignmentWithin(tileGrid, alignmentType)

/**
 * Calculates the [Position] of the resulting [Component] within
 * the given [container] using the given [ComponentAlignment].
 *
 * E.g. `TOP_LEFT` will align this [Component] to the top left
 * corner of the [container].
 */
fun alignmentWithin(container: Container,
                    alignmentType: ComponentAlignment) = ComponentAlignments.alignmentWithin(container, alignmentType)

/**
 * Calculates the [Position] of the resulting [Component] relative to
 * the given [component] using the given [ComponentAlignment].
 *
 * E.g. `TOP_LEFT` will align this [Component] to the top left
 * corner of [component].
 */
fun alignmentAround(component: Component,
                    alignmentType: ComponentAlignment) = ComponentAlignments.alignmentAround(component, alignmentType)

/**
 * Creates a [AlignmentStrategy] which will align a [Component]
 * within another one relative to its top left corner
 * using the given [x],[y] coordinates.
 */
fun positionalAlignment(x: Int, y: Int) = ComponentAlignments.positionalAlignment(x, y)

/**
 * Creates a [AlignmentStrategy] which will align a [Component]
 * within another one relative to its top left corner
 * using the given [Position].
 */
fun positionalAlignment(position: Position) = ComponentAlignments.positionalAlignment(position)
