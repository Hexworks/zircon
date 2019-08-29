package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.api.modifier.BorderType
import org.hexworks.zircon.api.modifier.BorderType.*
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import java.awt.BasicStroke
import java.awt.Graphics2D
import java.awt.image.BufferedImage


class Java2DBorderTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            val txt = it.texture
            txt.graphics.apply {
                if (tile.hasBorder) {
                    tile.fetchBorderData().forEach { border ->
                        color = border.borderColor.toAWTColor()
                        border.borderPositions.forEach { pos ->
                            txt.width
                            FILLER_LOOKUP[pos]?.invoke(txt, this as Graphics2D, border.borderType, border.borderWidth)
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
                Pair(TOP, { region: BufferedImage, graphics: Graphics2D, type: BorderType, width: Int ->
                    BORDER_TYPE_LOOKUP[type]?.invoke(graphics, width,0, 0, region.width, 0)
                }),
                Pair(BOTTOM, { region: BufferedImage, graphics: Graphics2D, type: BorderType, width: Int  ->
                    BORDER_TYPE_LOOKUP[type]?.invoke(graphics, width, 0, region.height, region.width, region.height)
                }),
                Pair(LEFT, { region: BufferedImage, graphics: Graphics2D, type: BorderType, width: Int  ->
                    BORDER_TYPE_LOOKUP[type]?.invoke(graphics, width, 0, 0, 0, region.height)
                }),
                Pair(RIGHT, { region: BufferedImage, graphics: Graphics2D, type: BorderType, width: Int  ->
                    BORDER_TYPE_LOOKUP[type]?.invoke(graphics, width, region.width, 0, region.width, region.height)
                }))
                .toMap()

        private fun drawDottedLine(graphics: Graphics2D,
                                   width: Int,
                                   x1: Int,
                                   y1: Int,
                                   x2: Int,
                                   y2: Int) {
            val dotted = BasicStroke(width.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(2f), 0f)
            graphics.stroke = dotted
            graphics.drawLine(x1, y1, x2, y2)
        }

        private fun drawSolidLine(graphics: Graphics2D,
                                  width: Int,
                                  x1: Int,
                                  y1: Int,
                                  x2: Int,
                                  y2: Int) {
            val solid = BasicStroke(width.toFloat())
            graphics.stroke = solid
            graphics.drawLine(x1, y1, x2, y2)
        }

        private fun drawDashedLine(graphics: Graphics2D,
                                   width: Int,
                                   x1: Int,
                                   y1: Int,
                                   x2: Int,
                                   y2: Int) {
            val dashed = BasicStroke(width.toFloat(), BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, floatArrayOf(9f), 0f)
            graphics.stroke = dashed
            graphics.drawLine(x1, y1, x2, y2)
        }
    }
}
