package org.hexworks.zircon.compose.tileset

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.internal.util.CP437Index
import org.jetbrains.compose.resources.decodeToImageBitmap

/**
 * A CP437 sprite sheet: 16 columns by 16 rows of [width] x [height] glyphs, white on
 * transparent, indexed by the character's CP437 code.
 */
public class CP437Tileset(resource: TilesetResource) : ComposeTileset(resource) {

    override suspend fun load() {
        if (atlas != null) return
        atlas = resource.loadBytes().decodeToImageBitmap()
    }

    override fun drawTile(tile: Tile, context: FrameData, position: Position) {
        val characterTile = tile as? CharacterTile ?: return
        if (atlas == null) {
            context.writeBackground(position, characterTile.backgroundColor)
            return
        }
        val index = characterTile.character.CP437Index()
        context.write(
            position = position,
            atlasId = atlasId,
            slotX = index % 16,
            slotY = index / 16,
            foreground = characterTile.foregroundColor,
            background = characterTile.backgroundColor,
        )
    }
}
