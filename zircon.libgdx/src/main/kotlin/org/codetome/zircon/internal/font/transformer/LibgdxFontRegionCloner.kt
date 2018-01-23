package org.codetome.zircon.internal.font.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.LibgdxFontTextureRegion

class LibgdxFontRegionCloner : FontRegionTransformer<TextureRegion> {

    override fun transform(region: FontTextureRegion<TextureRegion>, textCharacter: TextCharacter): FontTextureRegion<TextureRegion> {
        return LibgdxFontTextureRegion(TextureRegion(region.getBackend()))
    }
}
