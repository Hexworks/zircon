package org.codetome.zircon.internal.font

import org.codetome.zircon.api.font.TextureRegionMetadata

interface MetadataPickingStrategy {

    fun pickMetadata(metas: List<TextureRegionMetadata>) : TextureRegionMetadata
}
