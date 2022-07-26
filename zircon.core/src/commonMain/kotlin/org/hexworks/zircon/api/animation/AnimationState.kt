package org.hexworks.zircon.api.animation

/**
 * Represents the states an [Animation] can be in.
 */
@Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

enum class AnimationState {

    /**
     * The [Animation] is in progress and will finish at some point in the future.
     */
    IN_PROGRESS,

    /**
     * The [Animation] is infinite, it will not finish unless [AnimationHandle.stop] is called.
     */
    INFINITE,

    /**
     * The [Animation] is finished.
     */
    FINISHED
}
