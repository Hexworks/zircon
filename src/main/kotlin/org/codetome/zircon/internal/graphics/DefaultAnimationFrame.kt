package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.animation.AnimationFrame
import org.codetome.zircon.api.graphics.Layer

data class DefaultAnimationFrame(private val size: Size,
                                 private val layers: List<Layer>,
                                 private val repeatCount: Int) : AnimationFrame {

    private var position: Position = Position.DEFAULT_POSITION

    override fun getSize() = size

    override fun getRepeatCount() = repeatCount

    override fun getLayers() = layers

    override fun getPosition() = position

    override fun setPosition(position: Position) {
        this.position = position
    }
}