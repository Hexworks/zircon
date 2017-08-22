package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class NoOpTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter) = region
}