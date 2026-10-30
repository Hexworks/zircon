package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.builder.fragment.verticalTabBar
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * A vertical tab bar with more tabs than fit: the `▲...` / `▼...` buttons and the mouse
 * wheel over the strip scroll it, and selecting a tab scrolls it into view.
 */
public val verticalTabBarExample: Example = Example("V tab bar") { context ->
    val size = context.contentSize
    val contentSize = Size(size.width - TAB_WIDTH, size.height)
    val tabBar = verticalTabBar {
        this.size = size
        tabWidth = TAB_WIDTH
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

private const val TAB_WIDTH = 12
