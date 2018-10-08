package org.hexworks.zircon.api.builder.game

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.InMemoryGameArea

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
data class GameAreaBuilder(private var size: Size3D = Size3D.one(),
                           private var layersPerBlock: Int = -1,
                           private var levels: MutableMap<Int, MutableList<TileGraphics>> = mutableMapOf()) : Builder<GameArea> {

    fun layersPerBlock(layersPerBlock: Int) = also {
        this.layersPerBlock = layersPerBlock
    }

    fun size(size: Size3D) = also {
        this.size = size
        levels = mutableMapOf()
        (0 until size.zLength).forEach { z ->
            levels[z] = mutableListOf()
        }
    }

    fun setLevel(level: Int, vararg images: TileGraphics) = setLevel(level, images.toList())

    fun setLevel(level: Int, images: List<TileGraphics>) = also {
        require(level in 0.rangeTo(size.zLength)) {
            "Level '$level' is out create bounds (0 - ${size.zLength})!"
        }
        require(images.all { image -> image.size == size.to2DSize() }) {
            "The supplied image(s) do(es) not match the size create the GameArea (${size.to2DSize()})!"
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

        fun newBuilder(): GameAreaBuilder {
            require(RuntimeConfig.config.betaEnabled) {
                "GameArea is a beta feature. Please enable them when setting up Zircon using an AppConfig."
            }
            return GameAreaBuilder()
        }
    }
}
