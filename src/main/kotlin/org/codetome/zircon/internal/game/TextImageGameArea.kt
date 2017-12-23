package org.codetome.zircon.internal.game

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.GameAreaSegment
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Beta
class TextImageGameArea(private val size: Size3D,
                        private val levels: Map<Int, List<TextImage>>) : GameArea {

    init {
        require(levels.isNotEmpty()) {
            "A game area must have at least one level!"
        }
        require(levels.map { it.value }.all { it.isNotEmpty() }) {
            "All levels of a game area must have at least 1 layer!"
        }
        val sizes: List<Size> = levels.flatMap { it.value }.map { it.getBoundableSize() }
        val first = sizes.first()
        require(sizes.map { it == first }.reduce(Boolean::and)) {
            "Game area only supports text images with equal size for now!"
        }
    }

    override fun getSize() = size

    override fun getCharactersAt(position: Position3D): List<TextCharacter> {
        require(levels.containsKey(position.z)) {
            "This game area does not have a z level of '${position.z}'!"
        }
        val first = levels[position.z]!!.first()
        val pos = position.to2DPosition()
        require(first.containsPosition(pos)) {
            "The position '$pos' is out of the bounds of the game area: '${first.getBoundableSize()}'!"
        }
        return levels[position.z]!!.map { it.getCharacterAt(position.to2DPosition()).get() }
    }

    override fun getSegmentAt(offset: Position3D, size: Size): GameAreaSegment {
        require(levels.containsKey(offset.z)) {
            "This game area does not have a z level of '${offset.z}'!"
        }
        val level = offset.z
        val first = levels[level]!!.first()
        val pos = offset.to2DPosition()
        require(first.containsPosition(pos)) {
            "The position '$pos' is out of the bounds of the game area: '${first.getBoundableSize()}'!"
        }
        return GameAreaSegment(
                level = level,
                layers = levels[level]!!.map { it.toSubImage(pos, size) })
    }

    override fun getLayerAt(level: Int, layerIdx: Int): TextImage {
        require(levels.containsKey(level)) {
            "This game area does not have a z level of '$level'!"
        }
        require(levels[level]!!.size > layerIdx) {
            "This game area does not have a layer by index '$layerIdx'."
        }
        return levels[level]!![layerIdx]
    }

    override fun setCharactersAt(position: Position3D, characters: List<TextCharacter>) {
        require(levels.containsKey(position.z)) {
            "This game area does not have a z level of '${position.z}'!"
        }
        levels[position.z]!!.forEachIndexed { layerIdx, layer ->
            layer.setCharacterAt(position.to2DPosition(), if(characters.size > layerIdx){
                characters[layerIdx]
            } else {
                TextCharacterBuilder.EMPTY
            })
        }
    }

    override fun setCharacterAt(position: Position3D, layerIdx: Int, character: TextCharacter) {
        require(levels.containsKey(position.z)) {
            "This game area does not have a z level of '${position.z}'!"
        }
        require(levels[position.z]!!.size > layerIdx) {
            "This game area does not have a layer by index '$layerIdx'."
        }
        levels[position.z]!![layerIdx].setCharacterAt(position.to2DPosition(), character)
    }

}