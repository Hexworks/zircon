package org.codetome.zircon.api.game

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.Block
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.graphics.TextImage

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
     * Returns a part of this [GameArea] as a sequence of [Block]s.
     * @param offset the position where the collection of Blocks will start.
     * @param size the size of the area which you need the blocks from.
     *
     * Example: offset=(xLength=2, yLength=4, yLength=8), size=(xLength=9,yLength=4,yLength=3)
     *<pre>
     *
     *
     *
     *        ^ (yLength,yLength, positive direction)
     *        \
     *        \
     *        \
     *        \
     * (2,4,8)O---------> (xLength,xLength, positive direction)
     *       /
     *     /
     *   /
     *(yLength,yLength, positive direction)
     *
     *         ^^^--this point
     *</pre>
     */
    fun fetchBlocksAt(offset: Position3D, size: Size3D): Iterable<Block>

    /**
     * Returns the layers as a collection of [TextCharacter]s at the given [Position3D].
     * Since there can be multiple layers on the same position an [Iterable] is returned
     * instead of a single [TextCharacter].
     * Note that the returned [TextCharacter]s are ordered from bottom to top.
     */
    fun getLayersAt(position: Position3D): Iterable<TextCharacter>

    /**
     * Returns the layer as a [TextImage] at a given `level` (yLength) and `layerIndex`.
     */
    fun getLayerAt(level: Int, layerIdx: Int): TextImage

    /**
     * Returns the number of levels this [GameArea] has.
     * (eg: the `yLength` of the [GameArea])
     */
    fun getLevelCount() = getSize().zLength


    /**
     * Sets the [TextCharacter]s at the given position. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setCharactersAt(position: Position3D, characters: List<TextCharacter>)

    /**
     * Sets the [TextCharacter]s at the given position and layer. Text characters are ordered
     * as layers from bottom to top.
     */
    fun setCharacterAt(position: Position3D, layerIdx: Int, character: TextCharacter)

}
