package org.hexworks.zircon.internal.grid

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.LayerHandle
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.internal.animation.DefaultAnimationRunner
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.graphics.InternalLayer
import org.hexworks.zircon.internal.graphics.Renderable
import org.hexworks.zircon.internal.uievent.UIEventProcessor

class DefaultTileGrid(
    override val config: AppConfig,
    override var layerable: InternalLayerable = buildLayerable(config.size),
    override var animationHandler: InternalAnimationRunner = DefaultAnimationRunner(),
    override var cursorHandler: InternalCursorHandler = DefaultCursorHandler(
        initialCursorSpace = config.size
    ),
    private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault()
) : InternalTileGrid,
    ShutdownHook by DefaultShutdownHook(),
    UIEventProcessor by eventProcessor,
    ViewContainer by ViewContainer.create() {

    init {
        initializeLayerable(config)
    }

    // Note that this is passed in right after creation
    // the lateinit is necessary because Application also has a reference to the grid
    override lateinit var application: InternalApplication

    override var backend: Layer = layerable.getLayerAtOrNull(0)!!

    override val tiles: Map<Position, Tile>
        get() = backend.tiles

    override val size: Size
        get() = backend.size

    override var tileset: TilesetResource
        get() = backend.tileset
        set(value) {
            backend.tileset = value
        }

    override val tilesetProperty: Property<TilesetResource>
        get() = backend.tilesetProperty

    override val layers: ObservableList<out InternalLayer>
        get() = layerable.layers

    override val renderables: List<Renderable>
        get() = layerable.renderables

    private var originalCursorHandler = cursorHandler
    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override var isCursorVisible: Boolean
        get() = cursorHandler.isCursorVisible
        set(value) {
            cursorHandler.isCursorVisible = value
        }
    override var cursorPosition: Position
        get() = cursorHandler.cursorPosition
        set(value) {
            cursorHandler.cursorPosition = value
        }
    override val isCursorAtTheEndOfTheLine: Boolean
        get() = cursorHandler.isCursorAtTheEndOfTheLine
    override val isCursorAtTheStartOfTheLine: Boolean
        get() = cursorHandler.isCursorAtTheStartOfTheLine
    override val isCursorAtTheFirstRow: Boolean
        get() = cursorHandler.isCursorAtTheFirstRow
    override val isCursorAtTheLastRow: Boolean
        get() = cursorHandler.isCursorAtTheLastRow

    override val closedValue: Property<Boolean> = false.toProperty()
    override val closed: Boolean by closedValue.asDelegate()

    override fun getTileAtOrNull(position: Position): Tile? {
        return backend.getTileAtOrNull(position)
    }

    override fun putTile(tile: Tile) {
        if (tile is CharacterTile && tile.character == '\n') {
            moveCursorToNextLine()
        } else {
            backend.draw(tile, cursorPosition)
            moveCursorForward()
        }
    }

    override fun moveCursorForward() {
        cursorHandler.moveCursorForward()
    }

    override fun moveCursorBackward() {
        cursorHandler.moveCursorBackward()
    }

    override fun close() {
        animationHandler.close()
        closedValue.value = true
    }

    override fun delegateTo(tileGrid: InternalTileGrid) {
        backend = tileGrid.backend
        layerable = tileGrid.layerable
        animationHandler = tileGrid.animationHandler
        cursorHandler = tileGrid.cursorHandler
    }

    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
        animationHandler = originalAnimationHandler
        cursorHandler = originalCursorHandler
    }

    override fun clear() {
        backend.clear()
        layerable = buildLayerable(size)
        initializeLayerable(config)
        backend = layerable.getLayerAtOrNull(0)!!
    }

    // ANIMATION HANDLER

    override fun start(animation: Animation): AnimationHandle {
        return animationHandler.start(animation)
    }

    override fun stop(animation: InternalAnimation) {
        animationHandler.stop(animation)
    }

    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        animationHandler.updateAnimations(currentTimeMs, layerable)
    }

    // DRAW SURFACE

    override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        backend.draw(tileComposite, drawPosition, drawArea)
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition, drawArea)
    }

    override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition)
    }

    override fun draw(tileComposite: TileComposite) {
        backend.draw(tileComposite)
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        backend.draw(tileComposite, drawPosition)
    }

    override fun draw(tileMap: Map<Position, Tile>) {
        backend.draw(tileMap)
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        backend.draw(tileMap, drawPosition)
    }

    override fun fill(filler: Tile) {
        backend.fill(filler)
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        backend.transform(transformer)
    }

    override fun applyStyle(styleSet: StyleSet) {
        backend.applyStyle(styleSet)
    }

    // LAYERABLE

    override fun getLayerAtOrNull(level: Int): LayerHandle? = layerable.getLayerAtOrNull(level)

    override fun addLayer(layer: Layer) = layerable.addLayer(layer)

    override fun insertLayerAt(level: Int, layer: Layer) = layerable.insertLayerAt(level, layer)

    override fun setLayerAt(level: Int, layer: Layer) = layerable.setLayerAt(level, layer)

    override fun removeLayer(layer: Layer): Boolean {
        return layerable.removeLayer(layer)
    }

    private fun moveCursorToNextLine() {
        cursorPosition = cursorPosition.withRelativeY(1).withX(0)
    }

    private fun initializeLayerable(config: AppConfig) {
        layerable.addLayer(
            Layer.newBuilder()
                .withSize(config.size)
                .withTileset(config.defaultTileset)
                .build()
        )
    }
}

private fun buildLayerable(initialSize: Size): DefaultLayerable {
    return DefaultLayerable(
        initialSize = initialSize
    )
}
