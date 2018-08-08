package org.codetome.zircon.internal.animation

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.animation.AnimationFrame
import org.codetome.zircon.api.graphics.Layer

internal data class DefaultAnimationFrame(private val size: Size,
                                 private val layers: List<Layer>,
                                 private val repeatCount: Int) : AnimationFrame {

    private var position: Position = Position.defaultPosition()

    override fun getSize() = size

    override fun getRepeatCount() = repeatCount

    override fun getLayers() = layers

    override fun getPosition() = position

    override fun setPosition(position: Position) {
        this.position = position
    }
}
