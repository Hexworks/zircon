package org.hexworks.zircon.examples.animations

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.builder.animation.animation
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.extensions.darkenByPercent
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.extensions.draw
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** A looping skull animation loaded from a `.zap` archive. */
public val skullExample: Example = Example("Skull") { context ->
    val skull = context.assets.skull.createCopy().apply {
        loopKind = Animation.InfiniteLoop
        position = Position(
            (context.contentSize.width - SKULL_SIZE.width) / 2,
            (context.contentSize.height - SKULL_SIZE.height) / 2,
        ) + Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    }.build()
    var handle: AnimationHandle? = null
    ExampleView(
        content = context.emptyContent(),
        onShown = { handle = context.screen.start(skull) },
        onHidden = { handle?.stop() },
    )
}

/** The Zircon logo from a REXPaint file, fading out and in: one frame per shade. */
public val logoExample: Example = Example("Logo") { context ->
    val logo = context.assets.rexFiles.getValue("zircon_logo.xp")
    val size = LOGO_SIZE
    val tileset = context.screen.tileset
    val image = tileGraphics { this.size = size; this.tileset = tileset }
    logo.toLayerList(tileset).forEach { image.draw(it) }

    fun shaded(percent: Double) = tileGraphics { this.size = size; this.tileset = tileset }.apply {
        draw(image)
        transform { _, tile ->
            if (tile is CharacterTile) {
                tile.withBackgroundColor(tile.backgroundColor.darkenByPercent(percent))
                    .withForegroundColor(tile.foregroundColor.darkenByPercent(percent))
            } else tile
        }
    }

    val offset = Position(
        (context.contentSize.width - size.width) / 2,
        (context.contentSize.height - size.height) / 2,
    ) + Position(EXAMPLE_TAB_WIDTH, EXAMPLE_TOP)
    val fade = animation {
        loopKind = Animation.InfiniteLoop
        fps = 20
        position = offset
        for (idx in 20 downTo 1) {
            frame {
                this.size = size
                repeatCount = if (idx == 1) 40 else 1
                layer { this.size = size; this.tileset = tileset; tileGraphics = shaded(idx / 20.0) }
            }
        }
        for (idx in 0..20) {
            frame {
                this.size = size
                repeatCount = if (idx == 20) 20 else 1
                layer { this.size = size; this.tileset = tileset; tileGraphics = shaded(idx / 20.0) }
            }
        }
    }
    var handle: AnimationHandle? = null
    ExampleView(
        content = context.emptyContent(),
        onShown = { handle = context.screen.start(fade) },
        onHidden = { handle?.stop() },
    )
}

public val animationExamples: List<Example> = listOf(skullExample, logoExample)

private val SKULL_SIZE = Size(13, 24)
private val LOGO_SIZE = Size(59, 27)
