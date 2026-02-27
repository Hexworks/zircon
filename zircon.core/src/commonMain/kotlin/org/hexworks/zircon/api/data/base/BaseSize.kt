package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.math.max

/**
 * Base class for implementing [Size].
 */
abstract class BaseSize : Size {

    override val rect: Boundableby lazy {
        Boundable.create(Position.DEFAULT_POSITION, this)
    }

    override operator fun plus(other: Size) = Size.create(width + other.width, height + other.height)

    override operator fun minus(other: Size) = Size.create(
        width = max(0, width - other.width),
        height = max(0, height - other.height)
    )

    override operator fun component1() = width

    override operator fun component2() = height

    override fun compareTo(other: Size) = (this.width * this.height).compareTo(other.width * other.height)

}
