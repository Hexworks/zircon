package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.Animation.FiniteLoop
import org.hexworks.zircon.api.animation.Animation.LoopKind
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.data.PositionBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.internal.animation.InternalAnimationFrame
import org.hexworks.zircon.internal.animation.impl.DefaultAnimation
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationFrame
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.DurationUnit.MILLISECONDS
import kotlin.time.toDuration


private const val DEFAULT_FPS = 15L

@ZirconDsl
class AnimationBuilder(
    private val frames: MutableList<InternalAnimationFrame> = mutableListOf(),
    private val positions: MutableList<Position> = mutableListOf(),
    private var tick: Duration = (1000L / DEFAULT_FPS).toDuration(MILLISECONDS),
    private var uniqueFrameCount: Int = -1
) : Builder<Animation> {

    var totalFrameCount: Int = -1
        private set

    var loopKind: LoopKind = FiniteLoop(1)
        set(value) {
            field = value
        }

    var fps: Long
        get() = 1000L / tick.inWholeMilliseconds
        set(value) {
            require(value > 0) {
                "Fps must be greater than 0!"
            }
            tick = (1000L / fps).toDuration(MILLISECONDS)
        }

    var position: Position = 

    fun frame(init: AnimationFrameBuilder.() -> Unit) =
        AnimationFrameBuilder().apply(init).build().apply {
            frames.add(asInternal())
            recalculateFrameCountAndLength()
        }

    fun addFrames(frames: List<AnimationFrame>) {
        frames.forEach {
            this.frames.add(it.asInternal())
        }
        recalculateFrameCountAndLength()
    }

    override fun build(): Animation {
        recalculateFrameCountAndLength()
        require(uniqueFrameCount > 0) {
            "An Animation must contain at least one frame!"
        }
        require(totalFrameCount > 0) {
            "An Animation must have a length greater than zero!"
        }
        frames.forEach { frame ->
            frame.position = position
        }
        return DefaultAnimation(
            frames = frames,
            tick = tick,
            loopKind = loopKind,
            uniqueFrameCount = uniqueFrameCount,
            totalFrameCount = totalFrameCount
        )
    }

    fun createCopy() = AnimationBuilder(
        frames = frames
            .asSequence()
            .map { frame ->
                DefaultAnimationFrame(
                    size = frame.size,
                    layers = frame.layers.asSequence()
                        .map { it.createCopy().asInternal() }
                        .toList(),
                    repeatCount = frame.repeatCount)
            }
            .toMutableList(),
        positions = positions.toMutableList(),
        tick = tick,
        uniqueFrameCount = uniqueFrameCount
    )

    private fun recalculateFrameCountAndLength() {
        totalFrameCount = frames.asSequence()
            .map { it.repeatCount }
            .reduce(Int::plus)
        uniqueFrameCount = frames.size
    }

    companion object {
        fun create() = AnimationBuilder()
    }
}

/**
 * Creates a new [AnimationBuilder] using the builder DSL and returns it.
 */
fun animation(init: AnimationBuilder.() -> Unit): Animation =
    AnimationBuilder.create().apply(init).build()

fun AnimationBuilder.withPosition(init: PositionBuilder.() -> Unit) = apply {
    position = PositionBuilder().apply(init).build()
}