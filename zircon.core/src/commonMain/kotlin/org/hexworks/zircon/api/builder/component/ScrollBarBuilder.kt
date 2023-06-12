package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.ScrollBar
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer

abstract class ScrollBarBuilder<T : ScrollBar>(
    initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T>(initialRenderer) {

    var numberOfScrollableItems: Int = 100
        set(value) {
            require(value > 0) { "Number of items must be greater than 0." }
            field = value
        }

    var itemsShownAtOnce: Int = 1
        set(value) {
            require(value > 0) { "Count must be greater than 0." }
            field = value
        }
}
