package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabBarBuilder

@ZirconDsl
class HorizontalTabBarBuilder(
    size: Size = Size.unknown(),
    defaultSelected: String? = null,
    tabs: List<TabBuilder> = listOf(),
    position: Position = Position.zero()
) : TabBarBuilder(
    size = size,
    defaultSelected = defaultSelected,
    tabs = tabs,
    position = position
), FragmentBuilder<TabBar> {

    fun HorizontalTabBarBuilder.tab(init: TabBuilder.() -> Unit) {
        tabs = tabs + TabBuilder().apply(init)
    }

    val contentSize: Size
        get() = size.withRelativeHeight(-3)


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
}

/**
 * Creates a new [TabBar] using the fragment builder DSL and returns it.
 */

fun buildHorizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = HorizontalTabBarBuilder().apply(init).build()

/**
 * Creates a new horizontal [TabBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TabBar].
 */

fun AnyContainerBuilder.horizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, HorizontalTabBarBuilder(), init)
