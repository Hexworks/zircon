package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.font.FontTextureRegion
import java.awt.image.BufferedImage
import java.util.*

object FontSettings {
    val NO_FONT = object : Font {

        private val id = UUID.randomUUID()

        override fun getId() = id

        override fun getWidth(): Int {
            TODO()
        }

        override fun getHeight(): Int {
            TODO()
        }

        override fun hasDataForChar(char: Char): Boolean {
            TODO()
        }

        override fun fetchRegionForChar(textCharacter: TextCharacter): FontTextureRegion {
            TODO()
        }

        override fun fetchMetadataForChar(char: Char): List<CharacterMetadata> {
            TODO()
        }
    }
}