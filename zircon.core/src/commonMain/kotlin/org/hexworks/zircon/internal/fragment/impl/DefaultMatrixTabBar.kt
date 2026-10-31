package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.MatrixTabBar
import org.hexworks.zircon.api.fragment.MatrixTabBar.Orientation
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.util.reportError

/**
 * The horizontal strip runs along the top, right of the vertical strip, and the vertical strip
 * down the left below it; the content panel fills the rest. See [MatrixTabBar] for how the
 * primary strip filters the secondary one.
 */
public class DefaultMatrixTabBar internal constructor(
    size: Size,
    tabWidth: Int,
    private val primary: Orientation,
    private val horizontalTabs: List<Tab>,
    private val verticalTabs: List<Tab>,
    private val views: Map<Pair<String, String>, Component>,
    defaultHorizontal: String,
    defaultVertical: String,
) : MatrixTabBar {

    private val horizontalLookup = horizontalTabs.associateBy { it.key }
    private val verticalLookup = verticalTabs.associateBy { it.key }

    override val selectedHorizontalKeyProperty: Property<String> = defaultHorizontal.toProperty()
    override val selectedVerticalKeyProperty: Property<String> = defaultVertical.toProperty()

    private lateinit var horizontalBox: Container
    private lateinit var verticalBox: Container
    private lateinit var content: Panel
    private lateinit var horizontalStrip: TabStrip
    private lateinit var verticalStrip: TabStrip

    private var currentContent: AttachedComponent? = null
    private var currentView: Component? = null

    /** The horizontal tabs currently shown in their strip. */
    public val visibleHorizontalTabs: List<Tab> get() = horizontalStrip.visibleTabs

    /** The vertical tabs currently shown in their strip. */
    public val visibleVerticalTabs: List<Tab> get() = verticalStrip.visibleTabs

    /** The view shown right now. */
    public val shownContent: Component? get() = currentView

    internal fun horizontalTab(key: String): Tab = horizontalLookup.getValue(key)
    internal fun verticalTab(key: String): Tab = verticalLookup.getValue(key)

    override val root: Component = panel {
        preferredSize = size
        componentRenderer = NoOpComponentRenderer()

        horizontalBox = hBox {
            position = Position(tabWidth, 0)
            preferredSize = Size(size.width - tabWidth, STRIP_HEIGHT)
            decorations { +margin(0, 1) }
            componentRenderer = NoOpComponentRenderer()
        }
        verticalBox = vBox {
            position = Position(0, STRIP_HEIGHT)
            preferredSize = Size(tabWidth, size.height - STRIP_HEIGHT)
            decorations { +margin(1, 0) }
            componentRenderer = NoOpComponentRenderer()
        }
        content = panel {
            position = Position(tabWidth, STRIP_HEIGHT)
            preferredSize = Size(size.width - tabWidth, size.height - STRIP_HEIGHT)
        }
    }

    private val primaryKey: Property<String>
        get() = if (primary == Orientation.HORIZONTAL) selectedHorizontalKeyProperty else selectedVerticalKeyProperty
    private val secondaryKey: Property<String>
        get() = if (primary == Orientation.HORIZONTAL) selectedVerticalKeyProperty else selectedHorizontalKeyProperty
    private val secondaryTabs: List<Tab>
        get() = if (primary == Orientation.HORIZONTAL) verticalTabs else horizontalTabs
    private val secondaryStrip: TabStrip
        get() = if (primary == Orientation.HORIZONTAL) verticalStrip else horizontalStrip

    init {
        val horizontalGroup = groupOf(horizontalTabs)
        val verticalGroup = groupOf(verticalTabs)
        val primaryTabs = if (primary == Orientation.HORIZONTAL) horizontalTabs else verticalTabs

        horizontalStrip = TabStrip(
            strip = horizontalBox,
            tabs = if (primary == Orientation.HORIZONTAL) horizontalTabs else secondaryTabsFor(defaultVertical),
            horizontal = true,
            capacity = size.width - tabWidth - 2,
            scrollTabSize = SCROLL_TAB_WIDTH,
            sizeOf = { it.tabButton.width },
        )
        verticalStrip = TabStrip(
            strip = verticalBox,
            tabs = if (primary == Orientation.VERTICAL) verticalTabs else secondaryTabsFor(defaultHorizontal),
            horizontal = false,
            capacity = size.height - STRIP_HEIGHT - 2,
            scrollTabSize = STRIP_HEIGHT,
            sizeOf = { it.tabButton.height },
        )

        horizontalGroup.selectedButtonProperty.onChange { (_, button) ->
            if (button != null) selectedHorizontalKeyProperty.value = button.key
        }
        verticalGroup.selectedButtonProperty.onChange { (_, button) ->
            if (button != null) selectedVerticalKeyProperty.value = button.key
        }

        primaryKey.onChange { (oldKey, key) ->
            val tab = primaryTabs.firstOrNull { it.key == key }
            if (tab == null) {
                reportError("Matrix tab bar", IllegalArgumentException("No tab with key '$key'; keeping '$oldKey' selected"))
                primaryKey.value = oldKey
            } else {
                val strip = if (primary == Orientation.HORIZONTAL) horizontalStrip else verticalStrip
                strip.ensureVisible(primaryTabs.indexOf(tab))
                tab.isSelected = true
                val available = secondaryTabsFor(key)
                secondaryStrip.replaceTabs(available)
                val secondary = available.firstOrNull { it.key == secondaryKey.value } ?: available.first()
                if (secondary.key == secondaryKey.value) {
                    secondaryStrip.ensureVisible(available.indexOf(secondary))
                    showView()
                } else {
                    secondaryKey.value = secondary.key
                }
            }
        }
        secondaryKey.onChange { (oldKey, key) ->
            val tab = secondaryTabs.firstOrNull { it.key == key }
            val available = secondaryTabsFor(primaryKey.value)
            if (tab == null || tab !in available) {
                val reason = if (tab == null) "No tab with key '$key'" else "No view at ('${primaryKey.value}', '$key')"
                reportError("Matrix tab bar", IllegalArgumentException("$reason; keeping '$oldKey' selected"))
                secondaryKey.value = oldKey
            } else {
                secondaryStrip.ensureVisible(available.indexOf(tab))
                tab.isSelected = true
                showView()
            }
        }

        val primaryTab = primaryTabs.first { it.key == primaryKey.value }
        val secondaryTab = secondaryTabs.first { it.key == secondaryKey.value }
        (if (primary == Orientation.HORIZONTAL) horizontalStrip else verticalStrip)
            .ensureVisible(primaryTabs.indexOf(primaryTab))
        secondaryStrip.ensureVisible(secondaryTabsFor(primaryKey.value).indexOf(secondaryTab))
        primaryTab.isSelected = true
        secondaryTab.isSelected = true
        showView()
    }

    private fun groupOf(tabs: List<Tab>): RadioButtonGroup = radioButtonGroup { }.apply {
        tabs.forEach { addComponent(it.tabButton) }
    }

    /** The secondary tabs that have a view together with the primary tab [primaryKey], in order. */
    private fun secondaryTabsFor(primaryKey: String): List<Tab> = secondaryTabs.filter { tab ->
        views.containsKey(cellOf(primaryKey, tab.key))
    }

    private fun cellOf(primaryKey: String, secondaryKey: String): Pair<String, String> =
        if (primary == Orientation.HORIZONTAL) primaryKey to secondaryKey else secondaryKey to primaryKey

    private fun showView() {
        val view = views[selectedHorizontalKey to selectedVerticalKey] ?: return
        if (currentView === view) return
        currentContent?.detach()?.moveTo(Position.ZERO)
        currentContent = content.addComponent(view)
        currentView = view
    }

    private companion object {
        const val STRIP_HEIGHT = 3
        const val SCROLL_TAB_WIDTH = 5
    }
}
