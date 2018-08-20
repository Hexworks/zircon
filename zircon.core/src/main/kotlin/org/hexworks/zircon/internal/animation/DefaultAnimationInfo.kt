package org.hexworks.zircon.internal.animation

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandler
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.animation.AnimationState
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Maybe

/**
 * Default implementation of the [AnimationInfo] interface.
 */
internal class DefaultAnimationInfo(private var state: AnimationState,
                                    private val animationHandler: AnimationHandler,
                                    private val animation: Animation) : AnimationInfo {

    private var onFinishFn = Maybe.empty<(AnimationInfo) -> Unit>()

    override fun isFinished() = state == AnimationState.FINISHED

    override fun isRunning() = state == AnimationState.IN_PROGRESS

    override fun isInfinite() = state == AnimationState.INFINITE

    override fun onFinished(fn: (AnimationInfo) -> Unit) {
        require(state != AnimationState.INFINITE) {
            "Can't wait for an infinite Animation to finish."
        }
        onFinishFn = Maybe.of(fn)
    }

    override fun onFinished(fn: Consumer<AnimationInfo>) {
        onFinished { fn.accept(it) }
    }

    override fun stop() {
        animationHandler.stopAnimation(animation)
    }

    fun setState(state: AnimationState) {
        this.state = state
        if (state == AnimationState.FINISHED) {
            onFinishFn.map { it.invoke(this) }
        }
    }

}

