package org.hexworks.zircon.examples

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.Blocks
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Layers
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.Tiles
import org.hexworks.zircon.api.builder.game.GameAreaBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.mvc.base.BaseView
import org.hexworks.zircon.api.shape.LineFactory
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Processed

// TODO: not working
object DrawingExample {

    private const val TOOLS_WIDTH = 15
    private const val LAYERS = 5
    private val TEST_TILE = Tiles.newBuilder()
            .withBackgroundColor(ANSITileColor.GREEN)
            .withForegroundColor(ANSITileColor.RED)
            .withCharacter(Symbols.DELTA)
            .buildCharacterTile()
    private val EMPTY_TILE = Tiles.empty()
    private val BLACK_TILE = Tiles.newBuilder()
            .withBackgroundColor(ANSITileColor.BLACK)
            .buildCharacterTile()
    private val EMPTY_BLOCK = Blocks.newBuilder<Tile>()
            .withEmptyTile(EMPTY_TILE)
            .withLayers(EMPTY_TILE)
            .build()
    private val BLACK_BLOCK = Blocks.newBuilder<Tile>()
            .withEmptyTile(BLACK_TILE)
            .withLayers(BLACK_TILE)
            .build()

    @JvmStatic
    fun main(args: Array<String>) {

        val app = SwingApplications.startApplication(AppConfigs.newConfig()
                .enableBetaFeatures()
                .build())

        val size = Sizes.create3DSize(
                xLength = app.tileGrid.width - 2 - TOOLS_WIDTH,
                yLength = app.tileGrid.height - 2,
                zLength = LAYERS)

        app.dock(DrawView(DrawController(size)))
    }

    data class Context(val chosenLayer: Layer,
                       val gameAreaOffset: Position,
                       val layerLevel: Int)

    interface DrawCommand {

        fun execute(context: Context, gameArea: GameArea<Tile, Block<Tile>>, mouseAction: MouseEvent)
    }

    class FreeDrawCommand : DrawCommand {
        override fun execute(context: Context, gameArea: GameArea<Tile, Block<Tile>>, mouseAction: MouseEvent) {
            val (layer, offset) = context
            if (mouseAction.type == MouseEventType.MOUSE_RELEASED) {
                layer.draw(TEST_TILE, mouseAction.position - offset)
            }
        }

    }

    class DrawLineCommand : DrawCommand {

        private var maybePressed = Maybe.empty<Position>()
        private var maybeTemp = Maybe.empty<Layer>()

        override fun execute(context: Context, gameArea: GameArea<Tile, Block<Tile>>, mouseAction: MouseEvent) {
            val (layer, offset, level) = context
            when (mouseAction.type) {
                MouseEventType.MOUSE_PRESSED -> {
                    val temp = Layers.newBuilder()
                            .withSize(layer.size)
                            .build()
                    maybePressed = Maybe.of(mouseAction.position - context.gameAreaOffset)
                    maybeTemp = Maybe.of(temp)
                    gameArea.pushOverlayAt(temp, level)
                }
                MouseEventType.MOUSE_RELEASED -> {
                    maybePressed.map { pressedAt ->
                        val tempLayer = maybeTemp.get()
                        val pos = mouseAction.position - offset
                        if (pos != pressedAt) {
                            tempLayer.clear()
                            LineFactory.buildLine(pressedAt, pos).positions.forEach {
                                tempLayer.draw(TEST_TILE, it)
                            }
                            layer.draw(tempLayer)
                        }
                        gameArea.removeOverlay(tempLayer, level)
                    }
                }
                MouseEventType.MOUSE_DRAGGED -> {
                    maybePressed.map { pressedAt ->
                        val tempLayer = maybeTemp.get()
                        val pos = mouseAction.position - offset
                        if (pos != pressedAt) {
                            tempLayer.clear()
                            LineFactory.buildLine(pressedAt, pos).positions.forEach {
                                tempLayer.draw(TEST_TILE, it)
                            }
                        }
                    }
                }
                else -> {
                }
            }
        }

    }

    @Suppress("unused")
    enum class DrawOption(val drawCommand: DrawCommand) {
        FREE(FreeDrawCommand()),
        LINE(DrawLineCommand());
    }

