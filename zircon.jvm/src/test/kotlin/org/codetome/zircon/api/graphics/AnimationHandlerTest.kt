package org.codetome.zircon.api.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.animation.Animation
import org.codetome.zircon.api.animation.AnimationHandler
import org.codetome.zircon.api.builder.AnimationBuilder
import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.graphics.DefaultAnimation
import org.codetome.zircon.internal.graphics.DefaultAnimationFrame
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class AnimationHandlerTest {

    lateinit var target: AnimationHandler

    @Mock
    lateinit var screenMock: Screen
    @Mock
    lateinit var animationMock: Animation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        target = AnimationHandler(screenMock)
    }

    @Test(expected = IllegalStateException::class)
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

        val uuid = UUID.randomUUID()
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

        val uuid = UUID.randomUUID()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        Mockito.`when`(animationMock.getId()).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.fetchNextFrame()).thenReturn(Optional.empty())
        Mockito.`when`(animationMock.getCurrentFrame())
                .then{
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

    @Test
    fun shouldProperlyWaitUntilFinish() {

        val uuid = UUID.randomUUID()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)

        Mockito.`when`(animationMock.getId()).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely()).thenReturn(false)
        Mockito.`when`(animationMock.fetchNextFrame()).thenReturn(Optional.empty())
        Mockito.`when`(animationMock.getCurrentFrame())
                .then{
                    currFrame
                }
                .then {
                    currFrame
                }
        Mockito.`when`(animationMock.hasNextFrame()).thenReturn(false)

        // TODO: figure out a proper test for this
        val result = target.addAnimation(animationMock).waitUntilFinish(2, TimeUnit.SECONDS)

        assertThat(result.isFinished()).isTrue()
    }
}
