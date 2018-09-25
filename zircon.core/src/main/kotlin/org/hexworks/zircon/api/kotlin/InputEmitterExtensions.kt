package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.behavior.InputEmitter
import org.hexworks.zircon.api.event.Subscription
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.listener.KeyStrokeListener
import org.hexworks.zircon.api.util.Consumer

/**
 * Extension function which adapts [InputEmitter.onInput] to
 * Kotlin idioms (eg: lambdas).
 */
fun InputEmitter.onInput(fn: (Input) -> Unit): Subscription {
    return this.onInput(object : InputListener {
        override fun inputEmitted(input: Input) {
            fn(input)
        }
    })
}

/**
 * Extension function which adapts [InputEmitter.onKeyStroke] to
 * Kotlin idioms (eg: lambdas).
 */
fun InputEmitter.onKeyStroke(fn: (KeyStroke) -> Unit): Subscription {
    return this.onKeyStroke(object : KeyStrokeListener {
        override fun keyStroked(keyStroke: KeyStroke) {
            fn(keyStroke)
        }
    })
}

fun InputEmitter.onMouseClicked(fn: (MouseAction) -> Unit): Subscription {
    return onMouseClicked(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMousePressed(fn: (MouseAction) -> Unit): Subscription {
    return onMousePressed(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseReleased(fn: (MouseAction) -> Unit): Subscription {
    return onMouseReleased(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseEntered(fn: (MouseAction) -> Unit): Subscription {
    return onMouseEntered(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseExited(fn: (MouseAction) -> Unit): Subscription {
    return onMouseExited(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseWheelRotatedUp(fn: (MouseAction) -> Unit): Subscription {
    return onMouseWheelRotatedUp(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseWheelRotatedDown(fn: (MouseAction) -> Unit): Subscription {
    return onMouseWheelRotatedDown(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseDragged(fn: (MouseAction) -> Unit): Subscription {
    return onMouseDragged(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

fun InputEmitter.onMouseMoved(fn: (MouseAction) -> Unit): Subscription {
    return onMouseMoved(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}
