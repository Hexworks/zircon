package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.dsl.fragment.selector
import org.hexworks.zircon.api.extensions.toScreen

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val values = listOf("Green", "Red", "Blue").toProperty()

        val screen = SwingApplications.startTileGrid().toScreen()
        screen.addComponent(buildVbox {
            preferredSize = screen.size

            selector(20, values) {
                centeredText = true
            }
        })
        screen.display()
        screen.theme = ColorThemes.gamebookers()

    }

}

