package org.hexworks.zircon.internal.view


import org.hexworks.zircon.api.view.View
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.api.view.base.BaseView

class DefaultViewContainer : ViewContainer {

    private var currentView: View? = null

    override fun dock(view: View) {
        require(view is BaseView) {
            "You need to use BaseView when creating custom Views."
        }
        currentView?.onUndock()
        currentView = view
        view.onDock()
        view.screen.display()
    }
}
