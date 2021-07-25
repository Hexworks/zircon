package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.panel
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultVerticalTabBar(
    size: Size,
    tabWidth: Int,
    defaultSelected: String,
    tabs: List<TabMetadata>
) : TabBar {

    init {
        val sizeTooSmallMsg = "Can't create a tab bar that's smaller than 3x3"
        require(size > Size.create(3, 3)) {
            sizeTooSmallMsg
        }
        require(tabWidth >= 3) {
            sizeTooSmallMsg
        }
        val tabKeys = tabs.map { it.key }.toSet()
        require(tabKeys.contains(defaultSelected)) {
            "Default selected key ($defaultSelected) not found in tabs"
        }
        require(tabKeys.size == tabs.size) {
            "A tab bar can't have duplicate keys."
        }
    }

    private lateinit var tabBar: VBox
    private lateinit var content: Panel

    private var currentContent: AttachedComponent? = null
    private val group = Components.radioButtonGroup().build()
    private val lookup = tabs.associate { meta ->
        val tab = DefaultTab(
            key = meta.key,
            label = meta.label,
            width = tabWidth
        )
        group.addComponent(tab.tabButton)
        meta.key to TabData(tab, meta.content)
    }

    private data class TabData(
        val tab: Tab,
        val content: Component
    )

    override val root: Component = buildHbox {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()

        tabBar = vbox {
            preferredSize = Size.create(tabWidth, size.height)
            decoration = margin(1, 0)
            componentRenderer = NoOpComponentRenderer()
            childrenToAdd = lookup.map { it.value.tab.root }
        }

        content = panel {
            preferredSize = size.withRelativeWidth(-tabWidth)
        }

        group.selectedButtonProperty.onChange { (_, newValue) ->
            newValue.map { button ->
                currentContent?.detach()?.moveTo(Position.defaultPosition())
                currentContent = content.addComponent(lookup.getValue(button.key).content)
            }
        }
        lookup.getValue(defaultSelected).tab.isSelected = true
    }

}
