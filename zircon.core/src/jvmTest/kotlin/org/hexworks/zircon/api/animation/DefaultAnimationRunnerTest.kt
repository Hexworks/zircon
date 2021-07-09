package org.hexworks.zircon.api.animation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.animation.DefaultAnimationRunner
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.impl.DefaultAnimationFrame
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.quality.Strictness
import java.util.concurrent.locks.ReentrantLock

@Suppress("UNUSED_VARIABLE")
class DefaultAnimationRunnerTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS)

    private val target = DefaultAnimationRunner()

    @Mock
    lateinit var animationMock: InternalAnimation

    @Test
    fun shouldCloseProperlyWhenClosed() {
        whenever(animationMock.id).thenReturn(UUID.randomUUID())
        whenever(animationMock.isLoopedIndefinitely).thenReturn(false)

        target.start(animationMock)
        target.close()

        assertThat(target.closed).isTrue()
        verify(animationMock).removeCurrentFrame()
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val infiniteAnimation = AnimationBuilder.newBuilder()
            .addFrame(DefaultAnimationFrame(Size.one(), listOf(), 1))
            .addPosition(Position.offset1x1())
            .withLoopCount(0)
            .withFps(1)
            .build()

        val result = target.start(infiniteAnimation)

        assertThat(result.isInfinite).isTrue()
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {

        val uuid = UUIDFactory.randomUUID()
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        whenever(animationMock.id).thenReturn(uuid)
        whenever(animationMock.isLoopedIndefinitely).thenReturn(false)

        val result = target.start(animationMock)

        assertThat(result.isRunning).isTrue()
    }

    @Test
    fun shouldReturnFinishedWhenAnimationIsFinished() {

        val uuid = UUIDFactory.randomUUID()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)

//        whenever(animationMock.id).thenReturn(uuid)
//        whenever(animationMock.isLoopedIndefinitely).thenReturn(false)
//        whenever(animationMock.fetchNextFrame()).thenReturn(Maybe.empty())
//        whenever(animationMock.fetchCurrentFrame())
//                .then {
//                    currFrame
//                }
//        whenever(animationMock.hasNextFrame()).thenReturn(false)

        val tileGrid = TileGridBuilder.newBuilder()
            .withSize(Size.create(50, 50))
            .withTileset(BuiltInCP437TilesetResource.ADU_DHABI_16X16)
            .build()

        val result = target.start(animationMock)

        target.updateAnimations(System.currentTimeMillis() + 1000, tileGrid)

        assertThat(result.isFinished).isTrue()
    }
}
