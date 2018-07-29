package org.codetome.zircon.poc.drawableupgrade.renderer

import org.codetome.zircon.api.behavior.Clearable

interface Renderer<SURFACE : Any> : Clearable {

    val surface: SURFACE

    fun render()
}
