package org.codetome.zircon.internal.font

import org.codetome.zircon.api.font.CharacterMetadata

interface MetadataPickingStrategy {

    fun pickMetadata(metas: List<CharacterMetadata>) : CharacterMetadata
}