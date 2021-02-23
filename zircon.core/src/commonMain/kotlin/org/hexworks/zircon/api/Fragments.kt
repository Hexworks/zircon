package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.fragment.ColorThemeSelectorBuilder
import org.hexworks.zircon.api.builder.fragment.SelectorBuilder
import org.hexworks.zircon.api.builder.fragment.TableBuilder
import org.hexworks.zircon.api.builder.fragment.TilesetSelectorBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.fragment.Selector
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This *facade* object provides builders for the built-in [Fragment]s
 * (more complex Components that come with their own logic).
 */
object Fragments {

    /**
     * Creates a [SelectorBuilder] to create [Selector]s.
     */
    @JvmStatic
    fun <M : Any> selector(width: Int, values: List<M>) = SelectorBuilder.newBuilder(width, values)

    /**
     * Creates a new [TilesetSelectorBuilder] to build [Selector]s for [TilesetResource]s.
     */
    @JvmStatic
    fun tilesetSelector(
        width: Int,
        tileset: TilesetResource
    ): TilesetSelectorBuilder = TilesetSelectorBuilder.newBuilder(width, tileset)

    /**
     * Creates a new [TilesetSelectorBuilder] to build [Selector]s for [ColorTheme]s.
     */
    @JvmStatic
    fun colorThemeSelector(
        width: Int,
        theme: ColorTheme
    ): ColorThemeSelectorBuilder = ColorThemeSelectorBuilder.newBuilder(width, theme)

    fun <M: Any> table(data: List<M>): TableBuilder<M> =
        TableBuilder(data)

}
