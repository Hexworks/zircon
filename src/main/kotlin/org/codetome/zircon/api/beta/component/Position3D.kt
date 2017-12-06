package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Position

/**
 * Represents a coordinate in 3D space. Extends [Position] with
 * a `level` dimension. Use [Position3D.fromPosition] and [Position3D.toPosition]
 * to convert between the two.
 * **Important:**
 * - `column: 0` is considered the **leftmost** column in a 3D space
 * - `row: 0` is considered the **topmost** row in a 3D space
 * - `level: 0` is considered "ground level"
 */
data class Position3D(val column: Int, val row: Int, val level: Int) {

    /**
     * Transforms this [Position3D] to a [Position]. Note that
     * the `level` component (level) is lost during the conversion!
     */
    fun toPosition() = Position(
            column = column,
            row = row)

    companion object {

        /**
         * Creates a new [Position3D] from a [Position].
         * The `column` in `position` will be used as `column`,
         * the `row` will be used as `row`.
         * If `level` is not supplied it defaults to `0` (ground level).
         */
        @JvmOverloads
        @JvmStatic
        fun fromPosition(position: Position, level: Int = 0) = Position3D(
                column = position.column,
                row = position.row,
                level = level)
    }
}