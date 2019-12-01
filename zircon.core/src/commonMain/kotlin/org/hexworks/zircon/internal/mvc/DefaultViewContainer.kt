package org.hexworks.zircon.internal.mvc

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.view.View
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.api.view.base.BaseView

class DefaultViewContainer : ViewContainer {

    private var currentView = Maybe.empty<View>()

    override fun dock(view: View) {
        require(view is BaseView) {
            "You need to use BaseView when creating custom Views."
        }
        currentView.map {
            it.onUndock()
        }
        currentView = Maybe.of(view)
        view.onDock()
        view.screen.display()
    }
}
