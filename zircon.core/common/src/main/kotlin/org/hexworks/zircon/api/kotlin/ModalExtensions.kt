package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult

inline fun <T : ModalResult> Modal<T>.onClosed(crossinline fn: (T) -> Unit) {
    onClosed(object : Consumer<T> {
        override fun accept(value: T) {
            fn(value)
        }
    })
}
