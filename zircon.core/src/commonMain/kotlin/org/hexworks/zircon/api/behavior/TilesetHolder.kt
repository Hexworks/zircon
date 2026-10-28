package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Represents an object that has a tileset ([TilesetResource]).
 */
public interface TilesetHolder {

    public val tileset: TilesetResource
}
