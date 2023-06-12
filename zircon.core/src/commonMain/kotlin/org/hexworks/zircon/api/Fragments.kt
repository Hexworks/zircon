package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.fragment.*
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.fragment.MenuBar
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.Table

/**
 * This *facade* object provides builders for the built-in [Fragment]s
 * (groups of Components that come with their own logic).
 */
object Fragments {

    /**
     * Creates a [VerticalTabBarBuilder] to create [TabBar]s.
     */
    fun verticalTabBar() = VerticalTabBarBuilder.newBuilder()

    /**
     * Creates a [HorizontalTabBarBuilder] to create [TabBar]s.
     */
    fun horizontalTabBar() = HorizontalTabBarBuilder.newBuilder()

    /**
     * Creates a [MenuBarBuilder] to create [MenuBar]s.
     */
    fun <T : Any> menuBar() = MenuBarBuilder.newBuilder<T>()

    /**
     * Creates a new [TableBuilder] to build a [Table] with its [TableColumns].
     */
    fun <T : Any> table(): TableBuilder<T> = TableBuilder.newBuilder()

    /**
     * Creates a [SelectorBuilder] to create [Selector]s.
     */
    fun <T : Any> selector() = SelectorBuilder.newBuilder<T>()

}
