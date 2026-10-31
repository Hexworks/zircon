package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.component.Fragment

/**
 * Two strips of [Tab]s, a horizontal one along the top and a vertical one along the left,
 * around one content panel. A view lives at a (horizontal, vertical) pair of tabs and is
 * shown while both are selected. One strip is the **primary**: it always shows every tab of
 * its axis; the **secondary** strip shows only the tabs that have a view together with the
 * selected primary tab. Selecting a primary tab keeps the secondary selection when that pair
 * has a view and moves to the first one that has otherwise.
 *
 * Unlike a vertical tab bar nested into a horizontal one, both strips sit beside the
 * content, so every tab, including the selected ones, stays visible.
 */
public interface MatrixTabBar : Fragment {

    /** The [Tab.key] of the selected horizontal tab. Setting it selects that tab. */
    public val selectedHorizontalKeyProperty: Property<String>

    /** The [Tab.key] of the selected vertical tab. Setting it selects that tab. */
    public val selectedVerticalKeyProperty: Property<String>

    public var selectedHorizontalKey: String
        get() = selectedHorizontalKeyProperty.value
        set(value) {
            selectedHorizontalKeyProperty.value = value
        }

    public var selectedVerticalKey: String
        get() = selectedVerticalKeyProperty.value
        set(value) {
            selectedVerticalKeyProperty.value = value
        }

    /** Which strip drives the other one. */
    public enum class Orientation {
        HORIZONTAL,
        VERTICAL,
    }
}
