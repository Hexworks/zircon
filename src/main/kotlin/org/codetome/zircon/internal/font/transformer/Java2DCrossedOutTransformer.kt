package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DCrossedOutTransformer : FontRegionTransformer {

    override fun transform(region: FontTextureRegion, textCharacter: TextCharacter): FontTextureRegion {
        return region.also {
            it.getJava2DBackend().let { backend ->
                backend.graphics.apply {
                    color = textCharacter.getForegroundColor().toAWTColor()
                    fillRect(0, backend.height / 2, backend.width, 2)
                    dispose()
                }
            }
        }
    }
}