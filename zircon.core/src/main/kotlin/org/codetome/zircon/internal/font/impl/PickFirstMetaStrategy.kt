package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.TextureRegionMetadata
import org.codetome.zircon.internal.font.MetadataPickingStrategy

class PickFirstMetaStrategy : MetadataPickingStrategy {

    override fun pickMetadata(metas: List<TextureRegionMetadata>): TextureRegionMetadata {
        return metas.first()
    }
}
