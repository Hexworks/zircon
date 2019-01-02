package org.hexworks.zircon.api.mvc

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.screen.Screen

interface View {

    val screen: Screen
    val theme: ColorTheme

    fun onDock() {}

    fun onUndock() {}
}
