package org.hexworks.zircon.api.builder.game

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.internal.game.impl.DefaultGameArea

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
data class GameAreaBuilder<T : Tile, B : Block<T>>(
        private var actualSize: Size3D = Size3D.one(),
        private var visibleSize: Size3D = Size3D.one(),
        private var blocks: MutableMap<Position3D, B> = mutableMapOf(),
        private var projectionMode: ProjectionMode = ProjectionMode.TOP_DOWN)
    : Builder<GameArea<T, B>> {

    fun withActualSize(size: Size3D) = also {
        this.actualSize = size
        blocks = blocks.filterKeys { actualSize.containsPosition(it) }
                .toMutableMap()
    }

    fun withVisibleSize(size: Size3D) = also {
        this.visibleSize = size
    }

    fun withBlock(position3D: Position3D, block: B) = also {
        if (actualSize.containsPosition(position3D)) {
            blocks[position3D] = block
        }
    }

    fun withProjectionMode(projectionMode: ProjectionMode) = also {
        this.projectionMode = projectionMode
    }

    override fun build(): GameArea<T, B> {
        return DefaultGameArea(
                initialVisibleSize = visibleSize,
                initialActualSize = actualSize,
                initialContents = blocks.toMap(),
                projectionStrategy = projectionMode.projectionStrategy)
    }

    override fun createCopy() = copy(
            blocks = blocks.toMutableMap())

    companion object {

        fun <T : Tile, B : Block<T>> newBuilder(): GameAreaBuilder<T, B> {
            return GameAreaBuilder()
        }
    }
}
