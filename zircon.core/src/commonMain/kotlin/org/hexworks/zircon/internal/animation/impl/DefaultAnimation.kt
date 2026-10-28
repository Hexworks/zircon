package org.hexworks.zircon.internal.animation.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.Animation.InfiniteLoop
import org.hexworks.zircon.api.animation.Animation.LoopKind
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import kotlin.time.Duration

internal class DefaultAnimation(
    override val tick: Duration,
    override val loopKind: LoopKind,
    override val totalFrameCount: Int,
    override val uniqueFrameCount: Int,
    frames: List<InternalAnimationFrame>
) : InternalAnimation {

    override val id: UUID = UUID.randomUUID()
    override val isLoopedIndefinitely = loopKind is InfiniteLoop

    private val framesInOrder = mutableListOf<InternalAnimationFrame>()

    private var currentFrame: InternalAnimationFrame? = null

    /** Loops left to play, `null` for an infinite animation. */
    private var remainingLoops: Int? = (loopKind as? Animation.FiniteLoop)?.count

    private val flattenedFrames = frames.flatMap { frame ->
        (0 until frame.repeatCount).map { frame }
    }

    init {
        require(frames.isNotEmpty()) {
            "There are no frames in this Animation."
        }
        flattenedFrames.forEach {
            framesInOrder.add(it)
        }
    }

    override fun displayNextFrame(layerable: Layerable): Boolean {
        removeCurrentFrame()
        if (!hasNextFrame()) {
            currentFrame = null
            return false
        }
        val frame = framesInOrder.removeAt(0)
        currentFrame = frame
        if (framesInOrder.isEmpty()) {
            remainingLoops = remainingLoops?.minus(1)
            if (hasNextFrame()) {
                framesInOrder.addAll(flattenedFrames)
            }
        }
        frame.displayOn(layerable)
        return true
    }

    override fun removeCurrentFrame() {
        currentFrame?.let(InternalAnimationFrame::remove)
    }

    private fun hasNextFrame() = framesInOrder.isNotEmpty() && (remainingLoops ?: 1) > 0
}
