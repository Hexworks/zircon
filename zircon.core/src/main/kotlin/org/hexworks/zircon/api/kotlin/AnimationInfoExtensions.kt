package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.util.Consumer

/**
 * Extension function which adapts [AnimationInfo.onFinished] to
 * Kotlin idioms (eg: lambdas).
 */
fun AnimationInfo.onFinished(fn: (AnimationInfo) -> Unit) {
    onFinished(object : Consumer<AnimationInfo> {
        override fun accept(value: AnimationInfo) {
            return fn.invoke(value)
        }
    })
}
