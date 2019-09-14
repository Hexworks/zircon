package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.internal.resource.BuiltInCP437TilesetResource
import org.junit.Test
import java.io.File

class AnimationResourceTest {

    @Test
    fun shouldProperlyLoadAnimationFile() {
        AppConfigs.newConfig().enableBetaFeatures().build()
        val result = AnimationResource.loadAnimationFromStream(
                zipStream = this.javaClass.getResourceAsStream("/animations/skull.zap"),
                tileset = BuiltInCP437TilesetResource.ADU_DHABI_16X16)
        Assertions.assertThat(result).isNotNull()
    }
}
