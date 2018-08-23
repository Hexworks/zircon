package org.hexworks.zircon.api.animation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.BuiltInCP437Tileset
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.animation.DefaultAnimation
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class DefaultAnimationHandlerTest {

    lateinit var target: DefaultAnimationHandler

    @Mock
    lateinit var animationMock: Animation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = DefaultAnimationHandler()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldCloseProperlyWhenClosed() {
        target.close()

        target.startAnimation(DefaultAnimation(listOf(), 1L, 1, 1, 1))
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val infiniteAnimation = AnimationBuilder.newBuilder()
                .addFrame(DefaultAnimationFrame(Size.one(), listOf(), 1))
                .addPosition(Position.offset1x1())
                .loopCount(0)
                .fps(1)
                .build()

        val result = target.startAnimation(infiniteAnimation)

        assertThat(result.isInfinite()).isTrue()
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {

        val uuid = Identifier.randomIdentifier()
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        Mockito.`when`(animationMock.id).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.getCurrentFrame()).then {
            lock.lock()
            cond.await(2, TimeUnit.SECONDS)
            DefaultAnimationFrame(Size.one(), listOf(), 1)
        }

        val result = target.startAnimation(animationMock)

        assertThat(result.isRunning()).isTrue()
        lock.lock()
        cond.signalAll()
    }

    @Test
    fun shouldReturnFinishedWhenAnimationIsFinished() {

        val uuid = Identifier.randomIdentifier()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)

        Mockito.`when`(animationMock.id).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.fetchNextFrame()).thenReturn(Maybe.empty())
        Mockito.`when`(animationMock.getCurrentFrame())
                .then {
                    currFrame
                }
        Mockito.`when`(animationMock.hasNextFrame()).thenReturn(false)

        val tileGrid = TileGridBuilder.newBuilder()
                .size(Size.create(50, 50))
                .tileset(BuiltInCP437Tileset.ADU_DHABI_16X16)
                .build()

        val result = target.startAnimation(animationMock)

        target.updateAnimations(System.currentTimeMillis() + 1000, tileGrid)

        assertThat(result.isFinished()).isTrue()
    }
}
