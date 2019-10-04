package org.hexworks.zircon.api.builder.modifier

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType

/**
 * Builds [Border]s.
 * Defaults:
 * - a simple border
 * - on all sides (top, right, bottom, left)
 */
data class BorderBuilder(
        private var borderType: BorderType = BorderType.SOLID,
        private var borderColor: TileColor = TileColor.defaultForegroundColor(),
        private var borderWidth: Int = 2,
        private var borderPositions: Set<BorderPosition> = BorderPosition.values().toSet())
    : Builder<Border> {

    override fun build(): Border = Border(
            borderType = borderType,
            borderColor = borderColor,
            borderWidth = borderWidth,
            borderPositions = borderPositions)

    override fun createCopy() = copy(borderPositions = borderPositions.toSet())

    fun withBorderType(borderType: BorderType) = also {
        this.borderType = borderType
    }

    fun withBorderColor(borderColor: TileColor) = also {
        this.borderColor = borderColor
    }

    fun withBorderWidth(borderWidth: Int) = also {
        this.borderWidth = borderWidth
    }

    fun withBorderPositions(vararg borderPositions: BorderPosition) = also {
        withBorderPositions(borderPositions.toSet())
    }

    fun withBorderPositions(borderPositions: Set<BorderPosition>) = also {
        this.borderPositions = borderPositions
    }

    companion object {
        fun newBuilder() = BorderBuilder()
    }
}
