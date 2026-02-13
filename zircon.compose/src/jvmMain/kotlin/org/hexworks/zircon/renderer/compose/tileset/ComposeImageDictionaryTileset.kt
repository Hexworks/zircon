package org.hexworks.zircon.renderer.compose.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType.IMAGE_TILE
import org.hexworks.zircon.api.data.tile.ImageTile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.renderer.compose.ComposeContext
import kotlin.reflect.KClass

/**
 * Image dictionary tileset implementation for Compose.
 *
 * Loads tiles from a directory of PNG images where each image
 * file name (without extension) maps to a tile name.
 *
 * Note: Full implementation requires directory scanning and image loading.
 * This is a placeholder that provides the basic structure.
 */
class ComposeImageDictionaryTileset(
    private val resource: TilesetResource
) : Tileset<ComposeContext> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int get() = resource.width
    override val height: Int get() = resource.height
    override val targetType: KClass<ComposeContext> = ComposeContext::class

    private var loadingState = LoadingState.NOT_LOADED
    private var tileLookup = mutableMapOf<String, ByteArray>()

    init {
        require(resource.tileType == IMAGE_TILE) {
            "Image dictionary tilesets only support ${IMAGE_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: ComposeContext, position: Position) {
        require(tile is ImageTile) {
            "An image dictionary renderer can only render ${ImageTile::class.simpleName}s. Offending tile: $tile."
        }

        if (loadingState == LoadingState.NOT_LOADED) {
            loadingState = LoadingState.LOADING
            loadTileset()
        }

        if (loadingState == LoadingState.LOADED) {
            val px = position.x * width
            val py = position.y * height

            // Draw transparent background
            context.fillRect(px, py, width, height, 0x00000000)

            // Draw the tile image if available
            val imageData = tileLookup[tile.name]
            if (imageData != null) {
                context.blendImage(imageData, width, height, px, py, null)
            }
        }
    }

    private fun loadTileset() {
        // TODO: Implement directory scanning and image loading
        loadingState = LoadingState.LOADED
    }
}
