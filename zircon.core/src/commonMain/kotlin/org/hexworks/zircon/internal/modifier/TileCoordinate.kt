package org.hexworks.zircon.internal.modifier

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.TextureModifier

data class TileCoordinate(val position: Position) : TextureModifier {

    override val cacheKey: String
        get() = "Internal.Modifier.TileCoordinate(x=${position.x},y=${position.y})"
}
