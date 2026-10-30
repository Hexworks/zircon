package org.hexworks.zircon.internal.animation

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.builder.animation.animation
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.util.nowMillis
import kotlin.test.AfterTest
import kotlin.test.Test

class DefaultAnimationRunnerTest {

    private val target = DefaultAnimationRunner()
    private val errors = ErrorRecorder()
    private val grid = tileGrid {
        size = Size(50, 50)
        tileset = CP437TilesetResources.aduDhabi16x16()
    }

    @AfterTest
    fun tearDown() {
        errors.uninstall()
        grid.close()
    }

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
        val result = target.start(finiteAnimation())

        // One frame shown once at 1 fps: after a second it has nothing left to display.
        target.updateAnimations(nowMillis(), grid)
        target.updateAnimations(nowMillis() + 1000, grid)
        target.updateAnimations(nowMillis() + 2000, grid)

        result.isFinished shouldBe true
    }

    @Test
    fun a_frame_that_throws_stops_its_animation_and_is_reported_while_the_others_go_on() {
        val broken = target.start(finiteAnimation(frameSize = Size(100, 100)))
        val healthy = target.start(finiteAnimation())

        target.updateAnimations(nowMillis(), grid)

        broken.isFinished shouldBe true
        healthy.isRunning shouldBe true
        errors.errors shouldHaveSize 1
        errors.sources.single() shouldStartWith "Animation "
    }

    @Test
    fun two_animations_finishing_in_the_same_tick_are_both_stopped() {
        val first = target.start(finiteAnimation())
        val second = target.start(finiteAnimation())

        val now = nowMillis()
        target.updateAnimations(now, grid)
        target.updateAnimations(now + 1000, grid)
        target.updateAnimations(now + 2000, grid)

        first.isFinished shouldBe true
        second.isFinished shouldBe true
    }

    /** One frame with one layer of [frameSize] at 1 fps; a layer larger than the grid fails to display. */
    private fun finiteAnimation(frameSize: Size = Size.ONE) = animation {
        frame {
            size = frameSize
            repeatCount = 1
            layer { }
        }
        position = Position.OFFSET_1X1
        loopKind = Animation.FiniteLoop(1)
        fps = 1
    }
}
