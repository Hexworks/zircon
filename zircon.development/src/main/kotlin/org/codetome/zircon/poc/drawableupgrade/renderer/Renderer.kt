package org.codetome.zircon.poc.drawableupgrade.renderer

interface Renderer<SURFACE : Any> {

    val surface: SURFACE

    fun render()
}
