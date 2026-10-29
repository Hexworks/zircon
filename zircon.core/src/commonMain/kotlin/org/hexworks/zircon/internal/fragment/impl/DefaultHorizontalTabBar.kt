package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.fragment.TabData
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeHeight
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

public class DefaultHorizontalTabBar internal constructor(
    size: Size,
    defaultSelected: String,
    tabs: List<TabData>
) : TabBar {

    private lateinit var tabBar: HBox
    private lateinit var content: Panel

    private var currentContent: AttachedComponent? = null
    private val group = radioButtonGroup { }

    private val lookup = tabs.associate { (tab, content) ->
        group.addComponent(tab.tabButton)
        tab.key to TabData(tab, content)
    }

    override val selectedKeyProperty: Property<String> = defaultSelected.toProperty()

    internal fun tab(key: String): Tab = lookup.getValue(key).tab

    override val root: Component = vBox {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()

        tabBar = hBox {
            preferredSize = Size(size.width, 3)
            decorations {
                +margin(0, 1)
            }
            componentRenderer = NoOpComponentRenderer()
            childrenToAdd = lookup.map { it.value.tab.root }
        }

        content = panel {
            preferredSize = size.withRelativeHeight(-3)
        }

        group.selectedButtonProperty.onChange { (_, newValue) ->
            newValue?.let { button ->
                currentContent?.detach()?.moveTo(Position.ZERO)
                currentContent = content.addComponent(lookup.getValue(button.key).content)
                selectedKeyProperty.value = button.key
            }
        }
        selectedKeyProperty.onChange { (_, key) ->
            lookup.getValue(key).tab.isSelected = true
        }
        lookup.getValue(defaultSelected).tab.isSelected = true
    }

}
