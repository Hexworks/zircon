@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.extensions.handleComponentEvents
import org.hexworks.zircon.api.extensions.handleMouseEvents
import org.hexworks.zircon.api.uievent.ComponentEventType.ACTIVATED
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed

object DataBindingTest {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()
    private val bindings = mutableListOf<Binding<Any>>()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val panel = Components.panel()
                .withTitle("Panel title")
                .withSize(40, 20)
                .wrapWithBox()
                .build()

        val label = Components.label()
                .withText("Label")
                .withPosition(1, 1)
                .withSize(20, 1)
                .build()

        val header = Components.header()
                .withText("Header")
                .withPosition(1, 2)
                .withSize(20, 1)
                .build()

        val btn = Components.button()
                .withText("Button")
                .withPosition(1, 3)
                .withSize(20, 1)
                .wrapSides(false)
                .build()

        val checkBox = Components.checkBox()
                .withText("Check box")
                .withPosition(1, 4)
                .withSize(20, 1)
                .build()

        val rbg = Components.radioButtonGroup()
                .withPosition(1, 5)
                .withSize(20, 1)
                .build()
        val option = rbg.addOption("btn0", "Radio button")

        val paragraph = Components.paragraph()
                .withPosition(1, 6)
                .withText("This is a very long text on this paragraph, so it will be multi line.")
                .withSize(20, 4)
                .build()

        screen.addComponent(panel)

        val master = Components.label()
                .withText("foo")
                .withPosition(25, 1)
                .withSize(10, 1)
                .build()

        val tamper = Components.button()
                .withText("Tamper")
                .withPosition(25, 2)
                .build().apply {
                    handleMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
                        master.textProperty.value = master.text + "x"
                        Processed
                    }
                }

        val bind = Components.button()
                .withText("Bind")
                .withPosition(25, 3)
                .build().apply {
                    handleMouseEvents(MouseEventType.MOUSE_RELEASED) { _, _ ->
                        bindings.add(panel.titleProperty.bind(master.textProperty))
                        bindings.add(label.textProperty.bind(master.textProperty))
                        bindings.add(header.textProperty.bind(master.textProperty))
                        bindings.add(btn.textProperty.bind(master.textProperty))
                        bindings.add(checkBox.textProperty.bind(master.textProperty))
                        bindings.add(option.textProperty.bind(master.textProperty))
                        bindings.add(paragraph.textProperty.bind(master.textProperty))
                        Processed
                    }
                }

        val unbind = Components.button()
                .withText("Unbind")
                .withPosition(25, 4)
                .build().apply {
                    handleComponentEvents(ACTIVATED) {
                        bindings.forEach {
                            it.dispose()
                        }
                        bindings.clear()
                        Processed
                    }
                }

        panel.addComponent(label)
        panel.addComponent(header)
        panel.addComponent(btn)
        panel.addComponent(checkBox)
        panel.addComponent(rbg)
        panel.addComponent(paragraph)

        panel.addComponent(master)
        panel.addComponent(tamper)
        panel.addComponent(bind)
        panel.addComponent(unbind)

        screen.display()
        screen.applyColorTheme(theme)
    }

}
