package org.hexworks.zircon.compose.shader

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import org.hexworks.zircon.compose.FrameData

/**
 * Draws a [FrameData] with the platform's runtime shader: one pass for backgrounds, then
 * one pass per atlas that the frame uses. [tileWidth] and [tileHeight] are the grid cell
 * size in pixels.
 */
public expect class TileShader(tileWidth: Int, tileHeight: Int) : AutoCloseable {

    /**
     * Draws the whole frame at the canvas origin.
     *
     * @param atlases atlas id to (image, atlas cell width, atlas cell height)
     */
    public fun draw(canvas: Canvas, frame: FrameData, atlases: Map<Int, AtlasInfo>)

    override fun close()
}

public class AtlasInfo(
    public val image: ImageBitmap,
    public val cellWidth: Int,
    public val cellHeight: Int,
)
