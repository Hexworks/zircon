package org.hexworks.zircon.compose.tileset

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.compose.shader.AtlasInfo
import org.hexworks.zircon.internal.util.CP437Index
import org.hexworks.zircon.internal.util.CP437IndexOrNull
import org.jetbrains.compose.resources.decodeToImageBitmap

/**
 * A CP437 sprite sheet: 16 columns by 16 rows of [width] x [height] glyphs, white on
 * transparent, indexed by the character's CP437 code.
 */
public class CP437Tileset(resource: TilesetResource) : ComposeTileset(resource) {

    private var atlasId: Int = FrameData.NO_ATLAS

    override suspend fun load() {
        if (loaded) return
        atlasId = putAtlas(AtlasInfo(resource.loadBytes().decodeToImageBitmap(), width, height))
        loaded = true
    }

    override fun drawTile(tile: Tile, context: FrameData, position: Position) {
        val characterTile = tile as? CharacterTile ?: return
        if (!loaded) {
            context.writeBackground(position, characterTile.backgroundColor)
            return
        }
        // A character outside Code Page 437 shows as '?' rather than taking the frame down.
        val index = characterTile.character.CP437IndexOrNull() ?: REPLACEMENT_INDEX
        context.writeGlyph(
            position = position,
            atlasId = atlasId,
            slotX = index % 16,
            slotY = index / 16,
            foreground = characterTile.foregroundColor,
            background = characterTile.backgroundColor,
        )
    }
}

private val REPLACEMENT_INDEX = '?'.CP437Index()