    class DrawController(drawAreaSize: Size3D) {
        val gameArea = GameAreaBuilder<Tile, Block<Tile>>()
                .withActualSize(drawAreaSize)
                .withVisibleSize(drawAreaSize)
                .withLayersPerBlock(1)
                .withDefaultBlock(EMPTY_BLOCK)
                .build()

        private val overlays = (0 until LAYERS).map {
            it to Layers.newBuilder().withSize(drawAreaSize.to2DSize()).build()
        }.toMap()
        private var chosenCommand = DrawOption.FREE.drawCommand
        private var chosenLayer = 0 to overlays.getValue(0)

        init {
            gameArea.actualSize.to2DSize().fetchPositions().forEach {
                gameArea.setBlockAt(Positions.from2DTo3D(it), BLACK_BLOCK)
            }
            gameArea.pushOverlayAt(chosenLayer.second, chosenLayer.first)
        }

        fun chooseLayer(idx: Int) {
            gameArea.popOverlayAt(chosenLayer.first)
            chosenLayer = idx to overlays.getValue(idx)
            gameArea.pushOverlayAt(chosenLayer.second, chosenLayer.first)
        }

        fun useChosenTool(mouseAction: MouseEvent) {
            chosenCommand.execute(
                    context = Context(
                            chosenLayer = chosenLayer.second,
                            gameAreaOffset = Positions.create(TOOLS_WIDTH + 1, 1),
                            layerLevel = chosenLayer.first),
                    gameArea = gameArea,
                    mouseAction = mouseAction)
        }

        fun chooseTool(tool: DrawOption) {
            chosenCommand = tool.drawCommand
        }
    }

    class DrawView(private val controller: DrawController) : BaseView() {

        override val theme = ColorThemes.arc()

        override fun onDock() {

            val tools = Components.panel()
                    .withSize(TOOLS_WIDTH, 4)
                    .withDecorations(box(title = "Tools"))
                    .build().apply {
                        val chooseTool = Components.radioButtonGroup()
                                .withSize(TOOLS_WIDTH - 2, 2)
                                .build().apply {
                                    DrawOption.values().forEachIndexed { index, drawOption ->
                                        val opt = addOption(drawOption.name, drawOption.name)
                                        if (index == 0) {
                                            opt.isSelected = true
                                        }
                                    }
                                    onSelection {
                                        controller.chooseTool(DrawOption.valueOf(it.key))
                                    }
                                }
                        addComponent(chooseTool)
                    }

            val layers = Components.panel()
                    .withSize(TOOLS_WIDTH, LAYERS + 2)
                    .withDecorations(box(title = "Layers"))
                    .withPosition(0, tools.height)
                    .build().apply {
                        val chooseLayer = Components.radioButtonGroup()
                                .withSize(TOOLS_WIDTH - 2, LAYERS)
                                .build().apply {
                                    repeat(LAYERS) { idx ->
                                        val opt = addOption(idx.toString(), idx.toString())
                                        if (idx == 0) {
                                            opt.isSelected = true
                                        }
                                    }
                                    onSelection {
                                        controller.chooseLayer(it.key.toInt())
                                    }
                                }
                        addComponent(chooseLayer)
                    }

            val drawArea = Components.panel()
                    .withSize(screen.size.withRelativeWidth(-TOOLS_WIDTH))
                    .withPosition(TOOLS_WIDTH, 0)
                    .withDecorations(box(title = ("Draw Surface")))
                    .build().apply {
                        val gc = Components.gameComponent<Tile, Block<Tile>>()
                                .withSize(contentSize)
                                .withVisibleSize(Sizes.from2DTo3D(contentSize))
                                .withGameArea(controller.gameArea)
                                .build().apply {
                                    handleMouseEvents(MouseEventType.MOUSE_RELEASED) { event, _ ->
                                        controller.useChosenTool(event)
                                        Processed
                                    }
                                }
                        addComponent(gc)
                    }


            screen.addComponent(tools)
            screen.addComponent(layers)
            screen.addComponent(drawArea)
        }
    }
}
