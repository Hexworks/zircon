package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat

import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultAnimationTest {

    @Before
    fun setUp() {
        AppConfig.newBuilder().enableBetaFeatures().build()
    }

    @Test
    fun shouldProperlyBuildFromResource() {
        val builder = AnimationResource.loadAnimationFromStream(
                zipStream = this.javaClass.getResourceAsStream("/animations/skull.zap"),
                tileset = BuiltInCP437TilesetResource.AESOMATICA_16X16)
        (0 until EXPECTED_LENGTH).forEach { _ ->
            builder.addPosition(Position.defaultPosition())
        }
        val result = builder.build()
        assertThat(result.totalFrameCount).isEqualTo(EXPECTED_LENGTH)
        assertThat(result.uniqueFrameCount).isEqualTo(EXPECTED_FRAME_COUNT)
        assertThat(result.tick).isEqualTo(EXPECTED_TICK)
        assertThat(result.loopCount).isEqualTo(EXPECTED_LOOP_COUNT)
    }

    companion object {
        const val EXPECTED_FRAME_COUNT = 58
        const val EXPECTED_LENGTH = 90
        const val EXPECTED_TICK = 66L
        const val EXPECTED_LOOP_COUNT = 1

    }
}
