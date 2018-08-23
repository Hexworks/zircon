package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.animation.AnimationResource
import org.junit.Test
import java.io.File

class AnimationResourceTest {


    @Test
    fun shouldProperlyLoadAnimationFile() {
        val result = AnimationResource.loadAnimationFromStream(
                zipStream = File("src/test/resources/animations/skull.zap").inputStream(),
                tileset = CP437Tilesets.ADU_DHABI_16X16)
        Assertions.assertThat(result).isNotNull()
    }
}
