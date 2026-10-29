package org.hexworks.zircon.examples

import org.hexworks.zircon.api.loadAnimation
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.Resource
import org.hexworks.zircon.api.resource.ResourceType

/**
 * A looping skull animation loaded from a `.zap` archive.
 */
public val animationDemo: Demo = Demo("Animation", CP437TilesetResources.rexPaint16x16()) { context ->
    val tileset = context.screen.tileset
    val skull = loadAnimation(Resource.create("/animations/skull.zap", ResourceType.PROJECT), tileset).apply {
        loopKind = Animation.InfiniteLoop
        position = context.contentPosition + Position(2, 1)
    }.build()
    context.screen.start(skull)
    DemoView(context.emptyContent())
}
