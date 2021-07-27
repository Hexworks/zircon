package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.DefaultVerticalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabBarBuilder
import org.hexworks.zircon.internal.fragment.impl.TabData

class VerticalTabBarBuilder private constructor(
    size: Size = Size.unknown(),
    defaultSelected: String? = null,
    position: Position = Position.zero(),
    tabs: List<TabBuilder> = listOf(),
    var tabWidth: Int = -1,
) : TabBarBuilder(
    size = size,
    defaultSelected = defaultSelected,
    tabs = tabs,
    position = position
), FragmentBuilder<TabBar, VerticalTabBarBuilder> {

    fun VerticalTabBarBuilder.tab(init: TabBuilder.() -> Unit) {
        require(tabWidth >= 3) {
            "tabWidth must be set before adding tabs"
        }
        tabs = tabs + TabBuilder.newBuilder().apply(init).apply {
            width = tabWidth
        }
    }

    val contentSize: Size
        get() {
            require(tabWidth >= 3) {
                "tabWidth must be set before using contentSize"
            }
            return size.withRelativeWidth(-tabWidth)
        }

    override fun withPosition(position: Position) = also {
        this.position = position
    }

    override fun createCopy() = VerticalTabBarBuilder(
        size = size,
        defaultSelected = defaultSelected,
        tabs = tabs,
        position = position,
        tabWidth = tabWidth
    )

    override fun build(): TabBar {
        checkCommonProperties()
        val finalTabs = tabs.map { it.build() }
        require(finalTabs.fold(0) { acc, next -> acc + next.tab.tabButton.height } <= size.height) {
            "There is not enough space to display all the tabs"
        }
        return DefaultVerticalTabBar(
            size = size,
            defaultSelected = defaultSelected ?: finalTabs.first().tab.key,
            tabs = finalTabs,
            tabWidth = tabWidth
        )
    }

    companion object {

        @JvmStatic
        fun newBuilder(): VerticalTabBarBuilder = VerticalTabBarBuilder()
    }


}
