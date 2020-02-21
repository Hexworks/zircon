package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.animation.impl.DefaultAnimation
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationFrame
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import org.hexworks.zircon.internal.config.RuntimeConfig

@Suppress("DataClassPrivateConstructor", "UNCHECKED_CAST")
data class AnimationBuilder private constructor(
        private val frames: MutableList<InternalAnimationFrame> = mutableListOf(),
        private val positions: MutableList<Position> = mutableListOf(),
        private var tick: Long = 1000L / DEFAULT_FPS,
        private var uniqueFrameCount: Int = -1) : Builder<Animation> {

    var totalFrameCount: Int = -1
        private set

    var loopCount: Int = 1
        private set

    fun withLoopCount(loopCount: Int) = also {
        require(loopCount >= 0) {
            "Loop count must be greater than or equal to 0!"
        }
        this.loopCount = loopCount
    }

    fun withFps(fps: Int) = also {
        require(fps > 0) {
            "Fps must be greater than 0!"
        }
        this.tick = 1000L / fps
    }

    fun addFrame(frame: AnimationFrame) = also {
        this.frames.add(frame as? InternalAnimationFrame
                ?: throw IllegalArgumentException("Can't use a custom implementation of AnimationFrame"))
        recalculateFrameCountAndLength()
    }

    fun addFrames(frames: List<AnimationFrame>) = also {
        require(frames.isNotEmpty()) {
            "You can't add zero frames to an animation!"
        }
        frames as? List<InternalAnimationFrame>
                ?: throw IllegalArgumentException("Can't use a custom implementation of AnimationFrame")
        this.frames.addAll(frames)
        recalculateFrameCountAndLength()
    }

    fun setPositionForAll(position: Position) = also {
        for (i in 0 until totalFrameCount) {
            addPosition(position)
        }
    }

    fun addPositions(positions: List<Position>) = also {
        this.positions.addAll(positions)
    }

    fun addPosition(position: Position) = also {
        this.positions.add(position)
    }

    override fun build(): Animation {
        recalculateFrameCountAndLength()
        if (positions.size == 0) {
            setPositionForAll(Position.defaultPosition())
        } else {
            require(totalFrameCount == positions.size) {
                "An Animation must have the same amount of positions as frames (one position for each frame)!" +
                        " length: $totalFrameCount, position count: ${positions.size}"
            }
        }
        require(uniqueFrameCount > 0) {
            "An Animation must contain at least one frame!"
        }
        require(totalFrameCount > 0) {
            "An Animation must have a length greater than zero!"
        }
        frames.forEachIndexed { i, frame ->
            frame.position = positions[i]
        }
        return DefaultAnimation(
                frames = frames,
                tick = tick,
                loopCount = loopCount,
                uniqueFrameCount = uniqueFrameCount,
                totalFrameCount = totalFrameCount)
    }

    override fun createCopy() = copy(
            frames = frames
                    .asSequence()
                    .map { frame ->
                        DefaultAnimationFrame(
                                size = frame.size,
                                layers = frame.layers.asSequence()
                                        .map { it.createCopy() }
                                        .toList(),
                                repeatCount = frame.repeatCount)
                    }
                    .toMutableList(),
            positions = positions.toMutableList())

    private fun recalculateFrameCountAndLength() {
        totalFrameCount = frames.asSequence()
                .map { it.repeatCount }
                .reduce(Int::plus)
        uniqueFrameCount = frames.size
    }


    companion object {

        /**
         * Creates a new [AnimationBuilder] to build [Animation]s.
         */
        fun newBuilder(): AnimationBuilder {
            require(RuntimeConfig.config.betaEnabled) {
                "Animations are a beta feature. Please enable them when setting up Zircon using an AppConfig."
            }
            return AnimationBuilder()
        }


        private const val DEFAULT_FPS = 15L

    }
}
