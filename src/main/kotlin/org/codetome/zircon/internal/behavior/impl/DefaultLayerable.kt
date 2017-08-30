package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.InternalLayerable
import org.codetome.zircon.internal.behavior.Dirtiable
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue

class DefaultLayerable private constructor(boundable: Boundable,
                                           dirtiable: Dirtiable)
    : InternalLayerable, Boundable by boundable, Dirtiable by dirtiable {

    constructor(size: Size) : this(
            boundable = DefaultBoundable(size),
            dirtiable = DefaultDirtiable())

    private val layers: BlockingQueue<Layer> = LinkedBlockingQueue()

    override fun addLayer(layer: Layer) {
        layers.add(layer)
        markLayerPositionsDirty(layer)
    }

    override fun popLayer() = Optional.ofNullable(layers.poll()).also {
        it.map { markLayerPositionsDirty(it) }
    }

    override fun removeLayer(layer: Layer) {
        layers.remove(layer)
        markLayerPositionsDirty(layer)
    }

    override fun getLayers() = layers.toList()

    override fun drainLayers() = mutableListOf<Layer>().also {
        layers.drainTo(it)
        it.forEach{
            markLayerPositionsDirty(it)
        }
    }

    override fun fetchOverlayZIntersection(absolutePosition: Position): List<TextCharacter> {
        return fetchZIntersectionFor(layers, absolutePosition)
    }

    private fun fetchZIntersectionFor(queue: Queue<Layer>, position: Position): List<TextCharacter> {
        return queue.filter { layer ->
            layer.containsPosition(position)
        }.map { layer ->
            layer.getCharacterAt(position).get()
        }
    }

    private fun markLayerPositionsDirty(layer: Layer) {
        layer.fetchPositions().forEach {
            setPositionDirty(it)
        }
    }
}