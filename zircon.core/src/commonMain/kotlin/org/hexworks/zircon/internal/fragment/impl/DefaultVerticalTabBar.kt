package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.builder.component.selectorGroup
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeWidth
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.TabData
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.util.reportError
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.data.Orientation
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentTheme

public class DefaultVerticalTabBar internal constructor(
    size: Size,
    tabWidth: Int,
    defaultSelected: String,
    tabs: List<TabData>,
    theme: ComponentTheme,
) : TabBar {
    private val components = ComponentFactory { theme }

    private lateinit var tabBar: VBox
    private lateinit var content: Panel
    private lateinit var strip: TabStrip

    private var currentContent: AttachedComponent? = null
    private val group = selectorGroup { }

    private val order = tabs.map { it.tab }
    private val lookup = tabs.associate { (tab, content) ->
        group.addComponent(tab.tabButton)
        tab.key to TabData(tab, content)
    }

    override val selectedKeyProperty: Property<String> = defaultSelected.toProperty()

    /** The tabs currently shown in the strip. */
    public val visibleTabs: List<Tab> get() = strip.visibleTabs

    override val root: Component = components.hBox {
        role = ComponentRole.NONE
        preferredSize = size
        defaultComponentRenderer(NoOpComponentRenderer())

        tabBar = components.vBox {
            role = ComponentRole.TAB_BAR
            orientation = Orientation.VERTICAL
            preferredSize = Size(tabWidth, size.height)
        }

        content = components.panel {
            role = ComponentRole.TAB_BAR
            part = Part.CONTENT
            preferredSize = size.withRelativeWidth(-tabWidth)
        }
        childrenToAdd = listOf(tabBar, content)

        strip = TabStrip(
            strip = tabBar,
            tabs = order,
            horizontal = false,
            capacity = tabBar.contentSize.height,
            scrollTabSize = 3,
            sizeOf = { it.tabButton.height },
            theme = theme,
        )

        group.selectedProperty.onChange { (_, newValue) ->
            newValue?.let { button ->
                val data = lookup[button.key] ?: return@let
                currentContent?.detach()?.moveTo(Position.ZERO)
                currentContent = content.addComponent(data.content)
                selectedKeyProperty.value = button.key
            }
        }
        selectedKeyProperty.onChange { (oldKey, key) ->
            val data = lookup[key]
            if (data == null) {
                // An unknown key is reported and the previous selection restored.
                reportError("Tab bar", IllegalArgumentException("No tab with key '$key'; keeping '$oldKey' selected"))
                selectedKeyProperty.value = oldKey
            } else {
                strip.ensureVisible(order.indexOf(data.tab))
                data.tab.isSelected = true
            }
        }
        strip.ensureVisible(order.indexOf(lookup.getValue(defaultSelected).tab))
        lookup.getValue(defaultSelected).tab.isSelected = true
    }
}
