package org.hexworks.zircon.api.mvc

import org.hexworks.zircon.api.behavior.Themeable
import org.hexworks.zircon.api.screen.Screen

interface View : Themeable {

    val screen: Screen

    fun dock()

    fun replaceWith(view: View)

    fun onDock() {}

    fun onUndock() {}

}
