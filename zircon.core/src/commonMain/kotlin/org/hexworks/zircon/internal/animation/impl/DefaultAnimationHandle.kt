package org.hexworks.zircon.internal.animation.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.animation.AnimationState
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationRunner

/**
 * Default implementation of the [AnimationHandle] interface.
 */
internal class DefaultAnimationHandle(
    state: AnimationState,
    private val animationRunner: InternalAnimationRunner,
    private val animation: InternalAnimation
) : AnimationHandle, Animation by animation {

    private val stateProperty = state.toProperty()

    var state: AnimationState by stateProperty.asDelegate()

    override val isFinished: Boolean
        get() = stateProperty.value == AnimationState.FINISHED

    override val isRunning: Boolean
        get() = stateProperty.value == AnimationState.IN_PROGRESS

    override val isInfinite: Boolean
        get() = stateProperty.value == AnimationState.INFINITE

    override fun onFinished(fn: (AnimationHandle) -> Unit) {
        require(stateProperty.value != AnimationState.INFINITE) {
            "Can't wait for an infinite Animation to finish."
        }
        stateProperty.onChange {
            if (it.newValue == AnimationState.FINISHED) {
                fn(this)
            }
        }
    }

    override fun stop() {
        animationRunner.stop(animation)
    }
}

