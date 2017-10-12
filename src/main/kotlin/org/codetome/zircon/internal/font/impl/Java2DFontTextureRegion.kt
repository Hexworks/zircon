package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.FontTextureRegion
import java.awt.image.BufferedImage

class Java2DFontTextureRegion(private val backend: BufferedImage): FontTextureRegion {

    override fun getJava2DBackend() = backend
}