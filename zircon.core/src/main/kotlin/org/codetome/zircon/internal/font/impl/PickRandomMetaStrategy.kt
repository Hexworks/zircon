package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.api.util.Random
import org.codetome.zircon.internal.font.MetadataPickingStrategy

class PickRandomMetaStrategy : MetadataPickingStrategy {

    private val random = Random.create()

    override fun pickMetadata(metas: List<TextureRegionMetadata>): TextureRegionMetadata {
        return metas[random.nextInt(metas.size)]
    }
}
