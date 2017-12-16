package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.Symbols
import org.codetome.zircon.api.beta.component.GameComponent
import org.codetome.zircon.api.beta.component.ProjectionMode
import org.codetome.zircon.api.beta.component.Size3D
import org.codetome.zircon.api.beta.component.TextImageGameArea
import org.codetome.zircon.api.builder.*
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.resource.CP437TilesetResource
import org.codetome.zircon.api.resource.ColorThemeResource
import org.codetome.zircon.internal.graphics.BoxType
import java.util.*
import java.util.function.Consumer

object Playground {

    private val TERMINAL_WIDTH = 60
    private val TERMINAL_HEIGHT = 30
    private val SIZE = Size.of(TERMINAL_WIDTH, TERMINAL_HEIGHT)
    private val FONT = CP437TilesetResource.REX_PAINT_16X16.toFont()
    private val LEVEL_COUNT = 10
    private val VISIBLE_LEVEL_COUNT = 5


    val WALL_COLOR = TextColorFactory.fromString("#333333")
    val ROOF_COLOR = TextColorFactory.fromString("#4d4d4d")
    val TC = TextCharacterBuilder.newBuilder()
            .build()
    val WALL_WITH_FULL_WINDOW = TC.withCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(ROOF_COLOR)
    val WALL_WITH_NARROW_WINDOW = TC.withCharacter('-')
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(ROOF_COLOR)

    val BUILDING_INSIDE = TC.withCharacter('#')
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(ROOF_COLOR)

    val BUILDING_ROOF = TC.withBackgroundColor(ROOF_COLOR)
            .withForegroundColor(WALL_COLOR)
            .withCharacter(' ')
    val BUILDING_ROOF_TOP_LEFT = BUILDING_ROOF.withCharacter(Symbols.SINGLE_LINE_TOP_LEFT_CORNER)
    val BUILDING_ROOF_TOP_RIGHT = BUILDING_ROOF.withCharacter(Symbols.SINGLE_LINE_TOP_RIGHT_CORNER)
    val BUILDING_ROOF_BOT_LEFT = BUILDING_ROOF.withCharacter(Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER)
    val BUILDING_ROOF_BOT_RIGHT = BUILDING_ROOF.withCharacter(Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER)

    val BUILDING_WITH_FULL_WINDOWS_2X2_LEVEL = TextImageBuilder.newBuilder()
            .toCopy(arrayOf(
                    arrayOf(BUILDING_INSIDE, BUILDING_INSIDE),
                    arrayOf(BUILDING_INSIDE, BUILDING_INSIDE),
                    arrayOf(WALL_WITH_FULL_WINDOW, WALL_WITH_FULL_WINDOW)))
            .size(Size.of(2, 3))
            .build()

    val BUILDING_WITH_NARROW_WINDOWS_2X2_LEVEL = TextImageBuilder.newBuilder()
            .toCopy(arrayOf(
                    arrayOf(BUILDING_INSIDE, BUILDING_INSIDE),
                    arrayOf(BUILDING_INSIDE, BUILDING_INSIDE),
                    arrayOf(WALL_WITH_NARROW_WINDOW, WALL_WITH_NARROW_WINDOW)))
            .size(Size.of(2, 3))
            .build()

    val BUILDING_2X2_ROOF_0 = TextImageBuilder.newBuilder()
            .toCopy(arrayOf(
                    arrayOf(BUILDING_ROOF_TOP_LEFT, BUILDING_ROOF_TOP_RIGHT),
                    arrayOf(BUILDING_ROOF_BOT_LEFT, BUILDING_ROOF_BOT_RIGHT)))
            .size(Size.of(2, 3))
            .build()
    val BUILDING_2X2_ROOF_1 = TextImageBuilder.newBuilder()
            .toCopy(arrayOf(
                    arrayOf(BUILDING_ROOF, BUILDING_ROOF),
                    arrayOf(BUILDING_ROOF, BUILDING_ROOF)))
            .size(Size.of(2, 3))
            .build()

    val BASE_STYLE = StyleSetBuilder.newBuilder()
            .foregroundColor(TextColorFactory.fromString("#214100"))
            .backgroundColor(TextColorFactory.fromString("#4d6600"))
            .build()

    val SIMPLE_ANTENNA = TextCharacterBuilder.newBuilder()
            .backgroundColor(ROOF_COLOR)
            .foregroundColor(WALL_COLOR)
            .character(Symbols.ARROW_UP)
            .build()

