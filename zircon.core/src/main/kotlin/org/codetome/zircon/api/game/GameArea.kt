package org.codetome.zircon.api.game

import org.codetome.zircon.api.*
import org.codetome.zircon.api.graphics.TextImage
import java.util.*

/**
 * A [GameArea] represents the 3D space in which the entities of a
 * game take place.
 */
@Beta
interface GameArea {

    /**
     * Returns the size of the 3D space this [GameArea] represents.
     */
    fun getSize(): Size3D

    /**
     * Returns **all** the [Block]s in this [GameArea].
     * Empty positions are **ignored**.
     */
    fun fetchBlocks(): Iterable<Block>

    /**
     * Returns **all** the [Block]s in this [GameArea].
     * Empty positions are filled with a filler character.
     */
    fun fetchBlocks(fetchMode: BlockFetchMode): Iterable<Block>

    /**
     * Returns a part of this [GameArea] as a sequence of [Block]s.
     * Empty positions are **ignored**.
     *
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     *
     * Example: offset=(x=2, y=4, z=8), size=(xLength=9,yLength=3,zLength=4)
     *<pre>
     *         ^ (2,4,12) (z)
     *         \
     *         \
     *         \
     *         \
     *  (2,4,8)O---------> (11,4,8) (x)
     *        /
     *      /
     *    /
     *  L (2,7,8) (y)
     *</pre>
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Block>

    /**
     * Returns a part of this [GameArea] as a sequence of [Block]s.
     *
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     * @param fetchMode the [BlockFetchMode] to use.
     *
     * Example: offset=(x=2, y=4, z=8), size=(xLength=9,yLength=3,zLength=4)
     *<pre>
     *         ^ (2,4,12) (z)
     *         \
     *         \
     *         \
     *         \
     *  (2,4,8)O---------> (11,4,8) (x)
     *        /
     *      /
     *    /
     *  L (2,7,8) (y)
     *</pre>
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D, fetchMode: BlockFetchMode): Iterable<Block>

    /**
     * Returns the [Block]s at the given `z` level.
     * Empty positions are **ignored**.
     */
    fun fetchBlocksAt(z: Int): Iterable<Block>

    /**
     * Returns the [Block]s at the given `z` level.
     */
    fun fetchBlocksAt(z: Int, blockFetchMode: BlockFetchMode): Iterable<Block>

    /**
     * Returns the [Block] at the given `position` (if any).
     */
    fun fetchBlockAt(position: Position3D): Optional<Block>

    /**
     * Returns the [TextCharacter] at the given `position` and `layerIdx` (if any).
     */
    fun fetchCharacterAt(position: Position3D, layerIdx: Int): Optional<TextCharacter>

    /**
     * Returns all the layers from bottom to top as a collection of [org.codetome.zircon.api.graphics.TextImage]s.
     * A layer is a collection of [TextCharacter]s at a given `z` level and `layerIndex`.
     */
    fun fetchLayersAt(offset: Position3D, size: Size3D) : Iterable<TextImage>

    /**
     * Sets the [TextCharacter]s at the given position. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setBlockAt(position: Position3D, characters: List<TextCharacter>)

    /**
     * Sets the [TextCharacter]s at the given position and layer. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setCharacterAt(position: Position3D, layerIdx: Int, character: TextCharacter)

    /**
     * The fetch mode for [org.codetome.zircon.api.Block]s.
     */
    enum class BlockFetchMode {

        /**
         * In this fetch mode empty blocks are not returned
         * when bulk-fetching blocks.
         */
        IGNORE_EMPTY,
        /**
         * In this fetch mode empty blocks are returned
         * with the contents set from the `filler` value.
         */
        FETCH_EMPTY
    }

}
