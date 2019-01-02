package org.hexworks.zircon.api.mvc.base

import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.mvc.View
import org.hexworks.zircon.api.screen.Screen

abstract class BaseView : View {

    final override val screen: Screen by lazy {
        Screens.createScreenFor(tileGridProvider.invoke())
    }

    internal var tileGridProvider: () -> TileGrid = {
        throw IllegalStateException("This View is not ready. Try docking it to an Application first.")
    }
}
