package org.hexworks.zircon.internal.tileset.impl.korge

import korlibs.image.color.RGBA
import org.hexworks.zircon.api.color.TileColor

fun TileColor.toRGBA() = RGBA(red, green, blue, alpha)