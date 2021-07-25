package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabMetadata

class TabBuilder: FragmentBuilder<Tab, TabBuilder>, Builder<Tab> {

    var size: Size = Size.unknown()
    var tabWidth: Int = 3
    var defaultSelected: String? = null
    var tabs: List<TabMetadata> = listOf()

    override fun withPosition(position: Position): TabBuilder {
        TODO("not implemented")
    }

    override fun withPosition(x: Int, y: Int): TabBuilder {
        TODO("not implemented")
    }

    override fun createCopy(): Builder<TabBar> {
        TODO("not implemented")
    }

    override fun build(): TabBar {
        return DefaultHorizontalTabBar(
            size = size,
            tabWidth = tabWidth,
            defaultSelected = defaultSelected ?: tabs.first().key,
            tabs = tabs
        )
    }
}
