package org.hexworks.zircon.internal.mvc

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.mvc.View
import org.hexworks.zircon.api.mvc.ViewContainer
import org.hexworks.zircon.api.mvc.base.BaseView

class DefaultViewContainer(override val tileGrid: TileGrid) : ViewContainer {

    private var currentView = Maybe.empty<View>()

    override fun dock(view: View) {
        require(view is BaseView) {
            "You need to use BaseView when creating custom Views."
        }
        view.viewContainerProvider = { this }
        currentView.map {
            it.onUndock()
        }
        currentView = Maybe.of(view)
        view.onDock()
        view.screen.applyColorTheme(view.theme)
        view.screen.display()
    }
}
