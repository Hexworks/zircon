package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.CharacterTile

/**
 * A [TextArea] is an editable text box.
 */
interface TextArea : Component, Scrollable {

    /**
     * The plain text this [TextArea] contains.
     */
    var text: String

    /**
     * The styled text this [TextArea] contains.
     */
    var textTiles: List<List<CharacterTile>>

}
