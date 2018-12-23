package org.hexworks.zircon.api.component.modal

import org.hexworks.zircon.api.component.Fragment

interface ModalFragment<T: ModalResult> : Fragment {

    override val root: Modal<T>
}
