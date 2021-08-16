package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.hbox
import org.hexworks.zircon.api.dsl.component.panel
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultHorizontalTabBar internal constructor(
    size: Size,
    defaultSelected: String,
    tabs: List<TabData>
) : TabBar {

    private lateinit var tabBar: HBox
    private lateinit var content: Panel

    private var currentContent: AttachedComponent? = null
    private val group = Components.radioButtonGroup().build()
    private val lookup = tabs.associate { (tab, content) ->
        group.addComponent(tab.tabButton)
        tab.key to TabData(tab, content)
    }

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
            preferredSize = size.withRelativeHeight(-3)
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
