package org.codetome.zircon.api.animation

enum class AnimationState {

    /**
     * The `Animation` is in progress and will finish
     * some time in the future.
     */
    IN_PROGRESS,
    /**
     * The `Animation` is infinite, it will not
     * finish.
     */
    INFINITE,
    /**
     * The `Animation` is finished.
     */
    FINISHED
}
