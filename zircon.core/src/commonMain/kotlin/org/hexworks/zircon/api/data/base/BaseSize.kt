package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Size
import kotlin.math.max

/**
 * Base class for implementing [Size].
 */
public abstract class BaseSize : Size {

    override operator fun plus(other: Size): Size = Size(width + other.width, height + other.height)

    override operator fun minus(other: Size): Size = Size(
        width = max(0, width - other.width),
        height = max(0, height - other.height)
    )

    override operator fun component1(): Int = width

    override operator fun component2(): Int = height

    override fun compareTo(other: Size): Int = (this.width * this.height).compareTo(other.width * other.height)

}
