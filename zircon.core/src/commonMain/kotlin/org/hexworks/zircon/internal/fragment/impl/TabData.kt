package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.fragment.Tab

data class TabData(
    val tab: Tab,
    val content: Component
)
