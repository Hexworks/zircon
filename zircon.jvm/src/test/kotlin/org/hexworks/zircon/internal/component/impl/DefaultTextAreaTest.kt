package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.ColorThemeResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.event.ZirconEvent
import org.junit.Before
import org.junit.Test

class DefaultTextAreaTest {

    lateinit var target: DefaultTextArea
    lateinit var tileset: TilesetResource

    @Before
    fun setUp() {
        tileset = DefaultLabelTest.FONT
        target = TextAreaBuilder.newBuilder()
                .withComponentStyleSet(COMPONENT_STYLES)
                .withSize(SIZE)
                .withTileset(tileset)
                .withPosition(POSITION)
                .withText(TEXT)
                .build() as DefaultTextArea
    }

    @Test
    fun shouldProperlyReturnText() {
        assertThat(target.text).isEqualTo(TEXT)
    }

    @Test
    fun shouldUseProperFont() {
        assertThat(target.currentTileset().id)
                .isEqualTo(tileset.id)
    }

    @Test
    fun shouldAcceptFocus() {
        assertThat(target.acceptsFocus()).isTrue()
    }

    @Test
    fun shouldProperlyGiveFocus() {
        target.applyColorTheme(THEME)
        val pos = Position.create(2, 3)
        val tile = Tile.createCharacterTile('x', StyleSet.defaultStyle())
        target.setTileAt(pos, tile)
        var cursorVisible = false
        EventBus.subscribe<ZirconEvent.RequestCursorAt> {
            cursorVisible = true
        }

        target.giveFocus()

        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(FOCUSED_STYLE)
        assertThat(target.getTileAt(pos)).isNotEqualTo(tile)
        assertThat(cursorVisible).isTrue()
    }

    @Test
    fun shouldProperlyTakeFocus() {
        var cursorHidden = false
        EventBus.subscribe<ZirconEvent.HideCursor> {
            cursorHidden = true
        }
        target.takeFocus()

        assertThat(target.componentStyleSet.currentStyle()).isEqualTo(DEFAULT_STYLE)
        assertThat(cursorHidden).isTrue()
    }

    @Test
    fun shouldProperlyApplyTheme() {
        target.applyColorTheme(THEME)
        val styles = target.componentStyleSet
        assertThat(styles.fetchStyleFor(ComponentState.DEFAULT))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.MOUSE_OVER))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.FOCUSED))
                .isEqualTo(FOCUSED_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.ACTIVE))
                .isEqualTo(DEFAULT_STYLE)
        assertThat(styles.fetchStyleFor(ComponentState.DISABLED))
                .isEqualTo(DISABLED_STYLE)
    }

    @Test
    fun shouldRefreshDrawSurfaceIfSetText() {
        target.text = UPDATE_TEXT.toString()
        val character = target.tileGraphics.getTileAt(Position.defaultPosition())
        assertThat(character.get().asCharacterTile().get().character)
                .isEqualTo(UPDATE_TEXT)
    }

    companion object {
        const val TEXT = "text"
        const val UPDATE_TEXT = 'U'
        val THEME = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()
        val FONT = BuiltInCP437TilesetResource.WANDERLUST_16X16
        val SIZE = Size.create(10, 6)
        val POSITION = Position.create(4, 5)
        val DEFAULT_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.secondaryBackgroundColor)
                .withBackgroundColor(THEME.secondaryForegroundColor)
                .build()
        val FOCUSED_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.primaryBackgroundColor)
                .withBackgroundColor(THEME.primaryForegroundColor)
                .build()
        val DISABLED_STYLE = StyleSetBuilder.newBuilder()
                .withForegroundColor(THEME.secondaryForegroundColor)
                .withBackgroundColor(THEME.secondaryBackgroundColor)
                .build()
        val COMPONENT_STYLES = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(DEFAULT_STYLE)
                .build()
    }
}
