package org.hexworks.zircon.compose.tileset

import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.api.tileset.loadGraphicalTileset
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.compose.shader.AtlasInfo
import org.hexworks.zircon.internal.util.reportErrorOnce
import org.jetbrains.compose.resources.decodeToImageBitmap

/**
 * A graphical tileset archive (`tileset.json` plus sprite sheets): every sheet becomes an
 * atlas, and tiles are looked up by name.
 */
public class GraphicalTileset(resource: TilesetResource) : ComposeTileset(resource) {

    private class Sprite(val atlasId: Int, val slotX: Int, val slotY: Int)

    private val sprites = HashMap<String, Sprite>()

    override suspend fun load() {
        if (loaded) return
        val data = loadGraphicalTileset(resource.loadBytes())
        val size = data.metadata.tileSize
        val atlasIds = data.metadata.files.associate { file ->
            file.name to putAtlas(AtlasInfo(data.imageBytes(file.name).decodeToImageBitmap(), size, size))
        }
        for (name in data.tileNames) {
            val texture = data.textureFor(name) ?: continue
            sprites[name] = Sprite(atlasIds.getValue(texture.file), texture.x, texture.y)
        }
        loaded = true
    }

    override fun drawTile(tile: Tile, context: FrameData, position: Position) {
        val graphicalTile = tile as? GraphicalTile ?: return
        if (!loaded) return
        val sprite = sprites[graphicalTile.name]
        if (sprite == null) {
            // Unknown sprite name: a marked cell, reported once per name.
            reportErrorOnce(
                key = "GraphicalTileset:${resource.path}:${graphicalTile.name}",
                source = "Tile at $position",
                error = IllegalArgumentException("No tile named '${graphicalTile.name}' in the graphical tileset '${resource.path}'"),
            )
            context.writeBackground(position, PlaceholderTileset.MISSING_TILE_COLOR)
            return
        }
        context.writeSprite(position, sprite.atlasId, sprite.slotX, sprite.slotY)
    }
}
