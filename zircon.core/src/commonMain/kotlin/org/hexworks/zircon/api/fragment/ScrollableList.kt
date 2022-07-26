package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment


interface ScrollableList<T> : Fragment {
    val items: List<T>
    fun scrollTo(idx: Int)
}
