package org.hexworks.zircon.api.component.modal

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.modal.EmptyModalResult


/**
 * A [Modal] is a [Component] that blocks access to all other [Component]s on a [Screen]
 * and also stops component events until the modal is [close]d. A [Modal] returns
 * an arbitrary [ModalResult]. If you don't want to return anything from a [Modal]
 * use [EmptyModalResult].
 * [Modal]s can be opened on top of each other.
 */
interface Modal<T : ModalResult> : Component {

    /**
     * A value between `0` and `1` that will be used to darken the area
     * outside of the [Modal] when it is opened.
     */
    val darkenPercent: Double

    /**
     * Closes this [Modal] with a given [result].
     */
    fun close(result: T)

    fun onClosed(fn: (T) -> Unit)
}
