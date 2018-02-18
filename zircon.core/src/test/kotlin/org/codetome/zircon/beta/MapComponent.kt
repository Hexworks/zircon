package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.util.darkenColorByPercent
import java.util.*


class MapComponent(mapSegment: org.codetome.zircon.beta.MapSegment, textImage: TextImage) : TextImage by textImage {

    private val data = mapSegment.data
    private val size = Size.of(mapSegment.getColumnCount(), mapSegment.getRowCount())
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
                val percent: Double = actualLayer.toDouble() / layerCount.toDouble()
                Optional.of(it
                        .withBackgroundColor(darkenColorByPercent(it.getBackgroundColor(), percent))
                        .withForegroundColor(darkenColorByPercent(it.getForegroundColor(), percent)))
            }
        }
    }


}
