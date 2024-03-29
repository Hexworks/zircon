package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.animation.AnimationResource.loadAnimationFromStream
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.examples.animations.HexworksSkullExample

fun hexworksSkull(position: Position, tileset: TilesetResource) = loadAnimationFromStream(
    zipStream = HexworksSkullExample::class.java.getResourceAsStream("/animations/skull.zap"),
    tileset = tileset
)
    // 0 means infinite
    .withLoopCount(0).apply {
        for (i in 0 until totalFrameCount) {
            addPosition(position)
        }
    }.build()
