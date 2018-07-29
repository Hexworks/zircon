package org.codetome.zircon.api.grid

import org.codetome.zircon.api.behavior.Clearable
import org.codetome.zircon.api.behavior.Closeable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Layerable
import org.codetome.zircon.poc.drawableupgrade.drawables.TilesetOverride
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

interface TileGrid<T: Any, S: Any>
    : Closeable, Clearable, DrawSurface<T>, TilesetOverride<T, S>, Layerable, Styleable {

    fun widthInPixels() = tileset().width() * getBoundableSize().xLength

    fun heightInPixels() = tileset().height() * getBoundableSize().yLength
}
