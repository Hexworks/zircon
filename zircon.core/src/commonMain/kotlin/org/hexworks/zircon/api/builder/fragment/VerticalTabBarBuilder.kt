package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultVerticalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabBarBuilder


@ZirconDsl
class VerticalTabBarBuilder(
    size: Size = Size.unknown(),
    defaultSelected: String? = null,
    position: Position = Position.zero(),
    tabs: List<TabBuilder> = listOf(),
) : TabBarBuilder(
    size = size,
    defaultSelected = defaultSelected,
    tabs = tabs,
    position = position
), FragmentBuilder<TabBar> {

    var tabWidth: Int = -1

    val contentSize: Size
        get() {
            require(tabWidth >= 3) {
                "tabWidth must be set before using contentSize"
            }
            return size.withRelativeWidth(-tabWidth)
        }

    fun VerticalTabBarBuilder.tab(init: TabBuilder.() -> Unit) {
        require(tabWidth >= 3) {
            "tabWidth must be set before adding tabs"
        }
        val tabWidth = this.tabWidth
        tabs = tabs + TabBuilder().apply(init).apply {
            width = tabWidth
        }
    }

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
}

/**
 * Creates a new [TabBar] using the fragment builder DSL and returns it.
 */
fun buildVerticalTabBar(
    init: VerticalTabBarBuilder.() -> Unit
): TabBar = VerticalTabBarBuilder().apply(init).build()

/**
 * Creates a new horizontal [TabBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TabBar].
 */
fun AnyContainerBuilder.verticalTabBar(
    init: VerticalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, VerticalTabBarBuilder(), init)