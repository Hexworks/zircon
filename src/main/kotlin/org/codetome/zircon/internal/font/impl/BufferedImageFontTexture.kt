package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.FontTexture
import java.awt.image.BufferedImage

class BufferedImageFontTexture(private val backend: BufferedImage): FontTexture {
}