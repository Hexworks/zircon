package org.codetome.zircon.internal.animation

import org.codetome.zircon.api.animation.AnimationResult
import org.codetome.zircon.api.animation.AnimationState
import org.codetome.zircon.internal.multiplatform.api.Maybe

class DefaultAnimationResult(private var state: AnimationState) : AnimationResult {

    private var onFinishFn = Maybe.empty<(AnimationResult) -> Unit>()

    override fun isFinished() = state == AnimationState.FINISHED

    override fun isRunning() = state == AnimationState.IN_PROGRESS

    override fun isInfinite() = state == AnimationState.INFINITE

    override fun onFinished(fn: (AnimationResult) -> Unit) {
        require(state != AnimationState.INFINITE) {
            "Can't wait for an infinite Animation to finish."
        }
        onFinishFn = Maybe.of(fn)
    }

    override fun stop() {
        TODO("not implemented")
    }

    fun setState(state: AnimationState) {
        this.state = state
        if (state == AnimationState.FINISHED) {
            onFinishFn.map { it.invoke(this) }
        }
    }

}

