package org.hexworks.zircon.api.modifier

import korlibs.time.DateTime
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile

data class Delay(private val timeMs: Long = 2000) : TileTransformModifier<CharacterTile> {

    override val cacheKey: String
        get() = "Modifier.Delay.$shouldShow"

    private var shouldShow = false
    private var showAt: Long = -1

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile {
        // timer starts the first time we try to transform the tile
        if (showAt < 0) {
            showAt = DateTime.nowUnixMillisLong() + timeMs
        }
        val now = DateTime.nowUnixMillisLong()
        if (now > showAt) {
            shouldShow = true
        }
        return if (shouldShow) tile else Tile.empty()
    }
}
