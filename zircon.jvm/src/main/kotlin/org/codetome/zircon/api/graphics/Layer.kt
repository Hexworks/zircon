package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.behavior.Movable
import org.codetome.zircon.util.Maybe
import java.util.*

/**
 * A [Layer] is a specialized [TextImage] which is drawn upon a
 * [Layerable] object. A [Layer] differs from a [TextImage] in
 * the way it is handled. It can be repositioned relative to its
 * parent while a [TextImage] cannot.
 */
interface Layer : TextImage, Movable, FontOverride {

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains.
     */
    fun fetchPositions(): Set<Position>

    /**
     * Fetches all the (absolute) [Position]s which this
     * [Layer] contains and is not `EMPTY`.
     */
    override fun fetchFilledPositions(): List<Position>

    /**
     * Copies this [Layer].
     */
    fun createCopy(): Layer

    /**
     * Same as [Layer.getCharacterAt] but will not use the offset of this [Layer] (eg: just position instead of position - offset).
     */
    fun getRelativeCharacterAt(position: Position): Maybe<TextCharacter>

    /**
     * Same as [Layer.setCharacterAt] but will not use the offset of this [Layer] (eg: just position instead of position - offset).
     */
    fun setRelativeCharacterAt(position: Position, character: TextCharacter): Boolean
}
