package org.codetome.zircon.font

import org.codetome.zircon.TextCharacter

interface Font<out R> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun hasDataForChar(char: Char): Boolean

    fun fetchRegionForChar(textCharacter: TextCharacter, vararg tags: String): R

    fun fetchMetadataForChar(char: Char): List<CharacterMetadata>
}