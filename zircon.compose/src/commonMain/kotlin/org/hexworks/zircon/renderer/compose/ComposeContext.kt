package org.hexworks.zircon.renderer.compose

/**
 * Render context for Compose-based tile rendering.
 * This is the context type parameter for all Compose tilesets.
 *
 * The context provides access to the pixel buffer for rendering.
 * Each tileset is responsible for managing its own glyph cache or sprite data.
 */
expect class ComposeContext {
    /**
     * The width of tiles in pixels.
     */
    val tileWidth: Int

    /**
     * The height of tiles in pixels.
     */
    val tileHeight: Int

    /**
     * Fills a rectangular region with a solid color.
     * @param x The x position in pixels
     * @param y The y position in pixels
     * @param w The width in pixels
     * @param h The height in pixels
     * @param argb The color in ARGB format
     */
    fun fillRect(x: Int, y: Int, w: Int, h: Int, argb: Int)

    /**
     * Blends image data onto the buffer.
     * @param imageData The image data in BGRA format
     * @param imageWidth The width of the image
     * @param imageHeight The height of the image
     * @param destX The destination x position in pixels
     * @param destY The destination y position in pixels
     * @param tintArgb Optional tint color in ARGB format
     */
    fun blendImage(
        imageData: ByteArray,
        imageWidth: Int,
        imageHeight: Int,
        destX: Int,
        destY: Int,
        tintArgb: Int? = null
    )
}