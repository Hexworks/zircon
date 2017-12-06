package org.codetome.zircon.api.beta.component

import org.codetome.zircon.api.Size

/**
 * Represents the size of a slice of 3D space. Extends [org.codetome.zircon.api.Size]
 * with a `height` (z axis) dimension.
 * Width corresponds to the **x** and depth corresponds to the **y** axis.
 */
data class Size3D(private val size: Size, val height: Int) : Comparable<Size3D> {

    val width get() = size.columns

    val depth get() = size.rows

    operator fun plus(other: Size3D) = Size3D.from2DSize(
            size = size + other.size,
            levels = height + other.height)

    operator fun minus(other: Size3D) = Size3D.from2DSize(
            size = size - other.size,
            levels = height - other.height)

    override fun compareTo(other: Size3D) = (this.width * this.depth * this.height)
            .compareTo(other.width * other.depth * other.height)

    /**
     * Transforms this [Size3D] to a [Size]. Note that
     * the `height` component is lost during the conversion!
     */
    fun to2DSize() = size

    companion object {

        /**
         * Factory method for [Size3D].
         */
        @JvmStatic
        fun of(columns: Int, rows: Int, levels: Int) = Size3D(
                size = Size.of(
                        columns = columns,
                        rows = rows),
                height = levels)

        /**
         * Creates a new [Size3D] from a [Size].
         * If `height` is not supplied it defaults to `0` (ground height).
         */
        @JvmOverloads
        @JvmStatic
        fun from2DSize(size: Size, levels: Int = 0) = Size3D(
                size = size,
                height = levels)
    }
}