package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.internal.font.MetadataPickingStrategy

class PickFirstMetaStrategy : MetadataPickingStrategy {

    override fun pickMetadata(metas: List<CharacterMetadata>): CharacterMetadata {
        return metas.first()
    }
}
