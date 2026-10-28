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
     * @param atlases atlas id to the sprite sheet to sample for that id
     * @param gridLines draw the cell grid on top (DebugConfig.displayGrid)
     */
    public fun draw(canvas: Canvas, frame: FrameData, atlases: Map<Int, AtlasInfo>, gridLines: Boolean = false)

    override fun close()
}

/**
 * A sprite sheet the shader samples. [version] changes whenever [image]'s pixels change
 * (glyph caches grow), so platform wrappers of the image can be cached per version.
 */
public class AtlasInfo(
    public val image: ImageBitmap,
    public val cellWidth: Int,
    public val cellHeight: Int,
    public val version: Int = 0,
)
