package org.codetome.zircon.api.font

import com.badlogic.gdx.graphics.g2d.TextureRegion
import java.awt.image.BufferedImage

/**
 * Represents the texture which is used to represent
 * characters by a given [Font].
 */
interface FontTextureRegion {

    fun getJava2DBackend(): BufferedImage

    fun getGdxBackend(): TextureRegion
}