package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.fragment.Tab

public data class TabData(
    val tab: Tab,
    val content: Component
)
