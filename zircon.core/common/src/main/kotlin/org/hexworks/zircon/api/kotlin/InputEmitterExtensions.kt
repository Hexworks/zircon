package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.InputEmitter
import org.hexworks.zircon.api.behavior.input.KeyCombination
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.listener.KeyStrokeListener

/**
 * Extension function which adapts [InputEmitter.onInput] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun InputEmitter.onInput(crossinline fn: (Input) -> Unit): Subscription {
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
inline fun InputEmitter.onKeyStroke(crossinline fn: (KeyStroke) -> Unit): Subscription {
    return this.onKeyStroke(object : KeyStrokeListener {
        override fun keyStroked(keyStroke: KeyStroke) {
            fn(keyStroke)
        }
    })
}

inline fun InputEmitter.onKeyCombination(keyCombination: KeyCombination,
                                         crossinline fn: (KeyStroke) -> Unit): Subscription {
    return this.onKeyCombination(keyCombination, object : Consumer<KeyStroke> {
        override fun accept(value: KeyStroke) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onKeyPressed(key: Char,
                                     crossinline fn: (KeyStroke) -> Unit): Subscription {
    return this.onKeyPressed(key, object : Consumer<KeyStroke> {
        override fun accept(value: KeyStroke) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onInputOfType(inputType: InputType,
                                      crossinline fn: (Input) -> Unit): Subscription {
    return this.onInputOfType(inputType, object : Consumer<Input> {
        override fun accept(value: Input) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseAction(crossinline fn: (MouseAction) -> Unit): Subscription {
    return this.onMouseAction(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseClicked(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseClicked(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMousePressed(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMousePressed(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseReleased(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseReleased(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseEntered(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseEntered(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseExited(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseExited(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseWheelRotatedUp(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseWheelRotatedUp(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseWheelRotatedDown(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseWheelRotatedDown(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseDragged(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseDragged(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}

inline fun InputEmitter.onMouseMoved(crossinline fn: (MouseAction) -> Unit): Subscription {
    return onMouseMoved(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}
