package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.builder.fragment.HorizontalTabBarBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.TabBar

/**
 * Creates a new [TabBar] using the fragment builder DSL and returns it.
 */
@Beta
fun buildHorizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = HorizontalTabBarBuilder.newBuilder().apply(init).build()

/**
 * Creates a new horizontal [TabBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TabBar].
 */
@Beta
fun AnyContainerBuilder.horizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, HorizontalTabBarBuilder.newBuilder(), init)
