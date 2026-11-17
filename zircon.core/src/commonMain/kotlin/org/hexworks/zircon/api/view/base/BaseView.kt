package org.hexworks.zircon.api.view.base

import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.view.View
import org.hexworks.zircon.api.config.ZirconDefaults

public abstract class BaseView(
    private val tileGrid: TileGrid,
    palette: ComponentPalette = ZirconDefaults.current.componentPalette,
) : View {

    final override val screen: Screen = tileGrid.toScreen()

    init {
        screen.componentPalette = palette
    }

    final override fun replaceWith(view: View) {
        tileGrid.dock(view)
    }

    final override fun dock() {
        tileGrid.dock(this)
    }

    override fun onDock() {}

    override fun onUndock() {}

}
