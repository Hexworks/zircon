package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.behavior.Layerable

internal class DefaultAnimation(override val tick: Long,
                                override val loopCount: Int,
                                override val totalFrameCount: Int,
                                override val uniqueFrameCount: Int,
                                frames: List<InternalAnimationFrame>) : Animation {

    override val id: Identifier = IdentifierFactory.randomIdentifier()

    override val isLoopedIndefinitely = loopCount == 0

    private val infiniteLoop = loopCount == 0
    private var currentLoopCount = loopCount
    private val framesInOrder = mutableListOf<InternalAnimationFrame>()

    private var currentFrame: Maybe<InternalAnimationFrame> = Maybe.empty()

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
            currentFrame = Maybe.of(framesInOrder.removeAt(0))
        }
        return currentFrame.map { currentFrame ->
            if (framesInOrder.isEmpty()) {
                currentLoopCount--
                framesInOrder.addAll(flattenedFrames)
            }
            currentFrame.displayOn(layerable)
            true
        }.orElse(false)
    }

    override fun removeCurrentFrame() {
        currentFrame.map(InternalAnimationFrame::remove)
    }

    private fun hasNextFrame() = infiniteLoop || currentLoopCount > 0

}
