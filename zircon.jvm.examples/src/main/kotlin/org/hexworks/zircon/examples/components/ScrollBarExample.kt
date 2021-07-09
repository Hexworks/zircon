package org.hexworks.zircon.examples.components


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.fragment.impl.VerticalScrollableList

object ScrollBarExample {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(Size.create(60, 30))
                .build()
        )

        val screen = Screen.create(tileGrid)
        val panel = Components.panel()
            .withDecorations(box(title = "Scrollbar on panel"), shadow())
            .withPreferredSize(Size.create(30, 28))
            .withAlignment(positionalAlignment(29, 1))
            .build()
        screen.addComponent(panel)


        val demoList = mutableListOf<String>()
        (0 until 70).forEach { idx ->
            demoList.add("Item $idx")
        }
        val scrollFragment = VerticalScrollableList(
            Size.create(20, 15),
            Position.create(0, 1),
            demoList,
            onItemActivated = { item, idx ->
                println("You clicked on $item at idx $idx")
            }
        )
        panel.addFragment(scrollFragment)

        val scrollbar2 = Components.horizontalScrollbar()
            .withPreferredSize(20, 1)
            .withNumberOfScrollableItems(200)
            .withDecorations()
            .withAlignment(positionalAlignment(1, 23))
            .build()
        panel.addComponent(scrollbar2)
        val numberInput = Components.horizontalNumberInput(3)
            .withInitialValue(200)
            .withMinValue(0)
            .withMaxValue(500)
            .withAlignment(positionalAlignment(1, 22))
            .build()
        panel.addComponent(numberInput)

        numberInput.currentValueProperty.onChange {
            scrollbar2.resizeScrollBar(it.newValue)
        }
        val label2 = Components.label()
            .withText("Resize Me!")
            .withDecorations()
            .withAlignment(positionalAlignment(1, 21))
            .build()
        panel.addComponent(label2)

        screen.display()
        screen.theme = theme
    }

}
