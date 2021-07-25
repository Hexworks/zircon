package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultHorizontalTabBar(
    size: Size,
    tabWidth: Int,
    defaultSelected: String,
    tabs: List<TabMetadata>
) : TabBar {

    init {
        require(size > Size.create(3, 3)) {
            "Can't create a tab bar that's smaller than 3x3"
        }
        require(tabWidth * tabs.size <= size.width) {
            "There is not enough space to display all the tabs"
        }
        val tabKeys = tabs.map { it.key }.toSet()
        require(tabKeys.contains(defaultSelected)) {
            "Default selected key ($defaultSelected) not found in tabs"
        }
        require(tabKeys.size == tabs.size) {
            "A tab bar can't have duplicate keys."
        }
    }

    private lateinit var tabBar: HBox
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

    override val root: Component = buildVbox {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()

        tabBar = hbox {
            preferredSize = Size.create(size.width, 3)
            decoration = margin(0, 1)
            componentRenderer = NoOpComponentRenderer()
            childrenToAdd = lookup.map { it.value.tab.root }
        }

        content = panel {
            preferredSize = size.withRelativeHeight(3)
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
