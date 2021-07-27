package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabBarBuilder
import org.hexworks.zircon.internal.fragment.impl.TabData

class HorizontalTabBarBuilder private constructor(
    size: Size = Size.unknown(),
    defaultSelected: String? = null,
    tabs: List<TabBuilder> = listOf(),
    position: Position = Position.zero()
) : TabBarBuilder(
    size = size,
    defaultSelected = defaultSelected,
    tabs = tabs,
    position = position
), FragmentBuilder<TabBar, HorizontalTabBarBuilder> {

    fun HorizontalTabBarBuilder.tab(init: TabBuilder.() -> Unit) {
        tabs = tabs + TabBuilder.newBuilder().apply(init)
    }

    val contentSize: Size
        get() = size.withRelativeHeight(-3)

    override fun withPosition(position: Position) = also {
        this.position = position
    }

    override fun createCopy() = HorizontalTabBarBuilder(
        size = size,
        defaultSelected = defaultSelected,
        tabs = tabs,
        position = position
    )

    override fun build(): TabBar {
        checkCommonProperties()
        val finalTabs = tabs.map { it.build() }
        require(finalTabs.fold(0) { acc, next -> acc + next.tab.tabButton.width } <= size.width) {
            "There is not enough space to display all the tabs"
        }
        return DefaultHorizontalTabBar(
            size = size,
            defaultSelected = defaultSelected ?: finalTabs.first().tab.key,
            tabs = finalTabs
        )
    }

    companion object {

        @JvmStatic
        fun newBuilder(): HorizontalTabBarBuilder = HorizontalTabBarBuilder()
    }


}
