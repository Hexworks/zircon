package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.resource.TilesetResource.Companion.UNKNOWN
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.behavior.Identifiable
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType

/**
 * Contains metadata about a tileset for a given [Tile] type.
 * It is used by Zircon internally to be able to tell whether the currently chosen tileset
 * is compatible with another and to determine the dimensions of the tileset.
 */
public interface TilesetResource : Identifiable, Resource {

    public val tileType: TileType
    public val tilesetType: TilesetType

    public val width: Int
    public val height: Int

    public val size: Size
        get() = Size(width, height)

    /**
     * A [TilesetResource] is compatible with another if they have
     * the same size.
     */
    public infix fun isCompatibleWith(other: TilesetResource): Boolean {
        return if (other.isUnknown || isUnknown) true else {
            other.tileType == tileType &&
                    other.width == width &&
                    other.height == height
        }
    }

    /**
     * This function will throw an exception if [other] is not compatible with this tileset.
     */
    public fun checkCompatibilityWith(other: TilesetResource) {
        require(isCompatibleWith(other)) {
            "The supplied tileset: $other is not compatible with this: $this."
        }
    }

    public val isUnknown: Boolean
        get() = this === UNKNOWN

    public val isNotUnknown: Boolean
        get() = isUnknown.not()

    public companion object {

        /**
         * The UNKNOWN tileset resource is an implementation of the
         * [Null Object Pattern](https://en.wikipedia.org/wiki/Null_object_pattern).
         */
        public object UNKNOWN : TilesetResource {
            override val tileType: TileType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val tilesetType: TilesetType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val resourceType: ResourceType
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val width: Int
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val height: Int
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val path: String
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")
            override val id: UUID
                get() = error("This is the UNKNOWN TilesetResource, implementing the null object pattern. Don't use it as a tileset")

            override fun toString(): String = "UNKNOWN"
        }

        public fun unknown(): UNKNOWN = UNKNOWN
    }
}
