package org.hexworks.zircon.api.view.base

import org.hexworks.zircon.api.behavior.ColorThemeOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.View
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.config.RuntimeConfig

abstract class BaseView(
        private val tileGrid: TileGrid,
        final override var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme)
    : View, ColorThemeOverride by ColorThemeOverride.create(theme) {

    final override val screen = Screen.create(tileGrid)

    init {
        screen.theme = theme
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
