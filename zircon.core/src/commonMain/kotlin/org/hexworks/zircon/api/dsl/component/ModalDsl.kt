package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.internal.component.modal.EmptyModalResult
import kotlin.jvm.JvmName

/**
 * Creates a new [Modal] using the component builder DSL and returns it.
 */
fun <T : ModalResult> buildModal(init: ModalBuilder<T>.() -> Unit): Modal<T> =
    ModalBuilder<T>().apply(init).build()

/**
 * Creates a new [Modal] using the component builder DSL and returns it.
 */
@JvmName("buildModalWithNoResult")
fun buildModal(init: ModalBuilder<EmptyModalResult>.() -> Unit): Modal<EmptyModalResult> =
    ModalBuilder<EmptyModalResult>().apply(init).build()

/**
 * Creates a new [Modal] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Modal].
 */
fun <T : BaseContainerBuilder<*, *>, M : ModalResult> T.modal(
    init: ModalBuilder<M>.() -> Unit
): Modal<M> = buildChildFor(this, ModalBuilder(), init)

/**
 * Creates a new [Modal] with [EmptyModalResult] using the component builder DSL,
 * adds it to the receiver [BaseContainerBuilder] it and returns the [Modal].
 */
@JvmName("modalWithNoResult")
fun <T : BaseContainerBuilder<*, *>> T.modal(
    init: ModalBuilder<EmptyModalResult>.() -> Unit
): Modal<EmptyModalResult> = buildChildFor(this, ModalBuilder(), init)
