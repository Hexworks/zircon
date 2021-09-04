@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.events.api.EventBus
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


object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val frame = JFrame()
        val canvas0 = Canvas()
        val canvas1 = Canvas()

        val config0 = AppConfig.newBuilder()
            .withSize(30, 30)
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .build()
        val config1 = AppConfig.newBuilder()
            .withSize(24, 24)
            .withDefaultTileset(CP437TilesetResources.rexPaint20x20())
            .build()

        val renderer0 = SwingApplications.createRenderer(
            config = config0,
            frame = frame,
            canvas = canvas0,
            shouldInitializeSwingComponents = false
        )
        val renderer1 = SwingApplications.createRenderer(
            config = config1,
            frame = frame,
            canvas = canvas1,
            shouldInitializeSwingComponents = false
        )
        val app0 = SwingApplication(
            config = config0,
            eventBus = EventBus.create(),
            tileGrid = renderer0.tileGrid,
            renderer = renderer0,
        )
        val app1 = SwingApplication(
            config = config1,
            eventBus = EventBus.create(),
            tileGrid = renderer1.tileGrid,
            renderer = renderer1
        )

        app0.start()
        app1.start()

        frame.layout = null
        frame.size = Dimension(16 * 30 * 2, 16 * 30)
        canvas0.setBounds(0, 0, 16 * 30, 16 * 30)
        canvas1.setBounds(16 * 30, 0, 16 * 30, 16 * 30)
        frame.add(canvas0)
        frame.add(canvas1)
        frame.isVisible = true

        val s0 = renderer0.tileGrid.toScreen()
        val s1 = renderer1.tileGrid.toScreen()

        s0.useComponentBuilder {
            button {
                +"Stop App 0"
                onActivated {
                    app0.stop()
                }
            }
        }
        s0.display()
        s0.theme = ColorThemes.amigaOs()

        s1.useComponentBuilder {
            button {
                +"Stop App 1"
                onActivated {
                    app1.stop()
                }
            }
        }
        s1.display()
        s1.theme = ColorThemes.arc()

        frame.setFocusTraversalKeys(
            KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
            setOf(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0))
        )
        frame.setFocusTraversalKeys(
            KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
            setOf(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0))
        )
    }
}

