package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.fragment.MultiSelectBuilder
import org.hexworks.zircon.api.builder.fragment.TilesetSelectorBuilder
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.resource.TilesetResource
import kotlin.jvm.JvmStatic

/**
 * Provides builders for [Fragment]s (more complex Components that come with their own logic).
 */
object Fragments {

    /**
     * @see org.hexworks.zircon.api.fragment.MultiSelect
     */
    @JvmStatic
    fun <M : Any> multiSelect(width: Int, values: List<M>) = MultiSelectBuilder.newBuilder(width, values)

    @JvmStatic
    fun tilesetSelector(width: Int, initialTileset: TilesetResource): TilesetSelectorBuilder = TilesetSelectorBuilder.newBuilder(width, initialTileset)

}