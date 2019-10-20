package org.hexworks.zircon.internal.game

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicsBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.LayerState
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.impl.Position3D
import org.hexworks.zircon.api.data.impl.Size3D
import org.hexworks.zircon.api.game.GameArea
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.game.ProjectionMode
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.component.impl.DefaultContainer
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import kotlin.jvm.Synchronized
import kotlin.math.min

class DefaultGameComponent<T : Tile, B : Block<T>>(
        componentMetadata: ComponentMetadata,
        private val gameArea: GameArea<T, B>,
        private val projectionMode: ProjectionMode = ProjectionMode.TOP_DOWN)
    : GameComponent<T, B>, DefaultContainer(
        componentMetadata = componentMetadata,
        renderer = DefaultComponentRenderingStrategy(
                decorationRenderers = listOf(),
                componentRenderer = NoOpComponentRenderer())) {

    private val visibleLevelCount = gameArea.visibleSize.zLength

    override val children: List<InternalComponent>
        @Synchronized
        get() = super.children

    override val descendants: Iterable<InternalComponent>
        @Synchronized
        get() {
            return children.flatMap { listOf(it).plus(it.descendants) }
        }

    override fun acceptsFocus() = true

    override fun focusGiven() = Processed

    override fun focusTaken() = Processed

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSet.defaultStyleSet()
    }

    fun layers(): List<LayerState> {
        val height = gameArea.actualSize.zLength
        val fromZ = gameArea.visibleOffset.z
        val screenSize = gameArea.visibleSize.to2DSize()

        val result = mutableListOf<Layer>()

//        if (projectionMode == ProjectionMode.TOP_DOWN) {
//            (fromZ until min(fromZ + visibleLevelCount, height)).forEach { levelIdx ->
//                val segment = gameArea.fetchLayersAt(
//                        offset = Position3D.from2DPosition(gameArea.visibleOffset.to2DPosition(), levelIdx),
//                        size = Size3D.from2DSize(size, 1))
//                segment.forEach {
//                    result.add(LayerBuilder.newBuilder()
//                            .withTileGraphics(it)
//                            // TODO: regression test this: position vs absolutePosition
//                            .withOffset(relativePosition)
//                            .build())
//                }
//            }
//        } else {
//            val fixedLayerCount = 4
//            val customLayersPerBlock = gameArea.layersPerBlock()
//            val totalLayerCount = fixedLayerCount + customLayersPerBlock
//            val builders = (0 until totalLayerCount * height).map {
//                TileGraphicsBuilder.newBuilder().withSize(screenSize)
//            }
//            val (fromX, fromY) = gameArea.visibleOffset.to2DPosition()
//            val toX = fromX + size.width
//            val toY = fromY + size.height
//            (fromZ until min(fromZ + visibleLevelCount, height)).forEach { z ->
//                (fromY until toY).forEach { screenY ->
//                    (fromX until toX).forEach { x ->
//                        val y = screenY + z // we need to add `z` to `y` because of isometric
//                        val maybeBlock: Maybe<out Block<T>> = gameArea.fetchBlockAt(Position3D.create(x, y, z))
//                        val maybeNext = gameArea.fetchBlockAt(Position3D.create(x, y + 1, z))
//                        val screenPos = Position.create(x, screenY)
//                        val bottomIdx = z * totalLayerCount
//                        val frondIdx = bottomIdx + customLayersPerBlock + 1
//                        val backIdx = frondIdx + 1
//                        val topIdx = backIdx + 1
//                        maybeBlock.ifPresent { block ->
//                            val bot = block.bottom
//                            val layers = block.layers
//                            val front = block.front
//
//                            builders[bottomIdx].withTile(screenPos, bot)
//                            layers.forEachIndexed { idx, layer ->
//                                builders[bottomIdx + idx + 1].withTile(screenPos, layer)
//                            }
//                            builders[frondIdx].withTile(screenPos, front)
//                        }
//                        maybeNext.ifPresent { block ->
//                            val back = block.back
//                            val top = block.top
//                            builders[backIdx].withTile(screenPos, back)
//                            builders[topIdx].withTile(screenPos, top)
//                        }
//                    }
//                }
//            }
//            builders.forEach {
//                result.add(LayerBuilder.newBuilder()
//                        .withTileGraphics(it.build())
//                        .withOffset(relativePosition)
//                        .build())
//            }
//        }
        // TODO: fix
        return result.map { it.state }
    }

    override fun render() {
        // no-op
    }
}
