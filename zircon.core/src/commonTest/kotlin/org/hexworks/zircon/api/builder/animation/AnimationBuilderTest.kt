package org.hexworks.zircon.api.builder.animation

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation.FiniteLoop
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import kotlin.test.Test
import kotlin.time.Duration.Companion.milliseconds

class AnimationBuilderTest {

    private fun build() = animation {
        fps = 50
        position = Position.OFFSET_1X1
        loopKind = FiniteLoop(1)
        frame {
            size = Size(20, 20)
            repeatCount = 1
            layer {
                tileset = CP437TilesetResources.bisasam20x20()
            }
        }
    }

    @Test
    fun fpsSetsTheTick() {
        build().tick shouldBe 20.milliseconds
    }

    @Test
    fun aLayerBuiltInsideAFrameBelongsToTheFrame() {
        val layerable = DefaultLayerable(Size(40, 40))
        (build() as InternalAnimation).displayNextFrame(layerable) shouldBe true
        layerable.layers.size shouldBe 1
        layerable.layers.first().position shouldBe Position.OFFSET_1X1
    }
}
