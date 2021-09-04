package org.hexworks.zircon.internal.renderer

import org.hexworks.cobalt.events.api.Subscription

interface SwingRenderer : Renderer {

    fun onFrameClosed(fn: (SwingRenderer) -> Unit): Subscription
}
