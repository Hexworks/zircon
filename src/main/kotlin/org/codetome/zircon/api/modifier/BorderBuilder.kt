package org.codetome.zircon.api.modifier

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.builder.Builder

/**
 * Builds [Border]s.
 * Defaults:
 * - a simple border
 * - on all sides (top, right, bottom, left)
 */
data class BorderBuilder(
        private var borderType: BorderType = BorderType.SOLID,
        private var borderPositions: Set<BorderPosition> = BorderPosition.values().toSet())
    : Builder<Modifier> {

    override fun build(): Border = Border(
            borderType = borderType,
            borderPositions = borderPositions)

    override fun createCopy() = copy(borderPositions = borderPositions.toSet())

    fun borderType(borderType: BorderType) = also {
        this.borderType = borderType
    }

    fun borderPositions(vararg borderPositions: BorderPosition) = also {
        borderPositions(borderPositions.toSet())
    }

    fun borderPositions(borderPositions: Set<BorderPosition>) = also {
        this.borderPositions = borderPositions
    }

    companion object {

        /**
         * Creates a new [BorderBuilder] for creating [Border]s.
         */
        @JvmStatic
        fun newBuilder() = BorderBuilder()

        /**
         * Shorthand for the default border which is:
         * - a simple border
         * - on all sides (top, right, bottom, left)
         */
        @JvmField
        val DEFAULT_BORDER = BorderBuilder.newBuilder().build()
    }
}