package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.Fragment

/**
 * A bar of [Tab]s where exactly one is selected at a time; the selected tab's content
 * is the one shown. Tabs that do not fit into the bar are scrolled: a `…` button at
 * either end of the strip appears while there are tabs beyond it, the mouse wheel over
 * the strip scrolls too, and selecting a tab scrolls it into view.
 */
public interface TabBar : Fragment {

    /**
     * The [Tab.key] of the selected tab. Setting it selects that tab; selecting a tab
     * (by clicking it) updates it.
     */
    public val selectedKeyProperty: Property<String>

    public var selectedKey: String
        get() = selectedKeyProperty.value
        set(value) {
            selectedKeyProperty.value = value
        }
}
