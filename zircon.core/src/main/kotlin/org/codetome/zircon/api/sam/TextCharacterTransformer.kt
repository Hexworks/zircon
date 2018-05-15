package org.codetome.zircon.api.sam

import org.codetome.zircon.api.TextCharacter

interface TextCharacterTransformer {

    fun transform(tc: TextCharacter): TextCharacter
}
