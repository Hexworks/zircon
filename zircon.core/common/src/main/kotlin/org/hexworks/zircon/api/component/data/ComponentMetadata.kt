package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Contains metadata which is common to **all**
 * [org.hexworks.zircon.api.component.Component]s
 */
data class ComponentMetadata(val position: Position,
                             val size: Size,
                             val tileset: TilesetResource,
                             val componentStyleSet: ComponentStyleSet) {

    init {
        require(position.hasNegativeComponent().not()) {
            "Can't have a Component with a position ($position) which has a " +
                    "negative component (x or y)."
        }
    }
}
