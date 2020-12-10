package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.fragment.impl.DefaultTab

object TabBarPrototype {

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
                .build()
        ).toScreen()

        val tabBar = Components.hbox()
            .withSize(40, 3)
            .withDecorations(ComponentDecorations.padding(0, 1))
            .withComponentRenderer(NoOpComponentRenderer())
            .build()
        val content = Components.panel()
            .withSize(40, 27)
            .build()

        val box = Components.vbox()
            .withComponentRenderer(NoOpComponentRenderer())
            .withAlignmentWithin(screen, ComponentAlignment.CENTER)
            .withSize(40, 30)
            .build()

        screen.addComponent(box)
        box.addComponents(tabBar, content)

        val tab0 = DefaultTab("Tab 0", "tab0")
        val tab1 = DefaultTab("Tab 1", "tab1")
        val tab2 = DefaultTab("Tab 2", "tab2")

        val tab0Content = Components.textBox(30)
            .addHeader("Tab 0 Content")
            .addParagraph("This is the content of Tab 0")
            .withDecorations(ComponentDecorations.padding(1))
            .build()

        val tab1Content = Components.textBox(30)
            .addHeader("Tab 1 Content")
            .addParagraph("This is the content of Tab 1")
            .withDecorations(ComponentDecorations.padding(1))
            .build()

        val tab2Content = Components.textBox(30)
            .addHeader("Tab 2 Content")
            .addParagraph("This is the content of Tab 2")
            .withDecorations(ComponentDecorations.padding(1))
            .build()

        var currentContent: AttachedComponent? = null

        val lookup = mapOf(
            tab0.key to tab0Content,
            tab1.key to tab1Content,
            tab2.key to tab2Content
        )

        tabBar.addFragments(tab0, tab1, tab2)

        val group = Components.radioButtonGroup().build()
        group.addComponents(tab0.tabButton, tab1.tabButton, tab2.tabButton)
        group.selectedButtonProperty.onChange { (_, newValue) ->
            newValue.map { button ->
                currentContent?.detach()?.moveTo(Position.defaultPosition())
                currentContent = content.addComponent(lookup.getValue(button.key))
            }
        }

        tab0.isSelected = true

        screen.display()
        screen.theme = ColorThemes.ghostOfAChance()
    }
}
