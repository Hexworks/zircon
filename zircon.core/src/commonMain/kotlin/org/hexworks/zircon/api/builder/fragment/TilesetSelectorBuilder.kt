package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.fragment.TilesetSelectorBuilder.Companion.newBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.fragment.MultiSelect
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource

/**
 * A [MultiSelect] to change the tileset of multiple components at runtime.
 *
 * @see newBuilder
 */
class TilesetSelectorBuilder private constructor(
        width: Int,
        vararg componentsToUpdate: TilesetOverride
) : FragmentBuilder<MultiSelect<TilesetResource>, TilesetSelectorBuilder> {

    companion object {
        /**
         * Creates a new builder to build a [MultiSelect] that contains a list of [TilesetResource]s.
         *
         * When a new tileset is selected all components supplied are being updated with the new tileset.
         * This is handy to quickly see how a UI element looks with a different tileset.
         *
         * The default tileset that is selected is the current tileset of the first component. You can override it with [withTilesetSelected].
         */
        fun newBuilder(width: Int, vararg componentsToUpdate: TilesetOverride) = TilesetSelectorBuilder(width, *componentsToUpdate)
    }

    private val multiSelectBuilder: MultiSelectBuilder<TilesetResource>

    init {
        require(componentsToUpdate.isNotEmpty()) {
            "TilesetSelector needs a list of components it can update. Supplied list was empty."
        }
        val initialTileset: TilesetResource = componentsToUpdate.first().tileset
        val tilesets: List<TilesetResource> = BuiltInCP437TilesetResource.values()
                .filter { it.width == initialTileset.width && it.height == initialTileset.height }
                .sortedBy { it.name }
        multiSelectBuilder = Fragments
                .multiSelect(width, tilesets)
                .withCallback { _, newTileset -> componentsToUpdate.forEach { it.tilesetProperty.updateValue(newTileset) } }
                .withDefaultSelected(initialTileset)
    }

    /**
     * The given tileset is selected by default. It has to be present in the list of tilesets!
     * It must be one with the same dimensions as the one the components have, that have initially been provided in [newBuilder].
     */
    fun withTilesetSelected(initialTileset: TilesetResource) =
            multiSelectBuilder.withDefaultSelected(initialTileset)

    override fun withPosition(position: Position) = also { multiSelectBuilder.withPosition(position) }

    override fun withPosition(x: Int, y: Int) = withPosition(Position.create(x, y))

    override fun build(): MultiSelect<TilesetResource> = multiSelectBuilder.build()

    override fun createCopy(): Builder<MultiSelect<TilesetResource>> =
            //TODO: FIX THIS
            multiSelectBuilder.createCopy()

}
