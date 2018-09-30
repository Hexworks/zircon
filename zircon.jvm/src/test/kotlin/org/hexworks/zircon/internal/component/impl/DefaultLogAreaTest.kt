package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.log.DefaultLogArea
import org.hexworks.zircon.internal.component.impl.log.HyperLinkElement
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Before
import org.junit.Test

class DefaultLogAreaTest {

    lateinit var target: DefaultLogArea
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = FONT
        target = LogAreaBuilder.newBuilder()
                .componentStyleSet(COMPONENT_STYLES)
                .position(POSITION)
                .size(SIZE)
                .tileset(tileset)
                .textWrap(TextWrap.WORD_WRAP)
                .build() as DefaultLogArea
    }

    @Test
    fun shouldProperlyAddNewRow() {
        target.addNewRow()
        assertThat(target.getLogElementBuffer().getLogElementRows().size)
                .isEqualTo(2)
    }

    @Test
    fun shouldProperlyAddNewText() {
        target.addText(TEXT)
        assertThat(target.getLogElementBuffer().getAllLogElements().first().getTextAsString())
                .isEqualTo(TEXT)
    }

    @Test
    fun shouldProperlyAddHyperlink() {
        target.addHyperLink(HYPERLINK_TEXT, HYPERLINK_ID)
        assertThat(target.getLogElementBuffer().getAllLogElements().first().getTextAsString())
                .isEqualTo(HYPERLINK_TEXT)
        assertThat((target.getLogElementBuffer().getAllLogElements().first() as HyperLinkElement).linkId)
                .isEqualTo(HYPERLINK_ID)
    }

    @Test
    fun hyperLinkElementShouldRaiseEventOnMousePressed() {
        val hyperLinkIds = mutableListOf<String>()

        target.addHyperLink(HYPERLINK_TEXT, HYPERLINK_ID)
        EventBus.subscribe<ZirconEvent.TriggeredHyperLink> { (hyperLinkId) ->
            hyperLinkIds.add(hyperLinkId)
        }
        val hyperLinkElementRenderPos = target.getLogElementBuffer().getAllLogElements().first()
                .renderedPositionArea!!.startPosition
        target.mousePressed(MouseAction(MouseActionType.MOUSE_PRESSED, 1, hyperLinkElementRenderPos))
        assertThat(hyperLinkIds.first())
                .isEqualTo(HYPERLINK_ID)

    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.tileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.componentStyleSet()
        assertThat(styles.getStyleFor(ComponentState.DEFAULT))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.FOCUSED))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.ACTIVE))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.getStyleFor(ComponentState.DISABLED))
                .isEqualTo(DEFAULT_STYLE)
    }

    companion object {
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val POSITION = Position.create(4, 5)
        val SIZE = Size.create(40, 10)
        val TEXT = "This is my log row"
        val HYPERLINK_TEXT = "This is a hyper link"
        val HYPERLINK_ID = "HyperLinkId"
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .foregroundColor(THEME.secondaryForegroundColor())
                .backgroundColor(TileColor.transparent())
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(DEFAULT_STYLE)
                .build()
    }
}
