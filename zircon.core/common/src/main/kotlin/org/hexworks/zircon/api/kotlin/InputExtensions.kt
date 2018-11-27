package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction

inline fun Input.whenInputTypeIs(inputType: InputType, crossinline fn: (Input) -> Unit) {
    return this.whenInputTypeIs(inputType = inputType, fn = object : Consumer<Input> {
        override fun accept(value: Input) {
            fn(value)
        }
    })
}

inline fun Input.whenKeyStroke(crossinline fn: (KeyStroke) -> Unit) {
    return this.whenKeyStroke(object : Consumer<KeyStroke> {
        override fun accept(value: KeyStroke) {
            fn(value)
        }
    })
}

inline fun Input.whenMouseAction(crossinline fn: (MouseAction) -> Unit) {
    return this.whenMouseAction(object : Consumer<MouseAction> {
        override fun accept(value: MouseAction) {
            fn(value)
        }
    })
}
