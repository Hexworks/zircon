package org.hexworks.zircon.api.component.data

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.data.CommonComponentProperties
import kotlin.test.Test

class CommonComponentPropertiesTest {

    @Test
    fun shouldHaveProperDefaults() {
        val result = CommonComponentProperties<Component>()

        result.componentStyleSet shouldBe ComponentStyleSet.UNKNOWN
        result.tileset shouldBe TilesetResource.unknown()
        result.decorationRenderers shouldBe listOf<ComponentDecorationRenderer>()

    }


}
