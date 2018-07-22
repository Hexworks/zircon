package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.api.builder.graphics.TextImageBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.game.*
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.util.Math
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.Scrollable3D
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultScrollable3D
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus

/**
 * Note that this class is in **BETA**!
 * It's API is subject to change!
 */
class DefaultGameComponent(private val gameArea: GameArea,
                           private val projectionMode: ProjectionMode = ProjectionMode.TOP_DOWN,
                           visibleSize: Size3D,
                           initialFont: Font,
                           position: Position,
                           componentStyleSet: ComponentStyleSet,
                           boundable: DefaultBoundable = DefaultBoundable(
                                   size = visibleSize.to2DSize(),
                                   position = position),
                           private val scrollable: Scrollable3D = DefaultScrollable3D(
                                   visibleSpaceSize = visibleSize,
                                   virtualSpaceSize = gameArea.getSize()))

    : GameComponent, Scrollable3D by scrollable, DefaultComponent(
        initialSize = visibleSize.to2DSize(),
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = listOf(),
        initialFont = initialFont,
        boundable = boundable) {

    private val visibleLevelCount = visibleSize.zLength

    init {
        refreshVirtualSpaceSize()
    }

    override fun acceptsFocus(): Boolean {
        return true
    }

    override fun giveFocus(input: Maybe<Input>): Boolean {
        refreshVirtualSpaceSize()
        EventBus.broadcast(Event.ComponentChange)
        return true
    }

    override fun takeFocus(input: Maybe<Input>) {
    }

    override fun applyColorTheme(colorTheme: ColorTheme) {
    }

    override fun transformToLayers(): List<Layer> {
        val height = scrollable.getVirtualSpaceSize().zLength
        val fromZ = scrollable.getVisibleOffset().z
        val screenSize = getVisibleSpaceSize().to2DSize()

        val result = mutableListOf<Layer>()

        if (projectionMode == ProjectionMode.TOP_DOWN) {
            (fromZ until Math.min(fromZ + visibleLevelCount, height)).forEach { levelIdx ->
                val segment = gameArea.fetchLayersAt(
                        offset = Position3D.from2DPosition(getVisibleOffset().to2DPosition(), levelIdx),
                        size = Size3D.from2DSize(getBoundableSize(), 1))
                segment.forEach {
                    result.add(LayerBuilder.newBuilder()
                            .textImage(it)
                            .offset(getPosition())
                            .build())
                }
            }
        } else {
            val fixedLayerCount = 4
            val customLayersPerBlock = gameArea.getLayersPerBlock()
            val totalLayerCount = fixedLayerCount + customLayersPerBlock
            val builders = (0 until totalLayerCount * height).map {
                TextImageBuilder.newBuilder().size(screenSize)
            }
            val (fromX, fromY) = getVisibleOffset().to2DPosition()
            val toX = fromX + getBoundableSize().xLength
            val toY = fromY + getBoundableSize().yLength
            (fromZ until Math.min(fromZ + visibleLevelCount, height)).forEach { z ->
                (fromY until toY).forEach { screenY ->
                    (fromX until toX).forEach { x ->
                        val y = screenY + z // we need to add `z` to `y` because of isometric
                        val maybeBlock: Maybe<Block> = gameArea.fetchBlockAt(Position3D.create(x, y, z))
                        val maybeNext = gameArea.fetchBlockAt(Position3D.create(x, y + 1, z))
                        val screenPos = Position.create(x, screenY)
                        val bottomIdx = z * totalLayerCount
                        val frondIdx = bottomIdx + customLayersPerBlock + 1
                        val backIdx = frondIdx + 1
                        val topIdx = backIdx + 1
                        maybeBlock.ifPresent { block ->
                            val bot = block.bottom
                            val layers = block.layers
                            val front = block.front

                            builders[bottomIdx].character(screenPos, bot)
                            layers.forEachIndexed { idx, layer ->
                                builders[bottomIdx + idx + 1].character(screenPos, layer)
                            }
                            builders[frondIdx].character(screenPos, front)
                        }
                        maybeNext.ifPresent { block ->
                            val back = block.back
                            val top = block.top
                            builders[backIdx].character(screenPos, back)
                            builders[topIdx].character(screenPos, top)
                        }
                    }
                }
            }
            builders.forEach {
                result.add(LayerBuilder.newBuilder().textImage(it.build()).build())
            }
        }

        return result
    }

    private fun refreshVirtualSpaceSize() {
        setVirtualSpaceSize(gameArea.getSize())
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return getBoundable().containsBoundable(boundable)
    }

    override fun containsPosition(position: Position): Boolean {
        return getBoundable().containsPosition(position)
    }

    override fun getBoundableSize(): Size {
        return getBoundable().getBoundableSize()
    }

    override fun getPosition(): Position {
        return getBoundable().getPosition()
    }

    override fun intersects(boundable: Boundable): Boolean {
        return getBoundable().intersects(boundable)
    }
}
