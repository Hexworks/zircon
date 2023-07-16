package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.DefaultGraphicalTile
import org.hexworks.zircon.internal.dsl.ZirconDsl

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

    override fun build(): GraphicalTile {
        return DefaultGraphicalTile(
            name = name,
            tags = tags,
            tileset = tileset
        )
    }
}

fun graphicalTile(init: GraphicalTileBuilder.() -> Unit): GraphicalTile {
    return GraphicalTileBuilder().apply(init).build()
}
