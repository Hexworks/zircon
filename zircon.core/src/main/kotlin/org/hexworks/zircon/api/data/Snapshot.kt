package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.resource.TilesetResource

interface Snapshot {

    fun cells(): Iterable<Cell>

    fun tileset(): TilesetResource
}
