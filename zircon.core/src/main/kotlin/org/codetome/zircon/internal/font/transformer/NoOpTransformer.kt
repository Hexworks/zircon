package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class NoOpTransformer : FontRegionTransformer {

    override fun transform(region: FontTextureRegion, textCharacter: TextCharacter) = region
}