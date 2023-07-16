package org.hexworks.zircon.api.dsl

import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

internal fun <T : Component, B : BaseComponentBuilder<T>> buildChildFor(
    parent: BaseContainerBuilder<*>,
    builder: B,
    init: B.() -> Unit
): T {
    val result = builder.apply(init).build()
    parent.childrenToAdd.plus(result)
    return result
}

internal fun <F : Fragment, B : FragmentBuilder<F>> buildFragmentFor(
    parent: BaseContainerBuilder<*>,
    builder: B,
    init: B.() -> Unit
): F {
    val result = builder.apply(init).build()
    parent.childrenToAdd.plus(result.root)
    return result
}

// TODO: this is why?
fun ModalBuilder<EmptyModalResult>.close() {
    close() // TODO: 👈 used to have `close(EmptyModalResult)`
}
