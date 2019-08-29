package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.uievent.ComponentEventType

// TODO: 1 cell displacement problem
object ScrollBarExample {
    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)
        val panel = Components.panel()
                .withDecorations(box(title = "Scrollbar on panel"), shadow())
                .withSize(Sizes.create(30, 28))
                .withAlignment(positionalAlignment(29, 1))
                .build()
        screen.addComponent(panel)


        val demoList = mutableListOf<String>()
        (0 until 70).forEach {idx ->
            demoList.add("Item $idx")
        }
        val scrollFragment = DemoScrollFragment(Size.create(20, 15), Position.create(0,1), demoList)
        panel.addFragment(scrollFragment)

        val compositeScrollBarPanel = Components.vbox()
                .withSize(1,17)
                .withSpacing(0)
                .withAlignment(positionalAlignment(20, 0))
                .build()
        val scrollbar1 = Components.verticalScrollbar()
                .withSize(1, 15)
                .withNumberOfScrollableItems(70)
                .withDecorations()
                .build()
        val decrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_UP_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        scrollbar1.decrementValues()
                    }
                }
        val incrementButton = Components.button()
                .withText("${Symbols.TRIANGLE_DOWN_POINTING_BLACK}")
                .withSize(1,1)
                .withDecorations()
                .build().apply {
                    processComponentEvents(ComponentEventType.ACTIVATED) {
                        scrollbar1.incrementValues()
                    }
                }
        scrollbar1.onValueChange {
            scrollFragment.scrollTo(it.newValue)
        }
        compositeScrollBarPanel.addComponent(decrementButton)
        compositeScrollBarPanel.addComponent(scrollbar1)
        compositeScrollBarPanel.addComponent(incrementButton)
        panel.addComponent(compositeScrollBarPanel)

        val scrollbar2 = Components.horizontalScrollbar()
                .withSize(20,1)
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
        screen.applyColorTheme(theme)
    }

    class DemoScrollFragment(private val size: Size, position: Position, private var items: List<String>): Fragment {
        private var topDisplayedItem: Int = 0
        override val root = Components.vbox()
                .withSize(size)
                .withAlignment(positionalAlignment(position))
                .withDecorations()
                .withSpacing(0)
                .build()

        init {
            displayListFromIndex()
        }

        private fun displayListFromIndex() {
            root.detachAllComponents()
            val maxIdx = when {
                topDisplayedItem + size.height < items.size -> topDisplayedItem + size.height
                else -> items.size
            }
            (topDisplayedItem until maxIdx).forEach { idx ->
                root.addComponent(
                        Components.label()
                                .withText(items[idx])
                                .withDecorations()
                                .build()
                )
            }
            root.applyColorTheme(theme)
        }

        fun scrollTo(idx: Int) {
            topDisplayedItem = idx
            displayListFromIndex()
        }
    }
}
