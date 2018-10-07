package org.hexworks.zircon.examples.events

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.kotlin.onKeyStroke
import org.hexworks.zircon.api.kotlin.onMousePressed
import org.hexworks.zircon.api.listener.MouseAdapter
import org.hexworks.zircon.api.listener.MouseListener
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource

object InputHandlingExampleKotlin {

    private val SCREEN_SIZE = Sizes.create(80, 40)
    private val TILESET = BuiltInCP437TilesetResource.ROGUE_YUN_16X16

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .defaultTileset(TILESET)
                .defaultSize(SCREEN_SIZE)
                .build())

        // called for all inputs
        tileGrid.onInput { println(it) }

        // called for all key strokes
        tileGrid.onKeyStroke {
            println("Key stroke is: $it")
        }

        // MouseAdapter provides empty defaults. In this case only the pressed events are handled.
        tileGrid.onMouseAction(object : MouseAdapter() {
            override fun mousePressed(action: MouseAction) {
                println("Mouse is pressed")
            }

            override fun mouseDragged(action: MouseAction) {
                println("Mouse is dragged")
            }
        })

        tileGrid.onMousePressed {

        }

        // methods are called for specific mouse action types
        // with Kotlin as opposed to Java the MouseListener can also be used with only
        // just some of the methods overridden.
        tileGrid.onMouseAction(object : MouseListener {
            override fun mouseClicked(action: MouseAction) {

            }

            override fun mousePressed(action: MouseAction) {

            }

            override fun mouseReleased(action: MouseAction) {

            }
        })
    }
}
