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
public interface Modal<T : ModalResult> : Component {

    /**
     * A value between `0` and `1` that darkens what is behind the [Modal]. When the modal
     * is opened the [Screen] captures what it shows at that moment; the modal draws that
     * capture, darkened by this percent, behind its content. `0` shows it as it was. A
     * modal opened over another one captures the already darkened view, so every level
     * darkens further, and closing a modal brings the previous view back.
     */
    public val darkenPercent: Double

    /**
     * Closes this [Modal] with a given [result].
     */
    public fun close(result: T)

    public fun onClosed(fn: (T) -> Unit)
}
