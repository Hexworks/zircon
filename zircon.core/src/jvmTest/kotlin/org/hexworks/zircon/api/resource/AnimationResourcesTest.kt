package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.animation.AnimationResources
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Test
import java.io.File

class AnimationResourcesTest {

    @Test
    fun shouldProperlyLoadAnimationFile() {
        val result = AnimationResources.loadAnimationFromStream(
            zipStream = File("src/jvmTest/resources/animations/skull.zap").inputStream(),
            tileset = BuiltInCP437TilesetResource.ADU_DHABI_16X16
        )
        Assertions.assertThat(result).isNotNull()
    }
}
