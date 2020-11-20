package org.hexworks.zircon.api.component.modal

import org.hexworks.zircon.api.component.Fragment

/**
 * A [ModalFragment] is a [Fragment] that is provided for convenience
 * and wraps a [Modal].
 */
interface ModalFragment<T: ModalResult> : Fragment {

    override val root: Modal<T>
}
