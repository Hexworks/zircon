package org.hexworks.zircon.terminal

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import kotlin.reflect.KClass

/**
 * The one tileset of the terminal renderer: every [CharacterTile] becomes exactly one cell
 * holding its code point, whatever font the terminal uses draws it. The pixel size of the
 * [TilesetResource] is carried along but means nothing here.
 *
 * Transparent colours keep what is below, like the Compose renderer: an opaque background
 * replaces the cell, a glyph on a transparent background only replaces the character and
 * its colour.
 */
public class TextTileset(private val resource: TilesetResource) : Tileset<CellBuffer> {

    override val id: UUID get() = resource.id
    override val targetType: KClass<CellBuffer> = CellBuffer::class
    override val width: Int get() = resource.width
    override val height: Int get() = resource.height

    override fun drawTile(tile: Tile, context: CellBuffer, position: Position) {
        val characterTile = tile as? CharacterTile
            ?: error("The terminal renderer draws text only; ${tile.tileType} tiles are not supported (${tile.cacheKey})")
        val index = context.index(position.x, position.y)
        val background = characterTile.backgroundColor
        val foreground = characterTile.foregroundColor
        if (!background.isTransparent) {
            context.backgrounds[index] = CellBuffer.pack(background)
            context.codes[index] = cellCodeOf(characterTile.character)
            context.foregrounds[index] = CellBuffer.pack(foreground)
        } else if (!foreground.isTransparent && characterTile.character != ' ') {
            context.codes[index] = cellCodeOf(characterTile.character)
            context.foregrounds[index] = CellBuffer.pack(foreground)
        }
    }

    public companion object {
        /**
         * The code point written for [character]: control characters become a space, and
         * characters a terminal would draw wider or narrower than one cell become U+FFFD
         * so the grid never drifts (see [TerminalCharacters]).
         */
        public fun cellCodeOf(character: Char): Int {
            val code = character.code
            return when {
                code < 0x20 || code in 0x7F..0x9F -> CellBuffer.SPACE
                character.isSurrogate() -> TerminalCharacters.REPLACEMENT
                !TerminalCharacters.isSingleWidth(code) -> TerminalCharacters.REPLACEMENT
                else -> code
            }
        }
    }
}

/**
 * Hands out [TextTileset]s. Any tileset of character tiles (CP437, TrueType, custom) renders
 * as text; a graphical tileset cannot be drawn in a terminal and fails here.
 */
public class TextTilesetLoader : TilesetLoader<CellBuffer> {

    private val byId = HashMap<UUID, TextTileset>()
    private var lastResource: TilesetResource? = null
    private var lastTileset: TextTileset? = null

    override fun canLoadTileset(resource: TilesetResource): Boolean =
        resource.tileType == org.hexworks.zircon.api.data.TileType.CHARACTER_TILE

    override fun loadTilesetFrom(resource: TilesetResource): Tileset<CellBuffer> {
        if (resource === lastResource) return lastTileset!!
        require(canLoadTileset(resource)) {
            "The terminal renderer draws text only; the ${resource.tileType} tileset '${resource.path}' cannot be used"
        }
        val tileset = byId.getOrPut(resource.id) { TextTileset(resource) }
        lastResource = resource
        lastTileset = tileset
        return tileset
    }
}
