package org.hexworks.zircon.api.builder.data

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Builds [CharacterTile]s.
 * Defaults:
 * - Default character is a space
 * - Default [StyleSet] comes from [StyleSet.defaultStyle]
 */
@ZirconDsl
public class CharacterTileBuilder : Builder<CharacterTile> {

    public var character: Char = ' '
    public var styleSet: StyleSet = StyleSet.defaultStyle()

    /**
     * The tileset the tile draws with. The default, [TilesetResource.Companion.UNKNOWN], means the
     * tileset of the layer the tile is drawn on.
     */
    public var tileset: TilesetResource = TilesetResource.Companion.UNKNOWN

    override fun build(): CharacterTile {
        return CharacterTile(
            character = character,
            styleSet = styleSet,
            tileset = tileset,
        )
    }

    public operator fun Char.unaryPlus() {
        character = this
    }
}

public fun characterTile(init: CharacterTileBuilder.() -> Unit): CharacterTile {
    return CharacterTileBuilder().apply(init).build()
}

public fun CharacterTileBuilder.withStyleSet(init: StyleSetBuilder.() -> Unit) {
    this.styleSet = StyleSetBuilder().apply(init).build()
}
