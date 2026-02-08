package org.hexworks.zircon.renderer.korge

import korlibs.image.color.RGBA
import org.hexworks.zircon.api.color.Color

fun Color.toRGBA() = RGBA(red, green, blue, alpha)