package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable


/**
 * A [ScrollBar] is a [Component] that can be used for scrolling a [Scrollable].
 */
interface ScrollBar : Component {
    val scrollDirection: ScrollDirection
}
