package org.hexworks.zircon.samples

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.uievent.ComponentEventType

class ConfirmButton : Fragment {

    override val root = Components.button()
            .withText("Confirm")
            .build()

    fun onConfirm(fn: () -> Unit): Subscription {
        return root.processComponentEvents(ComponentEventType.ACTIVATED) {
            fn()
        }
    }
}
