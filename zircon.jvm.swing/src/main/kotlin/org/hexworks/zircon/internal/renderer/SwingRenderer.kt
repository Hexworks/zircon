package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.application.SwingApplication

interface SwingRenderer : Renderer<SwingApplication> {

    fun onFrameClosed(fn: (SwingRenderer) -> Unit): Subscription
}
