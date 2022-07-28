@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.playground

import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.Applications
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.dsl.component.button
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.extensions.useComponentBuilder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.internal.application.SwingApplication
import java.awt.AWTKeyStroke
import java.awt.Canvas
import java.awt.Dimension
import java.awt.KeyboardFocusManager
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.KeyStroke


suspend fun main() {
    val app = Applications.startTileGrid()
}

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {
        val app = Applications.startTileGrid()
    }
}

