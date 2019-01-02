package org.hexworks.zircon.api.mvc.base

import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.mvc.View
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.api.screen.Screen

abstract class BaseView : View {

    final override val screen: Screen by lazy {
        Screens.createScreenFor(viewContainerProvider.invoke().tileGrid)
    }

    internal var viewContainerProvider: () -> ViewContainer = {
        throw IllegalStateException("This View is not ready. Try docking it to an Application first.")
    }

    final override fun replaceWith(view: View) {
        viewContainerProvider.invoke().dock(view)
    }

    override fun onDock() {}

    override fun onUndock() {}

    override fun close() {}
}
