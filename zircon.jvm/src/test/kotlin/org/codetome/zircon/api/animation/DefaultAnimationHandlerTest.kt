package org.codetome.zircon.api.animation

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.animation.DefaultAnimation
import org.codetome.zircon.internal.animation.DefaultAnimationFrame
import org.codetome.zircon.internal.multiplatform.api.Identifier
import org.codetome.zircon.internal.multiplatform.api.Maybe
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
    lateinit var screenMock: Screen
    @Mock
    lateinit var animationMock: Animation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = DefaultAnimationHandler(screenMock)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldCloseProperlyWhenClosed() {
        target.close()

        target.addAnimation(DefaultAnimation(listOf(), 1L, 1, 1, 1))
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val infiniteAnimation = AnimationBuilder.newBuilder()
                .addFrame(DefaultAnimationFrame(Size.one(), listOf(), 1))
                .addPosition(Position.offset1x1())
                .loopCount(0)
                .fps(1)
                .build()

        val result = target.addAnimation(infiniteAnimation)

        assertThat(result.isInfinite()).isTrue()
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {

        val uuid = Identifier.randomIdentifier()
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        Mockito.`when`(animationMock.getId()).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.getCurrentFrame()).then {
            lock.lock()
            cond.await(2, TimeUnit.SECONDS)
            DefaultAnimationFrame(Size.one(), listOf(), 1)
        }

        val result = target.addAnimation(animationMock)

        assertThat(result.isRunning()).isTrue()
        lock.lock()
        cond.signalAll()
    }

    @Test
    fun shouldReturnFinishedWhenAnimationIsFinished() {

        val uuid = Identifier.randomIdentifier()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        Mockito.`when`(animationMock.getId()).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.fetchNextFrame()).thenReturn(Maybe.empty())
        Mockito.`when`(animationMock.getCurrentFrame())
                .then {
                    currFrame
                }
                .then {
                    lock.lock()
                    cond.signalAll()
                    lock.unlock()
                    currFrame
                }
        Mockito.`when`(animationMock.hasNextFrame()).thenReturn(false)

        val result = target.addAnimation(animationMock)
        lock.lock()
        cond.await(2, TimeUnit.SECONDS)

        assertThat(result.isFinished()).isTrue()
    }
}
