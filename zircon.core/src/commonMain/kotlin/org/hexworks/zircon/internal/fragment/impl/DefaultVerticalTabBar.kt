package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.fragment.VerticalTabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultVerticalTabBar(
        contentSize: Size,
        barSize: Size,
        defaultSelected: String,
        private val tabs: Map<String, Component>
) : VerticalTabBar {

    init {
        require(contentSize.height >= barSize.height) {
            "Can't have a higher bar than the content area."
        }
        require(tabs.containsKey(defaultSelected)) {
            "Default selected tab $defaultSelected is not present in tabs."
        }
        require(tabs.size <= barSize.height) {
            "Tab bar height ${barSize.height} is not enough for the number of items supplied: ${tabs.size}."
        }
    }

    override val root = Components.hbox()
            .withComponentRenderer(NoOpComponentRenderer())
            .withSize(contentSize.withRelativeWidth(barSize.width))
            .build()

    private lateinit var currentTab: Button

    init {
        val bar = Components.vbox()
                .withComponentRenderer(NoOpComponentRenderer())
                .withSize(barSize)
                .build()
        val contentArea = Components.panel()
                .withRendererFunction { tileGraphics, ctx ->
                    val bg = ctx.component.theme.primaryBackgroundColor.withAlpha(25)
                    tileGraphics.fill(Tile.empty().withBackgroundColor(bg))
                }
                .withSize(contentSize)
                .build()

        tabs.forEach { (tab, content) ->
            val btn = Components.button()
                    .withText(tab)
                    .withDecorations()
                    .build()
            bar.addComponent(btn).onActivated {
                contentArea.clear()
                content.moveTo(Position.zero())
                contentArea.addComponent(content)
                currentTab.isDisabled = false
                currentTab = btn
                btn.isDisabled = true
            }
            if (defaultSelected == tab) {
                btn.isDisabled = true
                contentArea.addComponent(content)
                currentTab = btn
            }
        }

        root.addComponents(bar, contentArea)
    }
}
