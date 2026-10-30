package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.builder.fragment.horizontalTabBar
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * A horizontal tab bar with more tabs than fit: the `◄..` / `..►` buttons and the mouse
 * wheel over the strip scroll it, and selecting a tab scrolls it into view.
 */
public val horizontalTabBarExample: Example = Example("H tab bar") { context ->
    val size = context.contentSize
    val contentSize = Size(size.width, size.height - 3)
    val tabBar = horizontalTabBar {
        this.size = size
        defaultSelected = TAB_NAMES.first()
        TAB_NAMES.forEach { name ->
            tab {
                key = name
                label = name
                content = tabContent(name, contentSize)
            }
        }
    }
    ExampleView(tabBar.root)
}
