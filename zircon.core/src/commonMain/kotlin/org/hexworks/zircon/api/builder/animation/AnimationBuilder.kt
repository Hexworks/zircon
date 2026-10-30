package org.hexworks.zircon.api.builder.animation

import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.Animation.FiniteLoop
import org.hexworks.zircon.api.animation.Animation.LoopKind
import org.hexworks.zircon.api.animation.AnimationFrame
import org.hexworks.zircon.api.builder.Builder
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
public class AnimationBuilder(
    private val frames: MutableList<InternalAnimationFrame> = mutableListOf(),
    private val positions: MutableList<Position> = mutableListOf(),
    private var tick: Duration = (1000L / DEFAULT_FPS).toDuration(MILLISECONDS),
    private var uniqueFrameCount: Int = -1
) : Builder<Animation> {

    public var totalFrameCount: Int = -1
        private set

    public var loopKind: LoopKind = FiniteLoop(1)
        set(value) {
            field = value
        }

    public var fps: Long
        get() = 1000L / tick.inWholeMilliseconds
        set(value) {
            require(value > 0) {
                "Fps must be greater than 0!"
            }
            tick = (1000L / value).toDuration(MILLISECONDS)
        }

    public var position: Position = Position.ZERO

    public fun frame(init: AnimationFrameBuilder.() -> Unit): AnimationFrame =
        AnimationFrameBuilder().apply(init).build().apply {
            frames.add(asInternal())
            recalculateFrameCountAndLength()
        }

    public fun addFrames(frames: List<AnimationFrame>) {
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

    public fun createCopy(): AnimationBuilder = AnimationBuilder(
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

    public companion object {
        public fun create(): AnimationBuilder = AnimationBuilder()
    }
}

/**
 * Creates a new [AnimationBuilder] using the builder DSL and returns it.
 */
public fun animation(init: AnimationBuilder.() -> Unit): Animation =
    AnimationBuilder.create().apply(init).build()

