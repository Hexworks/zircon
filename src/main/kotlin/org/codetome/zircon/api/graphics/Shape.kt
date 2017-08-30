package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.graphics.DefaultShape

/**
 * A [Shape] is a set of [org.codetome.zircon.api.Position]s representing a geometric formation
 * (line, triangle, rectangle, box,  etc). A [Shape] is the most abstract representation of any graphic
 * object in Zircon and has no associated terminal, style, nor characters thus it is useful for
 * templating (like creating multiple versions of the same rectangle with different colors, shades
 * and characters).
 * Any [Shape] implementation is supposed to be immutable!
 */
interface Shape : Collection<Position> {

    /**
     * Returns all the [Position]s this [Shape] contains.
     */
    fun getPositions(): Set<Position>

    /**
     * Combines two [Shape]s into a new one which means that this operation
     * creates an union of the [Position]s of both [Shape]s.
     * **Note that** the original [Shape]s are unmodified, this operation
     * creates a new [Shape].
     */
    operator fun plus(shape: Shape): Shape {
        return DefaultShape(getPositions().plus(shape.getPositions()))
    }

    /**
     * Creates a [TextImage] from this [Shape] using `textCharacter` to fill the positions.
     */
    fun toTextImage(textCharacter: TextCharacter) : TextImage

    /**
     * Offsets this [Shape] to the default position (column=0,row=0),
     * so offsetting a Shape which contains `(Position(2, 3), Position(3, 4))`
     * will be transformed to `((Position(0, 0), Position(1, 1))`.
     */
    fun offsetToDefaultPosition() : Shape
}