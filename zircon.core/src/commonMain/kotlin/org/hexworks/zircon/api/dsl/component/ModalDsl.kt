package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult

/**
 * Creates a new [Modal] using the component builder DSL and returns it.
 */
fun <T : ModalResult> buildModal(init: ModalBuilder<T>.() -> Unit): Modal<T> =
    ModalBuilder<T>().apply(init).build()

/**
 * Creates a new [Modal] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Modal].
 */
fun <T : BaseContainerBuilder<*, *>, M : ModalResult> T.modal(
    init: ModalBuilder<M>.() -> Unit
): Modal<M> = buildChildFor(this, ModalBuilder(), init)
