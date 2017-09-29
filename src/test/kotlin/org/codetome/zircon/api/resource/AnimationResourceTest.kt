package org.codetome.zircon.api.resource

import org.assertj.core.api.Assertions
import org.codetome.zircon.api.beta.animation.AnimationResource
import org.junit.Test

class AnimationResourceTest {


    @Test
    fun shouldProperlyLoadAnimationFile() {
        val result = AnimationResource.loadAnimationFromFile("src/test/resources/animations/skull.zap")
        Assertions.assertThat(result).isNotNull()
    }
}