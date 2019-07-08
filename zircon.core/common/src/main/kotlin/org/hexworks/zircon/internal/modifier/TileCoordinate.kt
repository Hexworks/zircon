package org.hexworks.zircon.internal.modifier

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.TextureTransformModifier

data class TileCoordinate(val position: Position) : TextureTransformModifier {

    private val key = "Internal.Modifier.TileCoordinate(x=${position.x},y=${position.y})"

    override fun generateCacheKey() = key
}
