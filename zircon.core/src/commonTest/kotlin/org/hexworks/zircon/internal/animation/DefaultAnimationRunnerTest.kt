package org.hexworks.zircon.internal.animation

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.builder.animation.animation
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.util.nowMillis
import kotlin.test.Test

class DefaultAnimationRunnerTest {

    private val target = DefaultAnimationRunner()

    @Test
    fun shouldCloseProperlyWhenClosed() {
        target.start(finiteAnimation())
        target.close()

        target.closed shouldBe true
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val result = target.start(
            animation {
                frame {
                    size = Size.ONE
                    repeatCount = 1
                }
                position = Position.OFFSET_1X1
                loopKind = Animation.InfiniteLoop
                fps = 1
            }
        )

        result.isInfinite shouldBe true
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {
        val result = target.start(finiteAnimation())

        result.isRunning shouldBe true
    }

    @Test
    fun shouldReturnFinishedWhenAllFramesWereDisplayed() {
        val grid = tileGrid {
            size = Size(50, 50)
            tileset = CP437TilesetResources.aduDhabi16x16()
        }
        val result = target.start(finiteAnimation())

        // One frame shown once at 1 fps: after a second it has nothing left to display.
        target.updateAnimations(nowMillis(), grid)
        target.updateAnimations(nowMillis() + 1000, grid)
        target.updateAnimations(nowMillis() + 2000, grid)

        result.isFinished shouldBe true
    }

    private fun finiteAnimation() = animation {
        frame {
            size = Size.ONE
            repeatCount = 1
        }
        position = Position.OFFSET_1X1
        loopKind = Animation.FiniteLoop(1)
        fps = 1
    }
}
