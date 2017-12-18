package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Beta
import org.codetome.zircon.api.game.GameArea
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.game.TextImageGameArea

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Beta
data class GameAreaBuilder(private var size: Size3D = Size3D.ONE,
                           private var levels: MutableMap<Int, List<TextImage>> = mutableMapOf()) : Builder<GameArea> {


    fun size(size: Size3D) = also {
        this.size = size
        levels = mutableMapOf()
        (0 until size.height).forEach {
            levels[it] = listOf()
        }
    }

    fun setLevel(level: Int, vararg images: TextImage) = setLevel(level, images.toList())

    fun setLevel(level: Int, images: List<TextImage>) = also {
        require(level in 0.rangeTo(size.height)) {
            "Level '$level' is out of bounds (0 - ${size.height})!"
        }
        require(images.all { it.getBoundableSize() == size.to2DSize() }) {
            "The supplied image(s) do(es) not match the size of the GameArea (${size.to2DSize()})!"
        }
        this.levels[level] = images
    }

    override fun build(): GameArea {
        return TextImageGameArea(
                size = size,
                levels = levels)
    }

    override fun createCopy() = copy(
            levels = levels.toMutableMap())

    companion object {

        @JvmStatic
        fun newBuilder() = GameAreaBuilder()
    }
}