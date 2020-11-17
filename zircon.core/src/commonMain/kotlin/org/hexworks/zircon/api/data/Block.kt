@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.Beta
import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.builder.data.BlockBuilder
import kotlin.jvm.JvmStatic

/**
 * A [Block] is a voxel that consists of [Tile]s representing each side, and the internal
 * [content] of the [Block]. All sides of the [Block] are optional. If any of them are missing
 * the [emptyTile] will be used when the sides are dereferenced.
 * If you want to have multiple [Tile]s in any side (or as the [content]) try using a [StackedTile].
 */
@Beta
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Block<T : Tile> : Copiable<Block<T>> {

    var content: T

    var top: T

    var bottom: T

    var front: T

    var back: T

    var left: T

    var right: T

    val emptyTile: T

    val tiles: Map<BlockTileType, T>

    operator fun component1() = content
    operator fun component2() = top
    operator fun component3() = bottom
    operator fun component4() = front
    operator fun component5() = back
    operator fun component6() = left
    operator fun component7() = right

    /**
     * Tells whether this [Block] is empty (all of its sides and content are
     * the the [emptyTile]).
     */
    fun isEmpty(): Boolean

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *x* axis.
     */
    fun withFlippedAroundX(): Block<T>

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *y* axis.
     */
    fun withFlippedAroundY(): Block<T>

    /**
     * Returns a new [Block] which is a rotation of this [Block]
     * around the *z* axis.
     */
    fun withFlippedAroundZ(): Block<T>

    /**
     * Returns the tile from this [Block] for the given [blockTileType].
     */
    fun getTileByType(blockTileType: BlockTileType): T

    /**
     * Creates a new [BlockBuilder] preconfigured with the contents of
     * this [Block].
     */
    fun toBuilder(): BlockBuilder<T>


    companion object {

        @JvmStatic
        fun <T : Tile> newBuilder() = BlockBuilder<T>()

        @JvmStatic
        fun <T : Tile> create(emptyTile: T): Block<T> {
            return BlockBuilder.newBuilder<T>()
                    .withEmptyTile(emptyTile)
                    .build()
        }
    }
}
