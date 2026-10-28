package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet

public interface HasStyle<T : Tile> {

    public val styleSet: StyleSet

    public fun withStyle(style: StyleSet): T

}
