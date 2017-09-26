package org.codetome.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.api.Modifiers.BorderType.DASHED
import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.builder.LabelBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.graphics.BoxType
import org.junit.Before
import org.junit.Test

class DefaultPanelTest {

    lateinit var target: DefaultPanel

    @Before
    fun setUp() {
        target = PanelBuilder.newBuilder()
                .addShadow()
                .boxType(BOX_TYPE)
                .title(TITLE)
                .addBorder(Modifiers.BORDER.of(DASHED))
                .size(SIZE)
                .wrapInBox()
                .position(POSITION)
                .build() as DefaultPanel
    }

    @Test
    fun shouldHaveProperTitle() {
        assertThat(target.getTitle()).isEqualTo(TITLE)
    }

    @Test
    fun shouldProperlySetPosition() {
        assertThat(target.getPosition()).isEqualTo(POSITION)
    }

    @Test
    fun shouldProperlySetSize() {
        assertThat(target.getBoundableSize()).isEqualTo(SIZE)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyTheme(THEME)

        ComponentState.values().forEach {
            assertThat(target.getComponentStyles().getStyleFor(it)).isEqualTo(EXPECTED_STYLE)
        }
    }

    @Test
    fun shouldProperlyApplyThemeToChildren() {
        val component = LabelBuilder.newBuilder()
                .text("text")
                .build()
        target.addComponent(component)
        target.applyTheme(THEME)

        assertThat(component.getComponentStyles().getCurrentStyle())
                .isEqualTo(DefaultLabelTest.DEFAULT_STYLE)
    }

    companion object {
        val BOX_TYPE = BoxType.LEFT_RIGHT_DOUBLE
        val TITLE = "TITLE"
        val SIZE = Size.of(5, 6)
        val POSITION = Position.of(2, 3)
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val EXPECTED_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.getBrightForegroundColor())
                .backgroundColor(THEME.getBrightBackgroundColor())
                .build()
    }
}