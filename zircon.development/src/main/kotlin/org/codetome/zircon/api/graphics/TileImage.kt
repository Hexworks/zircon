package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.*
import org.codetome.zircon.api.behavior.TilesetOverride

interface TileImage<T : Any, S: Any>
    : Boundable, Clearable, DrawSurface<T>, Drawable<T>, Styleable, TilesetOverride<T, S>
