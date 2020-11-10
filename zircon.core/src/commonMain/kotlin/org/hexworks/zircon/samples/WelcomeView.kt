package org.hexworks.zircon.samples

import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class WelcomeView(tileGrid: TileGrid, theme: ColorTheme) : BaseView(tileGrid, theme) {

    // we add components to the screen when the view is initialized
    init {
        screen.addComponent(header()
                .withAlignmentWithin(screen, ComponentAlignment.CENTER)
                .withText("Hello, Zircon!"))
    }

    // used to initialize resources (like network connections) that are resource-intensive
    override fun onDock() {
        println("Docking Initial View.")
    }

    // usually used for cleanup operations
    override fun onUndock() {
        println("Undocking Initial View.")
    }


}
