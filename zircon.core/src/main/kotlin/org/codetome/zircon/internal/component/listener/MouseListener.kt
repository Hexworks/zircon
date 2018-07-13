package org.codetome.zircon.internal.component.listener

import org.codetome.zircon.api.input.MouseAction

interface MouseListener {

    fun onMouseEvent(mouseAction: MouseAction)
}