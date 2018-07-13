package org.codetome.zircon.internal.animation

import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.animation.AnimationFrame
import org.codetome.zircon.internal.multiplatform.api.Identifier
import org.codetome.zircon.internal.multiplatform.api.Maybe
import org.codetome.zircon.internal.multiplatform.factory.ThreadSafeQueueFactory

class DefaultAnimation(private val animationFrames: List<AnimationFrame>,
                       private var tick: Long,
                       private var loopCount: Int,
                       private val frameCount: Int,
                       private var length: Int) : Animation {

    private val id = Identifier.randomIdentifier()
    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount

    private val framesInOrder = ThreadSafeQueueFactory.create<AnimationFrame>()
    private var currentFrame: AnimationFrame

    init {
        flattenFrames().forEach {
            framesInOrder.offer(it)
        }
        require(framesInOrder.isNotEmpty()) {
            "There are no frames in this Animation."
        }
        currentFrame = framesInOrder.peek().get()
    }

    override fun getId(): Identifier = id

    override fun getFrameCount() = frameCount

    override fun getLength() = length

    override fun getLoopCount() = loopCount

    override fun isLoopedIndefinitely() = loopCount == 0

    override fun getTick() = tick

    override fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

    override fun getCurrentFrame() = currentFrame

    override fun fetchNextFrame(): Maybe<AnimationFrame> {
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

    override fun getAllFrames() = animationFrames

    private fun flattenFrames() =
            animationFrames.flatMap { frame ->
                (0 until frame.getRepeatCount()).map { frame }
            }
}
