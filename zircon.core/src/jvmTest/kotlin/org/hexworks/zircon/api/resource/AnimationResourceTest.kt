package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions

import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Test
import java.io.File

class AnimationResourceTest {


    @Test
    fun shouldProperlyLoadAnimationFile() {
        AppConfig.newBuilder().enableBetaFeatures().build()
        val result = AnimationResource.loadAnimationFromStream(
            zipStream = File("src/jvmTest/resources/animations/skull.zap").inputStream(),
            tileset = BuiltInCP437TilesetResource.ADU_DHABI_16X16
        )
        Assertions.assertThat(result).isNotNull()
    }
}
