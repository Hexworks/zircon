package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.animation.DefaultAnimation
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame
import org.hexworks.zircon.internal.animation.InternalAnimationFrame

@Suppress("DataClassPrivateConstructor", "UNCHECKED_CAST")
data class AnimationBuilder private constructor(
        private val frames: MutableList<InternalAnimationFrame> = mutableListOf(),
        private val positions: MutableList<Position> = mutableListOf(),
        private var tick: Long = 1000L / DEFAULT_FPS,
        private var loopCount: Int = 1,
        private var uniqueFrameCount: Int = -1,
        private var totalFrameCount: Int = -1) : Builder<org.hexworks.zircon.api.animation.Animation> {

    fun getLength() = totalFrameCount

    fun loopCount(loopCount: Int) = also {
        require(loopCount >= 0) {
            "Loop count must be greater than or equal to 0!"
        }
        this.loopCount = loopCount
    }

    fun fps(fps: Int) = also {
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

    override fun build(): org.hexworks.zircon.api.animation.Animation {
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
            frame.setPosition(positions[i])
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
                    .map {
                        DefaultAnimationFrame(
                                size = it.getSize(),
                                layers = it.getLayers().map { it.createCopy() }.toList(),
                                repeatCount = it.getRepeatCount())
                    }
                    .toMutableList(),
            positions = positions.toMutableList())

    private fun recalculateFrameCountAndLength() {
        totalFrameCount = frames.map { it.getRepeatCount() }.reduce(Int::plus)
        uniqueFrameCount = frames.size
    }


    companion object {

        /**
         * Creates a new [AnimationBuilder] to build [Animation]s.
         */
        fun newBuilder() = AnimationBuilder()

        private const val DEFAULT_FPS = 15L

    }
}
