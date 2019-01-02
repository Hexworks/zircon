package org.hexworks.zircon.api.mvc

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.screen.Screen

interface View : Closeable {

    val screen: Screen
    val theme: ColorTheme

    fun replaceWith(view: View)

    fun onDock() {}

    fun onUndock() {}

    override fun close() {}
}
