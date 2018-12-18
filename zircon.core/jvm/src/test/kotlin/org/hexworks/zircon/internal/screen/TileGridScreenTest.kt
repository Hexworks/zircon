package org.hexworks.zircon.internal.screen

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.animation.AnimationResource
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.kotlin.onInput
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.grid.RectangleTileGrid
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.io.File
import java.util.concurrent.atomic.AtomicBoolean

class TileGridScreenTest {

    lateinit var target: TileGridScreen
    lateinit var tileset: TilesetResource
    lateinit var grid: RectangleTileGrid

    @Before
    fun setUp() {
        AppConfigs.newConfig().enableBetaFeatures().build()
        tileset = FONT
        grid = RectangleTileGrid(
                tileset = tileset,
                size = SIZE)
        MockitoAnnotations.initMocks(this)
        target = TileGridScreen(grid)
    }

    @Test
    fun givenScreenWithAnimationWhenGivenInputThenFireOnInput() {
        target.display()
        val animation = AnimationResource.loadAnimationFromStream(
                zipStream = File("jvm/src/test/resources/animations/skull.zap").inputStream(),
                tileset = tileset)
                .setPositionForAll(Position.create(0, 0))
                .withLoopCount(0)
                .build()

        val inputFired = AtomicBoolean(false)
        target.onInput { inputFired.set(true) }

        //first of all lets make sure the default behaviour works. if a key is pressed I should get an input fired
        Zircon.eventBus.publish(
                event = ZirconEvent.Input(KeyStroke('a')),
                eventScope = ZirconScope)
        assertThat(inputFired.get()).isTrue()

        //now lets add the animation and make sure we can still get input
        target.startAnimation(animation)

        inputFired.set(false)
        Zircon.eventBus.publish(
                event = ZirconEvent.Input(KeyStroke('a')),
                eventScope = ZirconScope)
        assertThat(inputFired.get()).isTrue()

    }

    @Test
    fun shouldBeAbleToPutCharacterWhenPutCharacterIsCalled() {
        val char = 'x'
        val expected = TileBuilder.newBuilder()
                .withStyleSet(target.toStyleSet())
                .withCharacter(char)
                .build()
        val currCursorPos = target.cursorPosition()

        target.putCharacter(char)

        assertThat(target.getTileAt(currCursorPos).get()).isEqualTo(expected)
        assertThat(target.cursorPosition()).isEqualTo(currCursorPos.withRelativeX(1))

    }

    @Test
    fun shouldUseTerminalsFontWhenCreating() {
        assertThat(target.currentTileset().id)
                .isEqualTo(grid.currentTileset().id)
    }

    @Test
    fun shouldProperlyOverrideTerminalFontWhenHasOverrideFontAndDisplayIsCalled() {
        val expectedFont = BuiltInCP437TilesetResource.AESOMATICA_16X16
        target.useTileset(expectedFont)
        target.display()
        assertThat(target.currentTileset().id).isEqualTo(expectedFont.id)
        assertThat(grid.currentTileset().id).isEqualTo(expectedFont.id)
    }

    @Test(expected = IllegalArgumentException::class)
    fun shouldProperlyThrowExceptionWhenTyringToSetNonCompatibleFont() {
        target.useTileset(BuiltInCP437TilesetResource.BISASAM_20X20)
    }

    @Test
    fun shouldBeDrawnWhenCharacterSet() {
        target.setTileAt(Position.offset1x1(), CHAR)
        assertThat(target.getTileAt(Position.offset1x1()).get())
                .isEqualTo(CHAR)

    }

    @Test
    fun shouldClearProperlyWhenClearIsCalled() {
        target.setTileAt(Position.offset1x1(), CHAR)
        target.display()

        target.clear()

        assertThat(target.getTileAt(Position.offset1x1()))
                .isNotEqualTo(CHAR)
    }


    companion object {
        val SIZE = Size.create(10, 10)
        val FONT = BuiltInCP437TilesetResource.ROGUE_YUN_16X16
        val CHAR = TileBuilder.newBuilder()
                .withCharacter('x')
                .build()
    }
}
