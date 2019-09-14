package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.color.TileColor

data class Border(val borderType: BorderType,
                  val borderColor: TileColor,
                  val borderWidth: Int,
                  val borderPositions: Set<BorderPosition>) : TextureTransformModifier {

    /**
     * Creates a new [Border] which has its border positions added to this border.
     */
    operator fun plus(other: Border): Border {
        return copy(borderPositions = borderPositions.plus(other.borderPositions))
    }

    override fun generateCacheKey(): String {
        return "Modifier.Border(t=${borderType.name},bp=[" + borderPositions.joinToString(separator = ",") { it.name } + "])"
    }

}
