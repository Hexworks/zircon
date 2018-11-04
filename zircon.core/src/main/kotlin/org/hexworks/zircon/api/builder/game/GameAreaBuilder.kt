package org.hexworks.zircon.api.builder.game

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.game.InMemoryGameArea

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
data class GameAreaBuilder<T: Tile, B : Block<T>>(
        private var defaultBlock: Maybe<B> = Maybe.empty(),
        private var size: Size3D = Size3D.one(),
        private var layersPerBlock: Int = -1,
        private var levels: MutableMap<Int, MutableList<TileGraphics>> = mutableMapOf()) : Builder<GameArea<T, B>> {

    fun withDefaultBlock(block: B) = also {
        this.defaultBlock = Maybe.of(block)
    }

    fun withLayersPerBlock(layersPerBlock: Int) = also {
        this.layersPerBlock = layersPerBlock
    }

    fun withSize(size: Size3D) = also {
        this.size = size
        levels = mutableMapOf()
        (0 until size.zLength).forEach { z ->
            levels[z] = mutableListOf()
        }
    }

    fun withLevel(level: Int, vararg images: TileGraphics) = withLevel(level, images.toList())

    fun withLevel(level: Int, images: List<TileGraphics>) = also {
        require(level in 0.rangeTo(size.zLength)) {
            "Level '$level' is out create bounds (0 - ${size.zLength})!"
        }
        require(images.all { image -> image.size == size.to2DSize() }) {
            "The supplied image(s) do(es) not match the size create the GameArea (${size.to2DSize()})!"
        }
        this.levels[level] = images.toMutableList()
    }

    override fun build(): GameArea<T, B> {
        require(layersPerBlock > 0) {
            "There must be at least 1 layer per block."
        }
        require(defaultBlock.isPresent) {
            "No default block supplied."
        }
        return InMemoryGameArea(
                layersPerBlock = layersPerBlock,
                size = size,
                defaultBlock = defaultBlock.get())
    }

    override fun createCopy() = copy(
            levels = levels.toMutableMap())

    companion object {

        fun <T: Tile, B : Block<T>> newBuilder(): GameAreaBuilder<T, B> {
            require(RuntimeConfig.config.betaEnabled) {
                "GameArea is a beta feature. Please enable them when setting up Zircon using an AppConfig."
            }
            return GameAreaBuilder()
        }
    }
}
