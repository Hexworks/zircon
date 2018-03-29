package org.codetome.zircon.internal.font.impl

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.font.FontTextureRegion

class LibgdxFontTextureRegion(private val backend: TextureRegion): FontTextureRegion<TextureRegion> {

    override fun getBackend() = backend
}
