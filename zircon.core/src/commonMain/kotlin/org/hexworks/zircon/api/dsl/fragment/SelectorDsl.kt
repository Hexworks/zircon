package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.zircon.api.builder.fragment.HorizontalTabBarBuilder
import org.hexworks.zircon.api.builder.fragment.SelectorBuilder
import org.hexworks.zircon.api.builder.fragment.VerticalTabBarBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.TabBar

/**
 * Creates a new [Selector] using the fragment builder DSL and returns it.
 */
fun <T : Any> buildSelector(
    width: Int,
    valuesProperty: ListProperty<T>,
    init: SelectorBuilder<T>.() -> Unit
): Selector<T> = SelectorBuilder(width, valuesProperty).apply(init).build()

/**
 * Creates a new [Selector] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Selector].
 */
fun <T : BaseContainerBuilder<*, *>, S : Any> T.selector(
    width: Int,
    valuesProperty: ListProperty<S>,
    init: SelectorBuilder<S>.() -> Unit
): Selector<S> = buildFragmentFor(this, SelectorBuilder(width, valuesProperty), init)

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
fun <T : BaseContainerBuilder<*, *>> T.horizontalTabBar(
    init: HorizontalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, HorizontalTabBarBuilder(), init)

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
fun <T : BaseContainerBuilder<*, *>> T.verticalTabBar(
    init: VerticalTabBarBuilder.() -> Unit
): TabBar = buildFragmentFor(this, VerticalTabBarBuilder(), init)
