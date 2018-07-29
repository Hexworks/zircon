package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Clearable
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset

interface TileImage<T : Any, S: Any>
    : Boundable, Clearable, DrawSurface<T>, Drawable<T>, Styleable, TilesetOverride<T, S>
