package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.modifier.BorderType
import org.hexworks.zircon.api.modifier.BorderType.*
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.image.BufferedImage


class Java2DBorderTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            val txt = it.texture()
            txt.graphics.apply {
                color = tile.foregroundColor.toAWTColor()
                if (tile.hasBorder()) {
                    tile.fetchBorderData().forEach { border ->
                        border.borderPositions.forEach { pos ->
                            txt.width
                            FILLER_LOOKUP[pos]?.invoke(txt, this as Graphics2D, border.borderType)
                        }
                    }
                }
                dispose()
            }
        }
    }

    companion object {

        private val BORDER_TYPE_LOOKUP = mapOf(
                Pair(SOLID, this::drawSolidLine),
                Pair(DOTTED, this::drawDottedLine),
                Pair(DASHED, this::drawDashedLine)
        ).toMap()

        private val FILLER_LOOKUP = mapOf(
                Pair(TOP, { region: BufferedImage, graphics: Graphics2D, borderType: BorderType ->
                    BORDER_TYPE_LOOKUP[borderType]?.invoke(graphics, 0, 1, region.width, 1)
                }),
                Pair(BOTTOM, { region: BufferedImage, graphics: Graphics2D, borderType: BorderType ->
                    BORDER_TYPE_LOOKUP[borderType]?.invoke(graphics, 0, region.height - 1, region.width, region.height - 1)
                }),
                Pair(LEFT, { region: BufferedImage, graphics: Graphics2D, borderType: BorderType ->
                    BORDER_TYPE_LOOKUP[borderType]?.invoke(graphics, 1, 0, 1, region.height)
                }),
                Pair(RIGHT, { region: BufferedImage, graphics: Graphics2D, borderType: BorderType ->
                    BORDER_TYPE_LOOKUP[borderType]?.invoke(graphics, region.width - 1, 0, region.width - 1, region.height)
                }))
                .toMap()

        private fun drawDottedLine(graphics: Graphics2D,
                                   x1: Int,
                                   y1: Int,
                                   x2: Int,
                                   y2: Int) {
            val dotted = BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(2f), 0f)
            graphics.stroke = dotted
            graphics.drawLine(x1, y1, x2, y2)
        }

        private fun drawSolidLine(graphics: Graphics2D,
                                  x1: Int,
                                  y1: Int,
                                  x2: Int,
                                  y2: Int) {
            val solid = BasicStroke(2f)
            graphics.stroke = solid
            graphics.drawLine(x1, y1, x2, y2)
        }

        private fun drawDashedLine(graphics: Graphics2D,
                                   x1: Int,
                                   y1: Int,
                                   x2: Int,
                                   y2: Int) {
            val dashed = BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(9f), 0f)
            graphics.stroke = dashed
            graphics.drawLine(x1, y1, x2, y2)
        }
    }
}
