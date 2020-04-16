package org.hexworks.zircon.examples.views

import org.hexworks.cobalt.logging.api.LoggerFactory

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED

object ViewsExampleKotlin {

    private val logger = LoggerFactory.getLogger(javaClass)

    class InitialView(grid: TileGrid) : BaseView(grid, ColorThemes.adriftInDreams()) {

        val dockOther = Components.button()
                .withText("Dock other")
                .withPosition(0, 2)
                .build()

        init {
            screen.apply {
                addComponent(Components.header().withText("Initial view."))
                addComponent(dockOther)
            }
        }

        override fun onDock() {
            logger.info("Docking Initial View.")
        }

        override fun onUndock() {
            logger.info("Undocking Initial View.")
        }
    }

    class OtherView(grid: TileGrid) : BaseView(grid, ColorThemes.afterglow()) {

        private val logger = LoggerFactory.getLogger(javaClass)

        val dockInitial = Components.button()
                .withText("Dock initial")
                .withPosition(12, 2)
                .build()

        init {
            screen.apply {
                addComponent(Components.header().withText("Other view."))
                addComponent(dockInitial)
            }
        }

        override fun onDock() {
            logger.info("Docking Other View.")
        }

        override fun onUndock() {
            logger.info("Undocking Other View.")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withSize(Size.create(60, 30))
                .build())

        val initial = InitialView(tileGrid)
        val other = OtherView(tileGrid)

        initial.dockOther.processComponentEvents(ACTIVATED) {
            other.dock()
        }

        other.dockInitial.processComponentEvents(ACTIVATED) {
            other.replaceWith(initial)
        }

        initial.dock()

    }
}