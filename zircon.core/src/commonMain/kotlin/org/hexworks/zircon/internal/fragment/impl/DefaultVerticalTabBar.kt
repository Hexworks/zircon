package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.buildHbox
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vbox
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class DefaultVerticalTabBar internal constructor(
    size: Size,
    tabWidth: Int,
    defaultSelected: String,
    tabs: List<TabData>
) : TabBar {

    private lateinit var tabBar: VBox
    private lateinit var content: Panel

    private var currentContent: AttachedComponent? = null
    private val group = radioButtonGroup { }
    private val lookup = tabs.associate { (tab, content) ->
        group.addComponent(tab.tabButton)
        tab.key to TabData(tab, content)
    }

    override val root: Component = buildHbox {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()

        tabBar = vbox {
            preferredSize = Size.create(tabWidth, size.height)
            decorations {
                +margin(1, 0)
            }
            componentRenderer = NoOpComponentRenderer()
            childrenToAdd = lookup.map { it.value.tab.root }
        }

        content = panel {
            preferredSize = size.withRelativeWidth(-tabWidth)
        }

        group.selectedButtonProperty.onChange { (_, newValue) ->
            newValue?.let { button ->
                currentContent?.detach()?.moveTo(Position.defaultPosition())
                currentContent = content.addComponent(lookup.getValue(button.key).content)
            }
        }
        lookup.getValue(defaultSelected).tab.isSelected = true
    }

}
