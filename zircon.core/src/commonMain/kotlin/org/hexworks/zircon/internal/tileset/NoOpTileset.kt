package org.hexworks.zircon.internal.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import kotlin.reflect.KClass

/**
 * The tileset a [DefaultTilesetLoader] hands out for a resource it has no factory for: it
 * has the resource's cell size and draws nothing, so the rest of the frame still renders.
 */
public class NoOpTileset<T : Any>(
    resource: TilesetResource,
    override val targetType: KClass<T>,
) : Tileset<T> {

    override val id: UUID = resource.id
    override val width: Int = resource.width
    override val height: Int = resource.height
    override val size: Size = Size(width, height)

    override fun drawTile(tile: Tile, context: T, position: Position) {}
}
