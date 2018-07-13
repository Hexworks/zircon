package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.multiplatform.api.Identifier

class NoFont : Font {

    private val id = Identifier.randomIdentifier()

    override fun getId() = id

    override fun getWidth() = signalNoOp()

    override fun getHeight() = signalNoOp()

    override fun hasDataForChar(char: Char) = signalNoOp()

    override fun fetchRegionForChar(textCharacter: TextCharacter) = signalNoOp()

    override fun fetchMetadataForChar(char: Char) = signalNoOp()

    private fun signalNoOp(): Nothing = TODO("No Font was supplied! Try setting a Font!")
}
