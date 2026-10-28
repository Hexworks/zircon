package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.CharacterTile

/**
 * A [TextArea] is an editable text box.
 */
public interface TextArea : Component, Scrollable {

    /**
     * The plain text this [TextArea] contains.
     */
    public var text: String

    /**
     * The [CharacterTile]s this [TextArea] contains.
     */
    public var textTiles: List<List<CharacterTile>>

}
