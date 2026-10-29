package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.config.ZirconDefaults

/**
 * Builds [GraphicalTile]s.
 * Defaults:
 * - Default name is a space
 * - Default tags is an empty set
 * - Default tileset comes from the [ZirconDefaults]
 */
@ZirconDsl
public class GraphicalTileBuilder : Builder<GraphicalTile> {

    public var name: String = " "
    public var tags: Set<String> = setOf()
    public var tileset: TilesetResource = ZirconDefaults.current.tileset
    public var modifiers: Set<Modifier> = setOf()

    override fun build(): GraphicalTile {
        return GraphicalTile(
            name = name,
            tags = tags,
            tileset = tileset,
            modifiers = modifiers
        )
    }
}

public fun graphicalTile(init: GraphicalTileBuilder.() -> Unit): GraphicalTile {
    return GraphicalTileBuilder().apply(init).build()
}
