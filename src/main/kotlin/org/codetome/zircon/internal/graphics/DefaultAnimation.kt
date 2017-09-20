package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.graphics.Animation
import org.codetome.zircon.api.graphics.AnimationFrame
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

class DefaultAnimation(private val animationFrames: List<AnimationFrame>,
                       private var tick: Long,
                       private var loopCount: Int,
                       private val frameCount: Int,
                       private var length: Int) : Animation {

    private val id = UUID.randomUUID()
    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount

    private val framesInOrder = LinkedBlockingQueue<AnimationFrame>(flattenFrames())
    private var currentFrame = framesInOrder.peek()

    override fun getId(): UUID = id

    override fun getFrameCount() = frameCount

    override fun getLength() = length

    override fun getLoopCount() = loopCount

    override fun getTick() = tick

    override fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

    override fun getCurrentFrame(): AnimationFrame = currentFrame

    override fun fetchNextFrame(): Optional<AnimationFrame> {
        return if (hasNextFrame()) {
            Optional.of(framesInOrder.poll()).also {
                currentFrame = it.get()
                if (framesInOrder.isEmpty()) {
                    currentLoopCount--
                    framesInOrder.addAll(flattenFrames())
                }
            }
        } else {
            Optional.empty()
        }
    }

    override fun getAllFrames() = animationFrames

    private fun flattenFrames() =
            animationFrames
                    .flatMap { frame ->
                        Collections.nCopies(frame.getRepeatCount(), frame)
                    }
}