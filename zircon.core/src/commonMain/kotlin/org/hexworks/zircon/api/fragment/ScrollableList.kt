package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.component.Fragment

@Beta
interface ScrollableList<T> : Fragment {
    val items: List<T>
    fun scrollTo(idx: Int)
}
