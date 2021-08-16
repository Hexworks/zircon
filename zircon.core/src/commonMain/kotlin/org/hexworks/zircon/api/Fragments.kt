@file:Suppress("DeprecatedCallableAddReplaceWith")

package org.hexworks.zircon.api

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.fragment.*
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.fragment.Table
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.fragment.TabBar
import org.hexworks.zircon.api.fragment.MenuBar
import kotlin.jvm.JvmStatic

/**
 * This *facade* object provides builders for the built-in [Fragment]s
 * (groups of Components that come with their own logic).
 */
object Fragments {

    /**
     * Creates a [VerticalTabBarBuilder] to create [TabBar]s.
     */
    @JvmStatic
    fun verticalTabBar() = VerticalTabBarBuilder.newBuilder()

    /**
     * Creates a [HorizontalTabBarBuilder] to create [TabBar]s.
     */
    @JvmStatic
    fun horizontalTabBar() = HorizontalTabBarBuilder.newBuilder()

    /**
     * Creates a [MenuBarBuilder] to create [MenuBar]s.
     */
    @JvmStatic
    fun <T: Any> menuBar() = MenuBarBuilder.newBuilder<T>()

    /**
     * Creates a [SelectorBuilder] to create [Selector]s.
     */
    @JvmStatic
    fun <T : Any> selector() = SelectorBuilder.newBuilder<T>()

    /**
     * Creates a new [TableBuilder] to build a [org.hexworks.zircon.api.fragment.Table] with its [TableColumns].
     *
     * @param data a simple list that will be converted to an [ObservableList] and passed to [table]. It is
     * generally recommended to directly pass an [ObservableList] to the table fragment.
     */
    @Beta
    fun <T : Any> table(data: List<T>): TableBuilder<T> = table(data.toProperty())

    /**
     * Creates a new [TableBuilder] to build a [Table] with its [TableColumns].
     */
    @Beta
    fun <T : Any> table(): TableBuilder<T> = TableBuilder.newBuilder()

    /**
     * Creates a [SelectorBuilder] to create [Selector]s.
     */
    @Deprecated("Use the function without parameters instead.")
    @JvmStatic
    fun <T : Any> selector(width: Int, values: List<T>): Nothing = SelectorBuilder.newBuilder(width, values)

    /**
     * Creates a new [TilesetSelectorBuilder] to build [Selector]s for [TilesetResource]s.
     */
    @Deprecated("This class is redundant, use a regular Selector instead")
    @JvmStatic
    fun tilesetSelector(
        width: Int,
        tileset: TilesetResource
    ): TilesetSelectorBuilder = TilesetSelectorBuilder.newBuilder(width, tileset)

    /**
     * Creates a new [TilesetSelectorBuilder] to build [Selector]s for [ColorTheme]s.
     */
    @Deprecated("This class is redundant, use a regular Selector instead")
    @JvmStatic
    fun colorThemeSelector(
        width: Int,
        theme: ColorTheme
    ): ColorThemeSelectorBuilder = ColorThemeSelectorBuilder.newBuilder(width, theme)
}
