package org.codetome.zircon.api.animation

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.graphics.Layer

interface AnimationFrame {

    /**
     * Returns the [Size] of the space which is occupied by this [AnimationFrame].
     */
    fun getSize(): Size

    /**
     * Returns a list of [Layer]s which are part of this [AnimationFrame].
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
