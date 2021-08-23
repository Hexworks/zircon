package org.hexworks.zircon.api.resource

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.resource.TilesetType
import kotlin.jvm.JvmStatic

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
interface TilesetResource : Identifiable {

    val tileType: TileType
    val tilesetType: TilesetType
    val tilesetSourceType: TilesetSourceType

    val width: Int
    val height: Int
    val path: String

    val size: Size
        get() = Size.create(width, height)

    /**
     * A [TilesetResource] is compatible with another if they have
     * the same size.
     */
    infix fun isCompatibleWith(other: TilesetResource): Boolean {
        return if (other.isUnknown || isUnknown) true else {
            other.tileType == tileType &&
                    other.width == width &&
                    other.height == height
        }
    }

    /**
     * This function will throw an exception if [other] is not compatible with this tileset.
     */
    fun checkCompatibilityWith(other: TilesetResource) {
        require(isCompatibleWith(other)) {
            "The supplied tileset: $other is not compatible with this: $this."
        }
    }

    val isUnknown: Boolean
        get() = this === UNKNOWN

    val isNotUnknown: Boolean
        get() = isUnknown.not()

    companion object {

        /**
         * The UNKNOWN tileset resource is an implementation of the
         * [Null Object Pattern](https://en.wikipedia.org/wiki/Null_object_pattern).
         */
        object UNKNOWN : TilesetResource {
            override val tileType: TileType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val tilesetType: TilesetType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val tilesetSourceType: TilesetSourceType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val width: Int
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val height: Int
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val path: String
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val id: UUID
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")

            override fun toString() = "UNKNOWN"
        }

        @JvmStatic
        fun unknown() = UNKNOWN
    }
}
