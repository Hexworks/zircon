package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.RadioButton

/**
 * A Tab contains a [Selectable] button, that is bound to a block of content (a [Container]
 * usually). Whenever the [Tab] is [Selectable.isSelected] the block of content becomes visible
 * and the [Tab] will display a label instead of the button. Deselecting a [Tab] usually happens
 * when another [Tab] becomes selected.
 * @see HorizontalTabBar
 * @see VerticalTabBar
 */
interface Tab : Fragment {

    val tabButton: RadioButton

    var isSelected: Boolean
        get() = tabButton.isSelected
        set(value) {
            tabButton.isSelected = value
        }


    val key: String
        get() = tabButton.key

}
