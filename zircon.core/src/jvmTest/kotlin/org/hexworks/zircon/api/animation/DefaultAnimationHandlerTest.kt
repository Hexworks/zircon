package org.hexworks.zircon.api.animation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.factory.IdentifierFactory

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.animation.AnimationBuilder
import org.hexworks.zircon.api.builder.grid.TileGridBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.animation.DefaultAnimation
import org.hexworks.zircon.internal.animation.DefaultAnimationFrame
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.locks.ReentrantLock

@Suppress("UNUSED_VARIABLE")
class DefaultAnimationHandlerTest {

    private lateinit var target: DefaultAnimationHandler

    @Mock
    lateinit var animationMock: Animation

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        AppConfig.newBuilder().enableBetaFeatures().build()
        target = DefaultAnimationHandler()
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldCloseProperlyWhenClosed() {
        target.close()

        target.startAnimation(DefaultAnimation(
                tick = 1,
                loopCount = 1,
                totalFrameCount = 1,
                uniqueFrameCount = 1,
                frames = listOf()))
    }

    @Test
    fun shouldReturnInfiniteWhenAnimationIsInfinite() {
        val infiniteAnimation = AnimationBuilder.newBuilder()
                .addFrame(DefaultAnimationFrame(Size.one(), listOf(), 1))
                .addPosition(Position.offset1x1())
                .withLoopCount(0)
                .withFps(1)
                .build()

        val result = target.startAnimation(infiniteAnimation)

        assertThat(result.isInfinite).isTrue()
    }

    @Test
    fun shouldReturnInProgressWhenAnimationIsInProgress() {

        val uuid = IdentifierFactory.randomIdentifier()
        val lock = ReentrantLock()
        val cond = lock.newCondition()

        Mockito.`when`(animationMock.id).thenReturn(uuid)
        Mockito.`when`(animationMock.isLoopedIndefinitely).thenReturn(false)
        // TODO
//        Mockito.`when`(animationMock.fetchCurrentFrame).then {
//            lock.lock()
//            cond.await(2, TimeUnit.SECONDS)
//            DefaultAnimationFrame(Size.one(), listOf(), 1)
//        }

        val result = target.startAnimation(animationMock)

        assertThat(result.isRunning).isTrue()
//        lock.lock()
//        cond.signalAll()
    }

    @Test
    fun shouldReturnFinishedWhenAnimationIsFinished() {

        val uuid = IdentifierFactory.randomIdentifier()
        val currFrame = DefaultAnimationFrame(Size.one(), listOf(), 1)

        // TODO
//        Mockito.`when`(animationMock.id).thenReturn(uuid)
//        Mockito.`when`(animationMock.isLoopedIndefinitely).thenReturn(false)
//        Mockito.`when`(animationMock.fetchNextFrame()).thenReturn(Maybe.empty())
//        Mockito.`when`(animationMock.fetchCurrentFrame())
//                .then {
//                    currFrame
//                }
//        Mockito.`when`(animationMock.hasNextFrame()).thenReturn(false)

        val tileGrid = TileGridBuilder.newBuilder()
                .withSize(Size.create(50, 50))
                .withTileset(BuiltInCP437TilesetResource.ADU_DHABI_16X16)
                .build()

        val result = target.startAnimation(animationMock)

        target.updateAnimations(System.currentTimeMillis() + 1000, tileGrid)

        assertThat(result.isFinished).isTrue()
    }
}
