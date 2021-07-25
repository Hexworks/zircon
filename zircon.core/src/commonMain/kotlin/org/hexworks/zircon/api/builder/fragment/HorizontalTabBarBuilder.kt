package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabMetadata

class HorizontalTabBarBuilder: FragmentBuilder<TabBar, HorizontalTabBarBuilder>, Builder<TabBar> {

    var size: Size = Size.unknown()
    var tabWidth: Int = 3
    var defaultSelected: String? = null
    var tabs: List<TabMetadata> = listOf()

    override fun withPosition(position: Position): HorizontalTabBarBuilder {
        TODO("not implemented")
    }

    override fun withPosition(x: Int, y: Int): HorizontalTabBarBuilder {
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
