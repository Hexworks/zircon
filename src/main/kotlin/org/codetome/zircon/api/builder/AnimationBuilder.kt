package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.animation.AnimationFrame
import org.codetome.zircon.internal.graphics.DefaultAnimation
import org.codetome.zircon.internal.graphics.DefaultAnimationFrame

data class AnimationBuilder private constructor(
        private val animationFrames: MutableList<AnimationFrame> = mutableListOf(),
        private val positions: MutableList<Position> = mutableListOf(),
        private var tick: Long = 1000L / DEFAULT_FPS,
        private var loopCount: Int = 1,
        private var frameCount: Int = -1,
        private var length: Int = -1)
    : Builder<Animation> {

    fun getLength() = length

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

    fun addFrames(animationFrames: List<AnimationFrame>) = also {
        require(animationFrames.isNotEmpty()) {
            "You can't add zero frames to an animation!"
        }
        this.animationFrames.addAll(animationFrames)
        recalculateFrameCountAndLength()
    }

    fun addFrame(frame: AnimationFrame) = also {
        this.animationFrames.add(frame)
        recalculateFrameCountAndLength()
    }

    fun setPositionForAll(position: Position) = also {
        for (i in 0 until length) {
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
        if (positions.size == 0) {
            setPositionForAll(Position.DEFAULT_POSITION)
        } else {
            require(length == positions.size) {
                "An Animation must have the same amount of positions as frames (one position for each frame)!" +
                        " length: $length, position count: ${positions.size}"
            }
        }
        require(frameCount > 0) {
            "An Animation must contain at least one frame!"
        }
        require(length > 0) {
            "An Animation must have a length greater than zero!"
        }
        animationFrames.forEachIndexed { i, frame ->
            frame.setPosition(positions[i])
        }
        return DefaultAnimation(
                animationFrames = animationFrames,
                tick = tick,
                loopCount = loopCount,
                frameCount = frameCount,
                length = length)
    }

    override fun createCopy() = copy(
            animationFrames = animationFrames
                    .map {
                        DefaultAnimationFrame(
                                size = it.getSize(),
                                layers = it.getLayers().map { it.createCopy() }.toList(),
                                repeatCount = it.getRepeatCount())
                    }
                    .toMutableList(),
            positions = positions.toMutableList())

    private fun recalculateFrameCountAndLength() {
        length = animationFrames.map { it.getRepeatCount() }.reduce(Int::plus)
        frameCount = animationFrames.size
    }


    companion object {

        /**
         * Creates a new [AnimationBuilder] to build [Animation]s.
         */
        @JvmStatic
        fun newBuilder() = AnimationBuilder()

        private val DEFAULT_FPS = 15L

    }
}