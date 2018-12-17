@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.cobalt.logging.api.debug

object KotlinPlayground {

    private val logger = LoggerFactory.getLogger(javaClass)

    @JvmStatic
    fun main(args: Array<String>) {
        logger.info("foo")
        val tileGrid = SwingApplications.startTileGrid(AppConfig.defaultConfiguration())

        val screen0 = Screens.createScreenFor(tileGrid)
        val toggleButton0 = Components.button()
                .withText("Toggle screen1")
                .wrapSides(true)
                .wrapWithBox(true)
                .build()

        var panel0 = Components.panel()
                .withSize(Sizes.defaultTerminalSize())
                .withPosition(Positions.defaultPosition())
                .build()

        panel0.addComponent(toggleButton0)

        logger.debug {
            "debug"
        }


        panel0.requestFocus()
        panel0.onInput {
            System.out.println("panel0 input " + it.toString())
        }


        screen0.addComponent(panel0)

        screen0.display()


    }

}
