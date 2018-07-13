package org.codetome.zircon.api.game.builder

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.game.InMemoryGameArea

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameAreaBuilder(private var size: Size3D = Size3D.ONE,
                           private var layersPerBlock: Int = -1,
                           private var levels: MutableMap<Int, MutableList<TextImage>> = mutableMapOf()) : Builder<GameArea> {

    fun layersPerBlock(layersPerBlock: Int) = also {
        this.layersPerBlock = layersPerBlock
    }

    fun size(size: Size3D) = also {
        this.size = size
        levels = mutableMapOf()
        (0 until size.zLength).forEach {
            levels[it] = mutableListOf()
        }
    }

    fun setLevel(level: Int, vararg images: TextImage) = setLevel(level, images.toList())

    fun setLevel(level: Int, images: List<TextImage>) = also {
        require(level in 0.rangeTo(size.zLength)) {
            "Level '$level' is out of bounds (0 - ${size.zLength})!"
        }
        require(images.all { it.getBoundableSize() == size.to2DSize() }) {
            "The supplied image(s) do(es) not match the size of the GameArea (${size.to2DSize()})!"
        }
        this.levels[level] = images.toMutableList()
    }

    override fun build(): GameArea {
        require(layersPerBlock > 0) {
            "There must be at least 1 layer per block."
        }
        return InMemoryGameArea(
                layersPerBlock = layersPerBlock,
                size = size)
    }

    override fun createCopy() = copy(
            levels = levels.toMutableMap())

    companion object {

        fun newBuilder() = GameAreaBuilder()
    }
}
