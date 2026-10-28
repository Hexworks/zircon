package org.hexworks.zircon.api.animation

import kotlinx.serialization.Serializable

/**
 * Describes a `.zap` animation: a zip archive holding this document as `animation.json`
 * next to one REXPaint `.xp` file per frame, named `<baseName><index>.<extension>`.
 */
@Serializable
public data class AnimationMetadata(
    /** Frames (ticks) per second. */
    val frameRate: Int,
    /** `0` loops forever, a positive number plays the animation that many times. */
    val loopCount: Int = 1,
    /** Prefix of the frame files, for example `skull-` for `skull-0.xp`. */
    val baseName: String = "",
    val extension: String = "xp",
    /**
     * The frame sequence with per-frame repeat counts. When empty, every frame file in the
     * archive plays once, in numeric order.
     */
    val frames: List<AnimationFrameRef> = emptyList(),
)

/**
 * One step of an animation's frame sequence: show frame file [frame] for [repeatCount] ticks.
 */
@Serializable
public data class AnimationFrameRef(
    val frame: Int,
    val repeatCount: Int = 1,
)
