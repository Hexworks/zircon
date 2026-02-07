package org.hexworks.zircon.api.animation

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.application.appConfig
import org.hexworks.zircon.api.builder.animation.animation
import org.hexworks.zircon.api.builder.application.withSize
import org.hexworks.zircon.api.builder.grid.tileGrid
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.animation.DefaultAnimationRunner
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationFrame
import kotlin.test.Test

@Suppress("UNUSED_VARIABLE")
class DefaultAnimationRunnerTest {

    private val target = DefaultAnimationRunner()

    private val animationMock = mockk<InternalAnimation>(relaxed = true)

    @Test
    fun shouldCloseProperlyWhenClosed() {
        every { animationMock.id } returns UUID.randomUUID()
        every { animationMock.isLoopedIndefinitely } returns false

        target.start(animationMock)
        target.close()

        target.closed shouldBe true
        verify { animationMock.removeCurrentFrame() }
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val infiniteAnimation = animation {
            frame {
                size = Size.one()
                repeatCount = 1
            }
            position = Position.offset1x1()
            loopKind = Animation.InfiniteLoop
            fps = 1
        }

        val result = target.start(infiniteAnimation)

        result.isInfinite shouldBe true
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {

        val uuid = UUID.randomUUID()

        every { animationMock.id } returns uuid
        every { animationMock.isLoopedIndefinitely } returns false

        val result = target.start(animationMock)

        result.isRunning shouldBe true
    }

    @Test
    fun shouldReturnFinishedWhenAnimationIsFinished() {

        val uuid = UUID.randomUUID()

        every { animationMock.id } returns uuid
        every { animationMock.isLoopedIndefinitely } returns false
        // displayNextFrame returns false by default (relaxed mock), indicating no more frames

        val grid = tileGrid {
            config = appConfig {
                withSize {
                    width = 50
                    height = 50
                }
                defaultTileset = CP437TilesetResources.aduDhabi16x16()
            }
        }

        val result = target.start(animationMock)

        target.updateAnimations(currentTimeMs(), grid)

        result.isFinished shouldBe true
    }
}

private fun currentTimeMs(): Long = korlibs.time.DateTime.nowUnixMillisLong() + 1000
