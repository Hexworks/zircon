package org.hexworks.zircon.api.component.data

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig

/**
 * Contains metadata that is common to **all** [Component]s.
 */
data class ComponentMetadata(
        val relativePosition: Position,
        val size: Size,
        val tileset: TilesetResource,
        val componentStyleSet: ComponentStyleSet,
        val theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
) {

    init {
        require(relativePosition.hasNegativeComponent.not()) {
            "Can't have a Component with a relative position ($relativePosition) which has a " +
                    "negative dimension."
        }
    }
}
