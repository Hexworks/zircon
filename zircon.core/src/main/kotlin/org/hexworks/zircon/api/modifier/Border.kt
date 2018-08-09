package org.hexworks.zircon.api.modifier

data class Border(val borderType: BorderType,
                  val borderPositions: Set<BorderPosition>) : Modifier {

    /**
     * Creates a new [Border] which has its border positions added to this border.
     */
    operator fun plus(other: Border): Border {
        return Border(borderType, borderPositions.plus(other.borderPositions))
    }

    override fun generateCacheKey(): String {
        return "Border(t=${borderType.name},bp=[" + borderPositions.joinToString(separator = ",") { it.name } + "])"
    }

}
