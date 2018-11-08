package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.animation.AnimationInfo

/**
 * Extension function which adapts [AnimationInfo.onFinished] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun AnimationInfo.onFinished(crossinline fn: (AnimationInfo) -> Unit) {
    onFinished(object : Consumer<AnimationInfo> {
        override fun accept(value: AnimationInfo) {
            return fn.invoke(value)
        }
    })
}
