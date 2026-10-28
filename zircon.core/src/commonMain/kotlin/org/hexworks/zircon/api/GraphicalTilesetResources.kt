package org.hexworks.zircon.api

import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.BuiltInGraphicalTilesetResource
import org.hexworks.zircon.internal.resource.GraphicalTilesetResource

/**
 * This object can be used to load either built-in graphical
 * [TilesetResource]s or external ones.
 */
public object GraphicalTilesetResources {

    public fun nethack16x16(): TilesetResource = BuiltInGraphicalTilesetResource.NETHACK_16X16

}
