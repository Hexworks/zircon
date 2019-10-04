package org.hexworks.zircon.api.component.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.junit.Test

class CommonComponentPropertiesTest {

    @Test
    fun shouldHaveProperDefaults() {
        val result = CommonComponentProperties<Component>()

        val componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()
        val tileset: TilesetResource = RuntimeConfig.config.defaultTileset
        val decorationRenderers: List<ComponentDecorationRenderer> = listOf()

        assertThat(result.componentStyleSet).isEqualTo(componentStyleSet)
        assertThat(result.tileset).isEqualTo(tileset)
        assertThat(result.decorationRenderers).isEqualTo(decorationRenderers)

    }


}
