package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.component.Component

data class TabMetadata(
    val label: String,
    val key: String,
    val content: Component
)
