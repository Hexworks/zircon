package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.SizeBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationFrame
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class AnimationFrameBuilder : Builder<AnimationFrame> {
    private val layers = mutableListOf<Layer>()

    /**
     * The size of this frame.
     */
    var size: Size = Size.unknown()

    /**
     * How many times this frame will be repeated.
     */
    var repeatCount: Int = 1

    fun layer(init: LayerBuilder.() -> Unit) =
        LayerBuilder().apply {
            if (this@AnimationFrameBuilder.size.isNotUnknown) {
                size = this@AnimationFrameBuilder.size
            }
        }.apply(init).build().also { layer ->
            if (size.isUnknown) {
                size = layer.size
            }
        }

    fun addLayers(layers: List<Layer>) {
        this.layers.addAll(layers)
    }

    override fun build(): AnimationFrame {
        require(size.isNotUnknown) {
            "An animation frame must have a size"
        }
        return DefaultAnimationFrame(
            size = size,
            layers = layers.map { it.asInternal() },
            repeatCount = repeatCount
        )
    }
}


fun animationFrame(init: AnimationFrameBuilder.() -> Unit) =
    AnimationFrameBuilder().apply(init).build()

fun AnimationFrameBuilder.withSize(init: SizeBuilder.() -> Unit) = run {
    apply {
        size = SizeBuilder().apply(init).build()
    }
}