package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult

fun <T : ModalResult> modal(init: ModalBuilder<T>.() -> Unit): Modal<T> =
    ModalBuilder<T>().apply(init).build()

fun <T : ModalResult> ModalBuilder<T>.component(init: () -> Component) {
    withComponent(
        init()
    )
}