package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultListItemRenderer
import org.junit.Before
import org.junit.Test

@Suppress("UNCHECKED_CAST")
class DefaultListItemTest : ComponentImplementationTest<DefaultListItem>() {

    override lateinit var target: DefaultListItem
    override lateinit var drawWindow: DrawWindow

    override val expectedComponentStyles: ComponentStyleSet
        get() = componentStyleSet {
            defaultStyle = styleSet {
                foregroundColor = DEFAULT_THEME.secondaryForegroundColor
                backgroundColor = transparent()
            }
        }

    @Before
    override fun setUp() {
        rendererStub = ComponentRendererStub(DefaultListItemRenderer())
        drawWindow = tileGraphics {
            size = SIZE_3_4
        }.toDrawWindow()
        target = DefaultListItem(
            componentMetadata = ComponentMetadata(
                size = SIZE_3_4,
                relativePosition = POSITION_2_3,
                componentStyleSetProperty = COMPONENT_STYLES.toProperty(),
                tilesetProperty = TILESET_REX_PAINT_20X20.toProperty()
            ),
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = rendererStub as ComponentRenderer<ListItem>
            ),
            textProperty = TEXT.toProperty()
        )
    }

    @Test
    fun shouldSetTextProperly() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldNotAcceptFocus() {
        assertThat(target.acceptsFocus()).isFalse()
    }

    @Test
    fun shouldNotAcceptFocusWhenGiven() {
        assertThat(target.focusGiven()).isEqualTo(Pass)
    }

    companion object {

        const val TEXT = "FOO"
    }

}
