package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Button
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.dsl.component.buildButton
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.buildPanel
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.fragment.VerticalTabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultVerticalTabBar(
    contentSize: Size,
    barSize: Size,
    defaultSelected: String,
    tabs: Map<String, Component>
) : VerticalTabBar {

    override val root = buildHbox {
        componentRenderer = NoOpComponentRenderer()
        preferredSize = contentSize.withRelativeWidth(barSize.width)
    }

    private lateinit var currentTab: Button

    init {
        val bar = buildVbox {
            componentRenderer = NoOpComponentRenderer()
            preferredSize = barSize
        }
        root.addComponent(bar)

        var contentArea = createContentArea(contentSize)
        var contentAttachment = root.addComponent(contentArea)
        var attachedTab: AttachedComponent? = null

        tabs.forEach { (tabText, tabContent) ->
            val btn = buildButton {
                +tabText
                decorations = listOf()
            }
            bar.addComponent(btn).onActivated {
                contentAttachment.detach()
                attachedTab?.detach()
                contentArea = createContentArea(contentSize)
                contentAttachment = root.addComponent(contentArea)
                tabContent.moveTo(Position.zero())
                attachedTab = contentArea.addComponent(tabContent)
                currentTab.isDisabled = false
                currentTab = btn
                btn.isDisabled = true
            }
            if (defaultSelected == tabText) {
                btn.isDisabled = true
                attachedTab = contentArea.addComponent(tabContent)
                currentTab = btn
            }
        }
    }

    private fun createContentArea(size: Size) = buildPanel {
        name = "Vertical Tab Bar Content Area"
        renderFunction = { tileGraphics, ctx ->
            val bg = ctx.component.theme.primaryBackgroundColor.withAlpha(25)
            tileGraphics.fill(Tile.empty().withBackgroundColor(bg))
        }
        preferredSize = size
    }
}
