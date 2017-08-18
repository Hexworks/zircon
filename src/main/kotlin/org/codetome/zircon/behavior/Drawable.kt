package org.codetome.zircon.behavior

import org.codetome.zircon.Position
import org.codetome.zircon.graphics.TextImage

/**
 * Represents an object which can be drawn upon a [DrawSurface].
 */
interface Drawable : Boundable {

    /**
     * Copies this [Drawable]'s content to a [DrawSurface]. If the destination [DrawSurface] is larger
     * than this [Drawable], the areas outside of the area that is written to will be untouched.
     */
    fun drawOnto(surface: DrawSurface, offset: Position = Position.TOP_LEFT_CORNER)

    /**
     * Copies this [Drawable]'s content to another [DrawSurface]. If the destination [DrawSurface]
     * is larger than this [Drawable], the areas outside of the area that is written to will be untouched.
     * Note that start row / column indexes and destination row / column offsets can be negative.
     * If the start row / column is negative `n` rows will be skipped from the source image.
     * So if you copy a 2x2 image over a 3x3 image and `startRowIndex` is -1 the first row of the 3x3 image
     * won't be touched.
     * If the offsets is -1 the first row / column will be overwritten by the 2x2 image's second row
     * @param destination [TextImage] to copy to
     * @param startRowIndex which row in this image to start copying from
     * @param rows how many rows to copy
     * @param startColumnIndex which column in this image to start copying from
     * @param columns how many columns to copy
     * @param destinationRowOffset Offset (in number of rows) in the target image where we want to first copied row to be
     * @param destinationColumnOffset Offset (in number of columns) in the target image where we want to first copied column to be
     */
    fun drawOnto(
            destination: DrawSurface,
            startRowIndex: Int = 0,
            rows: Int = getBoundableSize().rows,
            startColumnIndex: Int = 0,
            columns: Int = getBoundableSize().columns,
            destinationRowOffset: Int = 0,
            destinationColumnOffset: Int = 0)
}