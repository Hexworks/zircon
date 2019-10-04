package org.hexworks.zircon.api.component.modal


import org.hexworks.zircon.api.component.Component

interface Modal<T : ModalResult> : Component {

    val darkenPercent: Double

    fun close(result: T)

    fun onClosed(fn: (T) -> Unit)
}
