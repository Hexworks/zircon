package org.codetome.zircon.internal.font.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.LibgdxFontTextureRegion

class LibgdxFontRegionCloner : FontRegionTransformer<TextureRegion> {

    override fun transform(region: FontTextureRegion<TextureRegion>, tile: Tile): FontTextureRegion<TextureRegion> {
        return LibgdxFontTextureRegion(tile.generateCacheKey(),TextureRegion(region.getBackend()))
    }
}
