package org.codetome.zircon.api.modifier

data class Border(val borderType: BorderType,
                  val borderPositions: Set<BorderPosition>) : Modifier {

    private val cacheKey = StringBuilder().apply {
        append("Border")
        append(borderType)
        append(borderPositions.joinToString(separator = "", transform = {it.name}))
    }.toString()

    override fun generateCacheKey() = cacheKey

    /**
     * Creates a new [Border] which has its border positions added to this border.
     */
    operator fun plus(other: Border): Border {
        return Border(borderType, borderPositions.plus(other.borderPositions))
    }

}
