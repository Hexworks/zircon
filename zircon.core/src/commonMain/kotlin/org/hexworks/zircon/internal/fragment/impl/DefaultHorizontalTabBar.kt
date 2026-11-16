package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.behavior.extensions.width
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
import org.hexworks.zircon.api.fragment.TabData
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.util.reportError
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentTheme

public class DefaultHorizontalTabBar internal constructor(
    size: Size,
    defaultSelected: String,
    tabs: List<TabData>,
    theme: ComponentTheme? = null,
) : TabBar {
    private val components = ComponentFactory { theme }

    private lateinit var tabBar: HBox
    private lateinit var content: Panel
    private lateinit var strip: TabStrip

    private var currentContent: AttachedComponent? = null
    private val group = radioButtonGroup { }

    private val order = tabs.map { it.tab }
    private val lookup = tabs.associate { (tab, content) ->
        group.addComponent(tab.tabButton)
        tab.key to TabData(tab, content)
    }

    override val selectedKeyProperty: Property<String> = defaultSelected.toProperty()

    internal fun tab(key: String): Tab = lookup.getValue(key).tab

    /** The tabs currently shown in the strip. */
    public val visibleTabs: List<Tab> get() = strip.visibleTabs

    override val root: Component = components.vBox {
        role = ComponentRole.NONE
        preferredSize = size
        defaultComponentRenderer(NoOpComponentRenderer())

        tabBar = components.hBox {
            role = ComponentRole.TAB_BAR
            preferredSize = Size(size.width, 3)
            if (theme.lacks(ComponentRole.TAB_BAR, kind = ComponentRole.HBOX)) {
                defaultDecorationRenderers(listOf(margin(0, 1)))
                defaultComponentRenderer(NoOpComponentRenderer())
            }
        }

        content = components.panel {
            role = ComponentRole.TAB_BAR
            part = Part.CONTENT
            preferredSize = size.withRelativeHeight(-3)
        }
        childrenToAdd = listOf(tabBar, content)

        strip = TabStrip(
            strip = tabBar,
            tabs = order,
            horizontal = true,
            capacity = tabBar.contentSize.width,
            scrollTabSize = SCROLL_TAB_WIDTH,
            sizeOf = { it.tabButton.width },
            theme = theme,
        )

        group.selectedButtonProperty.onChange { (_, newValue) ->
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

    private companion object {
        const val SCROLL_TAB_WIDTH = 5
    }
}
