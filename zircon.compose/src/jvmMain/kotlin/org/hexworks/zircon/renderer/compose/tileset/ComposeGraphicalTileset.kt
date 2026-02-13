package org.hexworks.zircon.renderer.compose.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType.GRAPHICAL_TILE
import org.hexworks.zircon.api.data.tile.GraphicalTile
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.renderer.compose.ComposeContext
import kotlin.reflect.KClass

/**
 * Graphical tileset implementation for Compose.
 *
 * Loads tiles from a ZIP archive containing a tileinfo.yml metadata file
 * and sprite sheet images. Tiles are looked up by name.
 *
 * Note: Full implementation requires ZIP file handling and YAML parsing.
 * This is a placeholder that provides the basic structure.
 */
class ComposeGraphicalTileset(
    private val resource: TilesetResource
) : Tileset<ComposeContext> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int get() = resource.width
    override val height: Int get() = resource.height
    override val targetType: KClass<ComposeContext> = ComposeContext::class

    private var loadingState = LoadingState.NOT_LOADED
    private var tileLookup = mutableMapOf<String, ByteArray>()

    init {
        require(resource.tileType == GRAPHICAL_TILE) {
            "Graphical tilesets only support ${GRAPHICAL_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: ComposeContext, position: Position) {
        require(tile is GraphicalTile) {
            "A graphical tile renderer can only render ${GraphicalTile::class.simpleName}s. Offending tile: $tile."
        }

        if (loadingState == LoadingState.NOT_LOADED) {
            loadingState = LoadingState.LOADING
            loadTileset()
        }

        if (loadingState == LoadingState.LOADED) {
            var finalTile: GraphicalTile = tile
            finalTile.modifiers.filterIsInstance<TileModifier<GraphicalTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }

            val px = position.x * width
            val py = position.y * height

            // Draw transparent background
            context.fillRect(px, py, width, height, 0x00000000)

            // Draw the tile sprite if available
            val sprite = tileLookup[finalTile.name]
            if (sprite != null) {
                context.blendImage(sprite, width, height, px, py, null)
            }
        }
    }

    private fun loadTileset() {
        // TODO: Implement ZIP file loading and YAML parsing
        loadingState = LoadingState.LOADED
    }
}
