package org.codetome.zircon.api.font

import java.awt.image.BufferedImage

/**
 * Represents the texture which is used to represent
 * characters by a given [Font].
 */
interface FontTextureRegion {

    fun getJava2DBackend(): BufferedImage
}