@file:Suppress("MemberVisibilityCanPrivate")

package org.codetome.zircon.beta

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.Symbols
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.*
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.component.builder.GameComponentBuilder
import org.codetome.zircon.api.component.builder.PanelBuilder
import org.codetome.zircon.api.game.Position3D
import org.codetome.zircon.api.game.ProjectionMode
import org.codetome.zircon.api.game.Size3D
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.InputType
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.api.modifier.BorderType
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
    val FLOOR_COLOR = TextColorFactory.fromString("#999999")
    val LIGHT_COLOR = TextColorFactory.fromString("#ffff00")

    val TC = TextCharacterBuilder.newBuilder()
            .build()

    val SOUTH_WALL_WITH_FULL_WINDOW = TC.withCharacter(Symbols.SINGLE_LINE_HORIZONTAL)
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(ROOF_COLOR)
    val SOUTH_WALL_WITH_NARROW_WINDOW = TC.withCharacter('-')
            .withBackgroundColor(WALL_COLOR)
            .withForegroundColor(LIGHT_COLOR)
    val NORTH_WALL_WITH_NARROW_WINDOW = SOUTH_WALL_WITH_NARROW_WINDOW
            .withForegroundColor(ROOF_COLOR)
    val WEST_WALL = TC.withCharacter(' ')
            .withBackgroundColor(TextColorFactory.TRANSPARENT)
            .withForegroundColor(WALL_COLOR)
            .withModifiers(BorderBuilder.newBuilder()
                    .borderPositions(BorderPosition.LEFT)
                    .borderType(BorderType.SOLID)
                    .build())
    val EAST_WALL = WEST_WALL
            .withModifiers(BorderBuilder.newBuilder()
                    .borderPositions(BorderPosition.RIGHT)
                    .borderType(BorderType.SOLID)
                    .build())

    val BUILDING_FLOOR = TC.withCharacter(Symbols.BLOCK_SOLID)

            .withForegroundColor(FLOOR_COLOR)

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


    val BUILDING_3X3_FLOOR = TextImageBuilder.newBuilder()
            .toCopy(arrayOf(
                    arrayOf(BUILDING_FLOOR, BUILDING_FLOOR, BUILDING_FLOOR),
                    arrayOf(BUILDING_FLOOR, BUILDING_FLOOR, BUILDING_FLOOR),
                    arrayOf(BUILDING_FLOOR, BUILDING_FLOOR, BUILDING_FLOOR)))
            .size(Size.of(3, 3))
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

        val emptyLayer = TextImageBuilder.newBuilder()
                .size(gameAreaSize.to2DSize())
                .build()

        val ground = TextCharacterBuilder.newBuilder()
                .backgroundColor(TextColorFactory.fromString("#665233"))
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .build()

        val gameArea = GameAreaBuilder.newBuilder()
                .size(Size3D.from2DSize(gameAreaSize.to2DSize(), LEVEL_COUNT))
                .apply {
                    for (levelIdx in 0 until LEVEL_COUNT) {
                        setLevel(levelIdx, listOf(
                                emptyLayer.copyImage(),
                                emptyLayer.copyImage(),
                                emptyLayer.copyImage()))
                    }
                }
                .build()

        gameAreaSize.to2DSize().fetchPositions().forEach {
            gameArea.setCharacterAt(Position3D.from2DPosition(it), 0, ground)
        }

        val pos = Position.of(5, 9)
        val face = TextCharacterBuilder.newBuilder()
                .foregroundColor(TextColorFactory.fromString("#2288ff"))
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .character(Symbols.FACE_WHITE)
                .build()

        // level 0

        for (level in 0 until 6) {
            gameArea.getLayerAt(level, 0).draw(BUILDING_3X3_FLOOR, pos)
            gameArea.setCharacterAt(Position3D.from2DPosition(pos, level), 1, face)
            gameArea.setCharacterAt(Position3D.from2DPosition(pos
                    .withRelativeColumn(2)
                    .withRelativeRow(1), level), 1, face)
            gameArea.getLayerAt(level, 2).draw(TextImageBuilder.newBuilder()
                    .filler(SOUTH_WALL_WITH_NARROW_WINDOW)
                    .size(Size.of(3, 1))
                    .build(), pos.withRelativeRow(2))
            gameArea.getLayerAt(level, 2).draw(TextImageBuilder.newBuilder()
                    .filler(WEST_WALL)
                    .size(Size.of(1, 2))
                    .build(), pos)
            gameArea.getLayerAt(level, 2).draw(TextImageBuilder.newBuilder()
                    .filler(EAST_WALL)
                    .size(Size.of(1, 2))
                    .build(), pos.withRelativeColumn(2))
        }


        val gameComponent = GameComponentBuilder.newBuilder()
                .gameArea(gameArea)
                .visibleSize(gameAreaSize)
                .font(CP437TilesetResource.PHOEBUS_16X16.toFont())
                .projectionMode(ProjectionMode.ISOMETRIC)
                .build()

        screen.addComponent(gamePanel)
        gamePanel.addComponent(gameComponent)

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
}