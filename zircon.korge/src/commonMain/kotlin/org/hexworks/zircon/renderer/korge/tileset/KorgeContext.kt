package org.hexworks.zircon.renderer.korge.tileset

import korlibs.korge.render.BatchBuilder2D
import korlibs.korge.render.RenderContext
import korlibs.korge.view.RenderableView

data class KorgeContext(
    val context: RenderContext,
    val batch: BatchBuilder2D,
    val view: RenderableView
)