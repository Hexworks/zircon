package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

class TilesetSelectorBuilder private constructor(
        width: Int,
        private var initialTileset: TilesetResource
) : FragmentBuilder<MultiSelect<TilesetResource>, TilesetSelectorBuilder> {

    companion object {
        fun newBuilder(width: Int, initialTileset: TilesetResource) = TilesetSelectorBuilder(width, initialTileset)
    }

    private val multiSelectBuilder: MultiSelectBuilder<TilesetResource>

    init {
        val tilesets = BuiltInCP437TilesetResource.values()
                .filter { it.width == initialTileset.width && it.height == initialTileset.height }
        multiSelectBuilder = Fragments.multiSelect(width, tilesets)
    }

    override fun withPosition(position: Position) = also { multiSelectBuilder.withPosition(position) }

    override fun withPosition(x: Int, y: Int) = withPosition(Position.create(x, y))

    override fun build(): MultiSelect<TilesetResource> = multiSelectBuilder.build()

    override fun createCopy(): Builder<MultiSelect<TilesetResource>> {
        TODO("Not yet implemented")
    }

}
