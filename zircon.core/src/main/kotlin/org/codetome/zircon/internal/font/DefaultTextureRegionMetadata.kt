package org.codetome.zircon.internal.font

import org.codetome.zircon.api.font.TextureRegionMetadata

data class DefaultTextureRegionMetadata(override val char: Char,
                                        override val tags: Set<String> = setOf(),
                                        override val x: Int,
                                        override val y: Int) : TextureRegionMetadata
