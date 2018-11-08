package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.internal.component.InternalComponent

interface ComponentFocusHandler {

    val focusedComponent: InternalComponent

    fun focusNext()

    fun focusPrevious()

    fun focus(component: InternalComponent): Boolean

    fun refreshFocusables()
}
