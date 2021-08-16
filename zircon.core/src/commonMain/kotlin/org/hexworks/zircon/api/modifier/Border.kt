package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.color.TileColor
import kotlin.jvm.JvmStatic

data class Border internal constructor(
    val borderType: BorderType,
    val borderColor: TileColor,
    val borderWidth: Int,
    val borderPositions: Set<BorderPosition>
) : TextureTransformModifier {

    override val cacheKey: String
        get() = "Modifier.Border(t=${borderType.name},bp=[" + borderPositions.joinToString(separator = ",") { it.name } + "])"

    /**
     * Creates a new [Border] which has its border positions added to this border.
     */
    operator fun plus(other: Border): Border {
        return copy(borderPositions = borderPositions.plus(other.borderPositions))
    }

    companion object {

        @JvmStatic
        fun newBuilder() = BorderBuilder.newBuilder()
    }

}
