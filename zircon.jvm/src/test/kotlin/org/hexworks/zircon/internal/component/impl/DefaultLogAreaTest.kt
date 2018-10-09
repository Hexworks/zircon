package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.junit.Before
import org.junit.Test

class DefaultLogAreaTest {

    lateinit var target: DefaultLogArea

    @Before
    fun setUp() {
        target = LogAreaBuilder.newBuilder()
                .withComponentStyleSet(COMPONENT_STYLES)
                .withPosition(POSITION)
                .withSize(SIZE)
                .withLogRowHistorySize(ROW_HISTORY_SIZE)
                .withTileset(TILESET)
                .build() as DefaultLogArea
    }

    @Test
    fun shouldProperlyAddNewRow() {
        target.addNewRows(1)
        assertThat(target.children.size).isEqualTo(1)
    }

    @Test
    fun shouldProperlyAddNewText() {
        target.addParagraph(TEXT)
        val child = (target.children.first() as DefaultTextBox)
        assertThat((child.children.first() as Paragraph).text).isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyAddComponent() {
        target.addInlineComponent(COMPONENT)
        target.commitInlineElements()
        val child = (target.children.first() as DefaultTextBox)
        assertThat(target.children.size).isEqualTo(1)
        assertThat(child.children.first()).isSameAs(COMPONENT)
    }

    @Test
    fun shouldProperlyRemoveComponentIfItGetsDisposedDueHistorySize() {
        target.addInlineComponent(COMPONENT)
        target.commitInlineElements()
        target.addNewRows(ROW_HISTORY_SIZE)
        target.addParagraph(TEXT)

        assertThat(target.children.contains(COMPONENT)).isFalse()
    }


    @Test
    fun logElementShouldProperlyScrollDownIfNecessary() {
        target.addParagraph(TEXT)
        target.addNewRows(10)
        target.addParagraph(ALTERNATE_TEXT)
        val child = (target.children.first() as DefaultTextBox)
        assertThat(child.children).isEmpty() // this means it has no paragraph as a child
    }


    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val TILESET = CP437TilesetResources.wanderlust16x16()
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(40, 10)
        val ROW_HISTORY_SIZE = 15
        val TEXT = "This is my log row"
        val ALTERNATE_TEXT = "This is my other log row"
        val COMPONENT = Components.button()
                .withDecorationRenderers()
                .withText("Button")
                .build()
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.secondaryForegroundColor)
                .withBackgroundColor(TileColor.transparent())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(DEFAULT_STYLE)
                .build()
    }
}
