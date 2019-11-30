package org.hexworks.zircon.internal.animation

import org.hexworks.cobalt.datatypes.Maybe

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandler
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.animation.AnimationState

/**
 * Default implementation of the [AnimationInfo] interface.
 */
internal class DefaultAnimationInfo(private var state: AnimationState,
                                    private val animationHandler: AnimationHandler,
                                    private val animation: Animation) : AnimationInfo {

    private var onFinishFn = Maybe.empty<(AnimationInfo) -> Unit>()

    override val isFinished: Boolean
        get() = state == AnimationState.FINISHED

    override val isRunning: Boolean
        get() = state == AnimationState.IN_PROGRESS

    override val isInfinite: Boolean
        get() = state == AnimationState.INFINITE

    override fun onFinished(fn: (AnimationInfo) -> Unit) {
        require(state != AnimationState.INFINITE) {
            "Can't wait for an infinite Animation to finish."
        }
        onFinishFn = Maybe.of(fn)
    }

    override fun stop() {
        animationHandler.stopAnimation(animation)
    }

    fun setState(state: AnimationState) {
        this.state = state
        if (state == AnimationState.FINISHED) {
            onFinishFn.map { it(this) }
        }
    }

}

