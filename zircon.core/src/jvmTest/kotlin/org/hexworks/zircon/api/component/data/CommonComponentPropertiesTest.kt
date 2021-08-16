package org.hexworks.zircon.api.component.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.data.CommonComponentProperties
import org.junit.Test
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer

class CommonComponentPropertiesTest {

    @Test
    fun shouldHaveProperDefaults() {
        val result = CommonComponentProperties<Component>()

        assertThat(result.componentStyleSet).isSameAs(ComponentStyleSet.unknown())
        assertThat(result.tileset).isSameAs(TilesetResource.unknown())
        assertThat(result.decorationRenderers).isEqualTo(listOf<DescriptorRenderer>())

    }


}
