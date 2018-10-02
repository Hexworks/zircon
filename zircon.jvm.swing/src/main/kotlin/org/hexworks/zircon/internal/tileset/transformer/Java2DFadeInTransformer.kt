package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.FadeIn
import org.hexworks.zircon.api.tileset.TileTransformer

class Java2DFadeInTransformer : TileTransformer<FadeIn, CharacterTile> {

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile, modifier: FadeIn): CharacterTile {
        return modifier.transform(tile)
    }


}
