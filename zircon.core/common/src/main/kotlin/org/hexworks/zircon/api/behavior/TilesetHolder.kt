package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Represents an object which has a [TilesetResource].
 */
interface TilesetHolder {

    val tileset: TilesetResource
}
