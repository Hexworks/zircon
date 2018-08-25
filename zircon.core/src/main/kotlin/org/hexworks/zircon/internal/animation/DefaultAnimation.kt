package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

internal class DefaultAnimation(private val frames: List<InternalAnimationFrame>,
                                private var tick: Long,
                                private var loopCount: Int,
                                private val uniqueFrameCount: Int,
                                private var totalFrameCount: Int) : Animation {

    override val id: Identifier = Identifier.randomIdentifier()
    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount

    private val framesInOrder = ThreadSafeQueueFactory.create<InternalAnimationFrame>()
    private var currentFrame: InternalAnimationFrame

    init {
        flattenFrames().forEach {
            framesInOrder.offer(it)
        }
        require(framesInOrder.isNotEmpty()) {
            "There are no frames in this Animation."
        }
        currentFrame = framesInOrder.peek().get()
    }

    override fun getFrameCount() = uniqueFrameCount

    override fun getTotalFrameCount() = totalFrameCount

    override fun getLoopCount() = loopCount

    override fun isLoopedIndefinitely() = loopCount == 0

    override fun getTick() = tick

    override fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

    override fun clearCurrentFrame() {
        currentFrame.remove()
    }

    override fun getCurrentFrame() = currentFrame

    override fun fetchNextFrame(): Maybe<out AnimationFrame> {
        return if (hasNextFrame()) {
            framesInOrder.poll().also { nextFrame ->
                currentFrame = nextFrame.get()
                if (framesInOrder.isEmpty()) {
                    currentLoopCount--
                    framesInOrder.addAll(flattenFrames())
                }
            }
        } else {
            Maybe.empty()
        }
    }

    override fun getAllFrames() = frames

    private fun flattenFrames() =
            frames.flatMap { frame ->
                (0 until frame.getRepeatCount()).map { frame }
            }
}
