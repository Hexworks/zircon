package org.codetome.zircon.internal.font.transformer

import com.jhlabs.image.RaysFilter
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.api.modifier.RayShade
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion

class Java2DRayShaderTransformer : FontRegionTransformer {

    override fun transform(region: FontTextureRegion, textCharacter: TextCharacter): FontTextureRegion {
        val rayShade: RayShade = textCharacter.getModifiers().first{ it is RayShade } as RayShade
        return region.also {
            it.getJava2DBackend().let { backend ->
                backend.graphics.apply {
                    val filter = RaysFilter()
                    filter.opacity = rayShade.opacity
                    filter.threshold = rayShade.threshold
                    filter.strength = rayShade.strength
                    filter.raysOnly = rayShade.raysOnly
                    return Java2DFontTextureRegion(filter.filter(region.getJava2DBackend(), null))
                }
            }
        }
    }
}