    val BASE_4X4 = TextImageBuilder.newBuilder()
            .filler(TextCharacterBuilder.newBuilder()
                    .backgroundColor(BASE_STYLE.getBackgroundColor())
                    .foregroundColor(BASE_STYLE.getForegroundColor())
                    .build())
            .size(Size.of(4, 4))
            .build().apply {
        setStyleFrom(BASE_STYLE)
        setCharacterAt(Position.of(0, 0), Symbols.CLUB)
        setCharacterAt(Position.of(0, 2), '"')
        setCharacterAt(Position.of(1, 3), Symbols.SPADES)
        setCharacterAt(Position.of(3, 3), '"')
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val terminal = TerminalBuilder.newBuilder()
                .font(FONT)
                .initialTerminalSize(SIZE)
                .build()
        val screen = TerminalBuilder.createScreenFor(terminal)
        screen.setCursorVisibility(false) // we don't want the cursor right now

        val gamePanel = PanelBuilder.newBuilder()
                .size(screen.getBoundableSize().withColumns(60))
                .title("Game area")
                .wrapWithBox()
                .boxType(BoxType.TOP_BOTTOM_DOUBLE)
                .build()

        val gameAreaSize = Size3D.from2DSize(gamePanel.getBoundableSize()
                .minus(Size.of(2, 2)), VISIBLE_LEVEL_COUNT)


        val levels = HashMap<Int, List<TextImage>>()
        for (i in 0 until LEVEL_COUNT) {
            levels.put(i, listOf(TextImageBuilder.newBuilder()
                    .size(gameAreaSize.to2DSize())
                    .build()))
        }

        val gameArea = TextImageGameArea(Size3D.from2DSize(gameAreaSize.to2DSize(), LEVEL_COUNT), levels)

        val gameComponent = GameComponent(
                gameArea = gameArea,
                visibleSize = gameAreaSize,
                initialFont = CP437TilesetResource.PHOEBUS_16X16.toFont(),
                position = Position.DEFAULT_POSITION,
                componentStyles = ComponentStylesBuilder.DEFAULT,
                projectionMode = ProjectionMode.ISOMETRIC)
        screen.addComponent(gamePanel)
        gamePanel.addComponent(gameComponent)

        val level0 = levels[0]!![0]
        val floor = TextCharacterBuilder.newBuilder()
                .backgroundColor(TextColorFactory.fromString("#665233"))
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .build()
        level0.fetchCells().forEach { (position) -> level0.setCharacterAt(position, floor) }

        draw2x2Building(levels, Position.of(5, 6))
        draw2x2Building(levels, Position.of(6, 10))
        draw2x2Building(levels, Position.of(18, 21))
        draw2x2Building(levels, Position.of(26, 9))
        draw2x2Building(levels, Position.of(39, 15))
        draw2x2Building(levels, Position.of(48, 12))


        screen.applyColorTheme(ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme())
        screen.onInput(Consumer { input ->
            if (InputType.PageUp === input.getInputType()) {
                gameComponent.scrollOneUp()
            }
            if (InputType.PageDown === input.getInputType()) {
                gameComponent.scrollOneDown()
            }
            screen.drainLayers()
            val visibleOffset = gameComponent.getVisibleOffset()
            screen.pushLayer(LayerBuilder.newBuilder()
                    .textImage(TextCharacterStringBuilder.newBuilder()
                            .backgroundColor(TextColorFactory.TRANSPARENT)
                            .foregroundColor(TextColorFactory.fromString("#aaaadd"))
                            .text(String.format("Position: (x=%s, y=%s, z=%s)", visibleOffset.x, visibleOffset.y, visibleOffset.z))
                            .build()
                            .toTextImage())
                    .offset(Position.of(21, 1))
                    .build())
            screen.refresh()
        })
        screen.display()
    }

    private fun draw2x2Building(levels: Map<Int, List<TextImage>>, pos: Position) {
        val random = Random()
        var lastLevel = levels[0]!![0]
        lastLevel.draw(BASE_4X4, pos.withRelativeColumn(-1))
        val wall = if (random.nextInt(2) == 0) BUILDING_WITH_FULL_WINDOWS_2X2_LEVEL else BUILDING_WITH_NARROW_WINDOWS_2X2_LEVEL
        (0 until random.nextInt(7) + 1).forEach {
            val level = levels[it]!![0]
            level.draw(wall, pos)
            lastLevel = level
        }
        lastLevel.draw(if (random.nextInt(2) == 0) BUILDING_2X2_ROOF_0 else BUILDING_2X2_ROOF_1, pos)
        if(random.nextInt(2) == 1) {
            lastLevel.setCharacterAt(pos, SIMPLE_ANTENNA)
        }
    }
}