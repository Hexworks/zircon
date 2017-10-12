package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.TextImage
import java.util.*


class MapComponent(mapSegment: MapSegment, textImage: TextImage) : TextImage by textImage {

    private val data = mapSegment.data
    private val size = Size(mapSegment.getColumnCount(), mapSegment.getRowCount())
    private val layerCount = Math.min(mapSegment.getLayerCount(), 5)

    override fun getBoundableSize() = size

    override fun getCharacterAt(position: Position): Optional<TextCharacter> {
        val (col, row) = position
        val layersWithContent = data
                .map { it[row][col] }
                .take(layerCount)
                .dropWhile { it.getCharacter() == TextCharacterBuilder.DEFAULT_CHARACTER.getCharacter() }
        val actualLayer = layerCount - layersWithContent.size
        return if (layersWithContent.isEmpty()) {
            Optional.empty()
        } else {
            layersWithContent.first().let {
                val percent: Float = actualLayer.toFloat() / layerCount.toFloat()
                Optional.of(it
                        .withBackgroundColor(darkenColorByPercent(it.getBackgroundColor(), percent))
                        .withForegroundColor(darkenColorByPercent(it.getForegroundColor(), percent)))
            }
        }
    }

    private fun darkenColorByPercent(color: TextColor, percentage: Float): TextColor {
        return TextColorFactory.fromRGB(
                red = (color.getRed() * (1f - percentage)).toInt(),
                green = (color.getGreen() * (1f - percentage)).toInt(),
                blue = (color.getBlue() * (1f - percentage)).toInt())
    }


}