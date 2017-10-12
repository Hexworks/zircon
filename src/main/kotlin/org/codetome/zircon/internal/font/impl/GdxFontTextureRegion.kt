package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.font.FontTextureRegion

class GdxFontTextureRegion(private val backend: TextureRegion): FontTextureRegion {

    override fun getJava2DBackend() = TODO()

    override fun getGdxBackend() = backend
}