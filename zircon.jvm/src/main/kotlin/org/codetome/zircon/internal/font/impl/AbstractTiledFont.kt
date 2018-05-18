package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.util.Identifier

abstract class AbstractTiledFont(private val metadata: Map<Char, List<CharacterMetadata>>,
                                 private val metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy()) : Font {

    private val id = Identifier.randomIdentifier()

    override fun getId() = id

    override fun hasDataForChar(char: Char) = metadata.containsKey(char)

    override fun fetchMetadataForChar(char: Char): List<CharacterMetadata> = metadata[char] ?: listOf()

    protected fun fetchMetaFor(textCharacter: TextCharacter): CharacterMetadata {
        if (!hasDataForChar(textCharacter.getCharacter()))
            if (TextUtils.isPrintableCharacter(textCharacter.getCharacter()))
                throw IllegalArgumentException("No texture region exists for printable character: '${textCharacter.getCharacter().toInt()}'!")
            else
                throw IllegalArgumentException("No texture region exists for non-printable character: '${textCharacter.getCharacter().toInt()}'!")

        val tags = textCharacter.getTags()
        val filtered = metadata[textCharacter.getCharacter()]!!.filter { it.tags.containsAll(tags.toList()) }


        require(filtered.isNotEmpty()) {
            "Can't find font texture region for tag(s): ${tags.joinToString()}"
        }
        return metadataPickingStrategy.pickMetadata(filtered)
    }
}
