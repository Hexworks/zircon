package org.hexworks.zircon.api.animation

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer

/**
 * Stores information about a single animation frame.
 */
interface AnimationFrame {

    /**
     * Returns the [Size] of this [AnimationFrame].
     */
    fun getSize(): Size

    /**
     * Returns a list of [Layer]s which this [AnimationFrame] consists of.
     */
    fun getLayers(): List<Layer>

    /**
     * Returns how many times this frame will be repeated.
     */
    fun getRepeatCount(): Int

    /**
     * Returns the [Position] at which this [AnimationFrame] should be drawn.
     */
    fun getPosition(): Position

    /**
     * Sets the [Position] at which this [AnimationFrame] should be drawn.
     */
    fun setPosition(position: Position)
}
