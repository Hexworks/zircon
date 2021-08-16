package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.zircon.api.builder.fragment.VerticalTabBarBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.TabBar

/**
 * Creates a new [TabBar] using the fragment builder DSL and returns it.
 */
fun buildVerticalTabBar(
    init: VerticalTabBarBuilder.() -> Unit
): TabBar = VerticalTabBarBuilder.newBuilder().apply(init).build()

/**
 * Creates a new horizontal [TabBar] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TabBar].
 */
fun AnyContainerBuilder.verticalTabBar(
    init: VerticalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, VerticalTabBarBuilder.newBuilder(), init)
