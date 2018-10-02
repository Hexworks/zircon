package org.hexworks.zircon.internal.game

import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.kotlin.ifPresent
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.Scrollable3D
import org.hexworks.zircon.internal.behavior.impl.DefaultScrollable3D
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
class DefaultGameComponent(private val gameArea: GameArea,
                           private val projectionMode: ProjectionMode = ProjectionMode.TOP_DOWN,
                           position: Position,
                           size: Size3D,
                           tileset: TilesetResource,
                           componentStyleSet: ComponentStyleSet,
                           private val scrollable: Scrollable3D = DefaultScrollable3D(
                                   visibleSpaceSize = size,
                                   virtualSpaceSize = gameArea.size))

    : GameComponent,
        Scrollable3D by scrollable,
        DefaultComponent(
                position = position,
                size = size.to2DSize(),
                componentStyles = componentStyleSet,
                tileset = tileset,
                renderer = DefaultComponentRenderingStrategy(
                        decorationRenderers = listOf(),
                        componentRenderer = NoOpComponentRenderer())) {

    private val visibleLevelCount = size.zLength

    init {
        refreshVirtualSpaceSize()
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        refreshVirtualSpaceSize()
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSet.defaultStyleSet()
    }

    override fun transformToLayers(): List<Layer> {
        val height = scrollable.actualSize().zLength
        val fromZ = scrollable.visibleOffset().z
        val screenSize = visibleSize().to2DSize()

        val result = mutableListOf<Layer>()

        if (projectionMode == ProjectionMode.TOP_DOWN) {
            (fromZ until Math.min(fromZ + visibleLevelCount, height)).forEach { levelIdx ->
                val segment = gameArea.fetchLayersAt(
                        offset = Position3D.from2DPosition(visibleOffset().to2DPosition(), levelIdx),
                        size = Size3D.from2DSize(size, 1))
                segment.forEach {
                    result.add(LayerBuilder.newBuilder()
                            .tileGraphic(it)
                            .offset(position)
                            .build())
                }
            }
        } else {
            val fixedLayerCount = 4
            val customLayersPerBlock = gameArea.layersPerBlock()
            val totalLayerCount = fixedLayerCount + customLayersPerBlock
            val builders = (0 until totalLayerCount * height).map {
                TileGraphicsBuilder.newBuilder().size(screenSize)
            }
            val (fromX, fromY) = visibleOffset().to2DPosition()
            val toX = fromX + size.xLength
            val toY = fromY + size.yLength
            (fromZ until Math.min(fromZ + visibleLevelCount, height)).forEach { z ->
                (fromY until toY).forEach { screenY ->
                    (fromX until toX).forEach { x ->
                        val y = screenY + z // we need to add `z` to `y` because of isometric
                        val maybeBlock: Maybe<out Block> = gameArea.fetchBlockAt(Position3D.create(x, y, z))
                        val maybeNext = gameArea.fetchBlockAt(Position3D.create(x, y + 1, z))
                        val screenPos = Position.create(x, screenY)
                        val bottomIdx = z * totalLayerCount
                        val frondIdx = bottomIdx + customLayersPerBlock + 1
                        val backIdx = frondIdx + 1
                        val topIdx = backIdx + 1
                        maybeBlock.ifPresent { block ->
                            val bot = block.bottom()
                            val layers = block.layers
                            val front = block.front()

                            builders[bottomIdx].tile(screenPos, bot)
                            layers.forEachIndexed { idx, layer ->
                                builders[bottomIdx + idx + 1].tile(screenPos, layer)
                            }
                            builders[frondIdx].tile(screenPos, front)
                        }
                        maybeNext.ifPresent { block ->
                            val back = block.back()
                            val top = block.top()
                            builders[backIdx].tile(screenPos, back)
                            builders[topIdx].tile(screenPos, top)
                        }
                    }
                }
            }
            builders.forEach {
                result.add(LayerBuilder.newBuilder().tileGraphic(it.build()).build())
            }
        }
        return result
    }

    private fun refreshVirtualSpaceSize() {
        setActualSize(gameArea.size)
    }

    override fun render() {
        // no-op
    }
}
