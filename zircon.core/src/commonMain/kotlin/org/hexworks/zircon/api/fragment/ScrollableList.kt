package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment


public interface ScrollableList<T> : Fragment {
    public val items: List<T>
    public fun scrollTo(idx: Int)
}
