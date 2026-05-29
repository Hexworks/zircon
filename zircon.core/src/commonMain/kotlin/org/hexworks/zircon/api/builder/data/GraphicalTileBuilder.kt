package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.tile.GraphicalTile
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

/**
 * Builds [GraphicalTile]s.
 * Defaults:
 * - Default name is a space
 * - Default tags is an empty set
 * - Default tileset comes from the [RuntimeConfig]
 */
@ZirconDsl
class GraphicalTileBuilder : Builder<GraphicalTile> {

    var name: String = " "
    var tags: Set<String> = setOf()
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var modifiers: Set<Modifier> = setOf()

    override fun build(): GraphicalTile {
        return GraphicalTile(
            name = name,
            tags = tags,
            tileset = tileset,
            modifiers = modifiers
        )
    }
}

fun graphicalTile(init: GraphicalTileBuilder.() -> Unit): GraphicalTile {
    return GraphicalTileBuilder().apply(init).build()
}
