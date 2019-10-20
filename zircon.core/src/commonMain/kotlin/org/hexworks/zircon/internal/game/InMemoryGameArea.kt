package org.hexworks.zircon.internal.game

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.base.BaseGameArea
import org.hexworks.zircon.platform.extension.getOrDefault

class InMemoryGameArea<T : Tile, B : Block<T>>(
        initialVisibleSize: Size3D,
        initialActualSize: Size3D,
        override val defaultBlock: B) : BaseGameArea<T, B>(
        initialVisibleSize = initialVisibleSize,
        initialActualSize = initialActualSize) {

    private var blocks: PersistentMap<Position3D, B> = persistentMapOf()

    override fun hasBlockAt(position: Position3D): Boolean {
        return blocks.containsKey(position)
    }

    override fun fetchBlockAt(position: Position3D): Maybe<B> {
        return Maybe.ofNullable(blocks[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun fetchBlockOrDefault(position: Position3D) =
            blocks.getOrDefault(position, defaultBlock)

    override fun fetchBlocks(): Map<Position3D, B> {
        return blocks
    }

    override fun setBlockAt(position: Position3D, block: B) {
        require(actualSize.containsPosition(position)) {
            "The supplied position ($position) is not within the size (${actualSize}) of this game area."
        }
        blocks = blocks.put(position, block)
    }
}
