package org.hexworks.zircon.internal.animation.impl

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.Animation.InfiniteLoop
import org.hexworks.zircon.api.animation.Animation.LoopKind
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import kotlin.math.max
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
    private var loopState = loopKind

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
                loopKind.decrementFrameCount()
                framesInOrder.addAll(flattenedFrames)
            }
            currentFrame.displayOn(layerable)
            true
        }.orElse(false)
    }

    override fun removeCurrentFrame() {
        currentFrame?.let(InternalAnimationFrame::remove)
    }

    private fun hasNextFrame() = loopState.hasNextFrame

    private fun LoopKind.decrementFrameCount() = when(this) {
        is InfiniteLoop -> this
        is Animation.FiniteLoop -> copy(count = max(0, count - 1))
    }

    private val LoopKind.hasNextFrame: Boolean
        get() = when(this) {
            is Animation.InfiniteLoop -> true
            is Animation.FiniteLoop -> count > 0
        }
}
