package org.codetome.zircon.component.listener

import org.codetome.zircon.input.MouseAction

interface MouseListener {

    fun onMouseEvent(mouseAction: MouseAction)
}