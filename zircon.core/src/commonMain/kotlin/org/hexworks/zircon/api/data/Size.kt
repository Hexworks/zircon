package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Additive
import org.hexworks.zircon.internal.data.DefaultSize

/**
 * Represents a rectangular area in a 2D space.
 * This class is immutable and cannot change its internal state after creation.
 * [Size] supports destructuring to [width] and [height].
 */
public interface Size : Comparable<Size>, Additive<Size> {

    public val width: Int
    public val height: Int

    override operator fun plus(other: Size): Size

    override operator fun minus(other: Size): Size

    public operator fun component1(): Int = width

    public operator fun component2(): Int = height

    public companion object {
        /**
         * Creates a new [Size] using the given `width` (width) and `height` (height).
         */
        public operator fun invoke(width: Int, height: Int): Size = DefaultSize(width, height)

        //? This can also be called "IDENTITY". In abstract algebra an Additive type
        //? with a zero element is a Monoid (under addition), but we want to keep the
        //? api easy to understand, and there is also no way to enforce a type constraint
        //? in Kotlin that says "if a class implements Additive, its `companion object`
        //? also must implement `Identity<T>` since Kotlin doesn't support typeclasses
        //? so it would be pointless anyway.
        public val ZERO: Size = invoke(0, 0)
        public val ONE: Size = invoke(1, 1)
        public val DEFAULT_GRID_SIZE: Size = invoke(60, 30)
        public val UNKNOWN: Size = invoke(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}