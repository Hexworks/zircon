package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.withRelativeHeight
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultHorizontalTabBar
import org.hexworks.zircon.internal.fragment.impl.TabBarBuilder

@ZirconDsl
public class HorizontalTabBarBuilder(
    size: Size = Size.UNKNOWN,
    defaultSelected: String? = null,
    tabs: List<TabBuilder> = listOf(),
    position: Position = Position.ZERO
) : TabBarBuilder(
    size = size,
    defaultSelected = defaultSelected,
    tabs = tabs,
    position = position
), FragmentBuilder<TabBar> {

    public fun HorizontalTabBarBuilder.tab(init: TabBuilder.() -> Unit) {
        tabs = tabs + TabBuilder().apply(init)
    }

    public val contentSize: Size
        get() = size.withRelativeHeight(-3)


    override fun build(): TabBar {
        checkCommonProperties()
        val finalTabs = tabs.map { it.build() }
        // Tabs that do not fit are scrolled, so any number of tabs is fine.
        return DefaultHorizontalTabBar(
            size = size,
            defaultSelected = defaultSelected ?: finalTabs.first().tab.key,
            tabs = finalTabs
        ).also { it.root.moveTo(position) }
    }
}

/**
 * Creates a new [TabBar] using the fragment builder DSL and returns it.
 */

public fun horizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = HorizontalTabBarBuilder().apply(init).build()

/**
 * Creates a new horizontal [TabBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TabBar].
 */

public fun AnyContainerBuilder.horizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, HorizontalTabBarBuilder(), init)
