package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Interface which adds functionality for overriding tilesets used
 * in its implementors (components, layers, etc).
 */
interface TilesetOverride : TilesetHolder {

    /**
     * The (mutable) tileset value.
     */
    override var tileset: TilesetResource

    /**
     * A [Property] that wraps the [tileset] and offers data binding and
     * observability features.
     *
     * @see Property
     */
    val tilesetProperty: Property<TilesetResource>

}
