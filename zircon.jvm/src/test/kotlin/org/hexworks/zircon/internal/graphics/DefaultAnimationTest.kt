package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.junit.Before
import org.junit.Test

class DefaultAnimationTest {

    @Before
    fun setUp() {
        AppConfigs.newConfig().enableBetaFeatures().build()
    }

    @Test
    fun shouldProperlyBuildFromResource() {
        val builder = AnimationResource.loadAnimationFromStream(
                zipStream = this.javaClass.getResourceAsStream("/animations/skull.zap"),
                tileset = BuiltInCP437TilesetResource.AESOMATICA_16X16)
        (0 until EXPECTED_LENGTH).forEach {
            builder.addPosition(Position.defaultPosition())
        }
        val result = builder.build()
        assertThat(result.getTotalFrameCount()).isEqualTo(EXPECTED_LENGTH)
        assertThat(result.getFrameCount()).isEqualTo(EXPECTED_FRAME_COUNT)
        assertThat(result.getTick()).isEqualTo(EXPECTED_TICK)
        assertThat(result.getLoopCount()).isEqualTo(EXPECTED_LOOP_COUNT)
    }

    companion object {
        val EXPECTED_FRAME_COUNT = 58
        val EXPECTED_LENGTH = 90
        val EXPECTED_TICK = 66L
        val EXPECTED_LOOP_COUNT = 1

    }
}
