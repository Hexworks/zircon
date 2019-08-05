package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationFrame

internal class DefaultAnimation(override val tick: Long,
                                override val loopCount: Int,
                                override val totalFrameCount: Int,
                                override val uniqueFrameCount: Int,
                                private val frames: List<InternalAnimationFrame>) : Animation {

    override val id: Identifier = IdentifierFactory.randomIdentifier()

    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount
    private val framesInOrder = mutableListOf<InternalAnimationFrame>()

    private var currentFrame: InternalAnimationFrame

    init {
        flattenFrames().forEach {
            framesInOrder.add(it)
        }
        require(framesInOrder.isNotEmpty()) {
            "There are no frames in this Animation."
        }
        currentFrame = framesInOrder.first()
    }

    override fun isLoopedIndefinitely() = loopCount == 0

    override fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

    override fun clearCurrentFrame() {
        currentFrame.remove()
    }

    override fun fetchCurrentFrame() = currentFrame

    override fun fetchNextFrame(): Maybe<out AnimationFrame> {
        return Maybe.ofNullable(if (hasNextFrame()) {
            framesInOrder.first().also {
                if (framesInOrder.isEmpty()) {
                    currentLoopCount--
                    framesInOrder.addAll(flattenFrames())
                }
            }
        } else {
            null
        })
    }

    override fun fetchAllFrames() = frames

    private fun flattenFrames() =
            frames.flatMap { frame ->
                (0 until frame.repeatCount).map { frame }
            }
}
