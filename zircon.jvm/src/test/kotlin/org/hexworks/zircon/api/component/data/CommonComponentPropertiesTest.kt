package org.hexworks.zircon.api.component.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.junit.Test

class CommonComponentPropertiesTest {

    @Test
    fun shouldHaveProperDefaults() {
        val result = CommonComponentProperties()

        val componentStyleSet: ComponentStyleSet = ComponentStyleSet.defaultStyleSet()
        val tileset: TilesetResource = RuntimeConfig.config.defaultTileset
        val position: Position = Position.zero()
        val size: Size = Size.unknown()
        val boxType: BoxType = BoxType.SINGLE
        val wrapWithBox: Boolean = false
        val wrapWithShadow: Boolean = false
        val title: String = ""
        val decorationRenderers: List<ComponentDecorationRenderer> = listOf()

        assertThat(result.componentStyleSet).isEqualTo(componentStyleSet)
        assertThat(result.tileset).isEqualTo(tileset)
        assertThat(result.position).isEqualTo(position)
        assertThat(result.size).isEqualTo(size)
        assertThat(result.boxType).isEqualTo(boxType)
        assertThat(result.wrapWithBox).isEqualTo(wrapWithBox)
        assertThat(result.wrapWithShadow).isEqualTo(wrapWithShadow)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.decorationRenderers).isEqualTo(decorationRenderers)

    }

    @Test
    fun shouldProperlyReportIfHasTitleWhenItIsEmpty() {
        assertThat(CommonComponentProperties().hasTitle()).isFalse()
    }

    @Test
    fun shouldProperlyReportIfHasTitleWhenItIsBlank() {
        assertThat(CommonComponentProperties(title = " ").hasTitle()).isFalse()
    }

    @Test
    fun shouldProperlyReportIfHasTitleWhenItIsNotBlank() {
        assertThat(CommonComponentProperties(title = "foo").hasTitle()).isTrue()
    }

}
