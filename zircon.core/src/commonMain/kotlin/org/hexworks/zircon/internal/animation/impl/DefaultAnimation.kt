package org.hexworks.zircon.internal.animation.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationFrame

internal class DefaultAnimation(
    override val tick: Long,
    override val loopCount: Int,
    override val totalFrameCount: Int,
    override val uniqueFrameCount: Int,
    frames: List<InternalAnimationFrame>
) : InternalAnimation {

    override val id: UUID = UUID.randomUUID()

    override val isLoopedIndefinitely = loopCount == 0

    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount
    private val framesInOrder = mutableListOf<InternalAnimationFrame>()

    private var currentFrame: InternalAnimationFrame? = null

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
        if (hasNextFrame()) {
            currentFrame = framesInOrder.removeAt(0)
        }
        return currentFrame?.let { currentFrame ->
            if (framesInOrder.isEmpty()) {
                currentLoopCount--
                framesInOrder.addAll(flattenedFrames)
            }
            currentFrame.displayOn(layerable)
            true
        }.orElse(false)
    }

    override fun removeCurrentFrame() {
        currentFrame?.let(InternalAnimationFrame::remove)
    }

    private fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

}
