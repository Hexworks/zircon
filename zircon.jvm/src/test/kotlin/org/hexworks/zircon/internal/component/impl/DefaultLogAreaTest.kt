package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.log.DefaultLogArea
import org.hexworks.zircon.internal.component.impl.log.LogComponentElement
import org.hexworks.zircon.internal.component.impl.log.LogTextElement
import org.junit.Before
import org.junit.Test

class DefaultLogAreaTest {

    lateinit var target: DefaultLogArea
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = LogAreaBuilder.newBuilder()
                .withComponentStyleSet(COMPONENT_STYLES)
                .withPosition(POSITION)
                .withSize(SIZE)
                .logRowHistorySize(ROW_HISTORY_SIZE)
                .withTileset(tileset)
                .wrapLogElements(true)
                .build() as DefaultLogArea
    }

    @Test
    fun shouldProperlyAddNewRow() {
        target.addNewRows()
        assertThat(target.getLogElementBuffer().getLogElementRows().size)
                .isEqualTo(2)
    }

    @Test
    fun shouldProperlyAddNewText() {
        target.addTextElement(TEXT)
        assertThat((target.getLogElementBuffer().getAllLogElements().first() as LogTextElement).text)
                .isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyAddComponent() {
        target.addComponentElement(COMPONENT)
        assertThat(target.getLogElementBuffer().getAllLogElements().first())
                .isInstanceOf(LogComponentElement::class.java)
    }

    @Test
    fun shouldProperlyRemoveComponentIfItGetsDisposedDueHistorySize() {
        target.addComponentElement(COMPONENT)
        target.addNewRows(ROW_HISTORY_SIZE)
        target.addTextElement(TEXT)

        assertThat(target.children.contains(COMPONENT))
                .isFalse()
    }


    @Test
    fun logElementShouldProperlyScrollDownIfNecessary() {
        target.addTextElement(TEXT)
        target.addNewRows(10)
        target.addTextElement(ALTERNATE_TEXT)
        assertThat(target.visibleOffset)
                .isEqualTo(Position.create(0, 1))
    }


    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(40, 10)
        val ROW_HISTORY_SIZE = 15
        val TEXT = "This is my log row"
        val ALTERNATE_TEXT = "This is my other log row"
        val COMPONENT = Components.button()
                .withDecorationRenderers()
                .text("Button")
                .build()
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor)
                .backgroundColor(TileColor.transparent())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}
