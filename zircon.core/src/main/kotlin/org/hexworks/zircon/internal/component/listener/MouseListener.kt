package org.hexworks.zircon.internal.component.listener

import org.hexworks.zircon.api.input.MouseAction

interface MouseListener {

    fun onMouseEvent(mouseAction: MouseAction)
}
