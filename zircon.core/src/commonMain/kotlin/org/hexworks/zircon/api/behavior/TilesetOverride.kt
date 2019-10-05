package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Interface which adds functionality for overriding tilesets used
 * in its implementors (components, layers, etc).
 */
interface TilesetOverride : TilesetHolder {

    override var tileset: TilesetResource
}
