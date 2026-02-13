package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Additive
import org.hexworks.zircon.internal.data.DefaultSize

/**
 * Represents a rectangular area in a 2D space.
 * This class is immutable and cannot change its internal state after creation.
 * [Size] supports destructuring to [width] and [height].
 */
interface Size : Comparable<Size>, Additive<Size> {

    val width: Int
    val height: Int

    override operator fun plus(other: Size): Size

    override operator fun minus(other: Size): Size

    operator fun component1() = width

    operator fun component2() = height

    companion object {
        /**
         * Creates a new [Size] using the given `width` (width) and `height` (height).
         */
        fun create(width: Int, height: Int): Size = DefaultSize(width, height)

        //? This can also be called "IDENTITY". In abstract algebra an Additive type
        //? with a zero element is a Monoid (under addition), but we want to keep the
        //? api easy to understand, and there is also no way to enforce a type constraint
        //? in Kotlin that says "if a class implements Additive, its `companion object`
        //? also must implement `Identity<T>` since Kotlin doesn't support typeclasses
        //? so it would be pointless anyway.
        val ZERO = create(0, 0)
        val ONE = create(1, 1)
        val DEFAULT_GRID_SIZE = create(60, 30)
        val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}