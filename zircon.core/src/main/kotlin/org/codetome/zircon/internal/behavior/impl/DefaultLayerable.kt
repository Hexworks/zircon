package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.internal.behavior.Dirtiable
import org.codetome.zircon.internal.behavior.InternalLayerable
import org.codetome.zircon.internal.util.ThreadSafeQueue
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultLayerable(private val supportedFontSize: Size,
                       size: Size,
                       private val boundable: Boundable = DefaultBoundable(size),
                       private val dirtiable: Dirtiable = DefaultDirtiable())
    : InternalLayerable, Boundable by boundable, Dirtiable by dirtiable {

    private val layers = ThreadSafeQueueFactory.create<Layer>()

    override fun getSupportedFontSize() = supportedFontSize

    override fun pushLayer(layer: Layer) {
        if (layer.hasOverrideFont()) {
            require(getSupportedFontSize() == layer.getCurrentFont().getSize()) {
                "Can't add Layer to Layerable with unsupported tileset size! Supported size: " +
                        "${getSupportedFontSize()}, layer tileset size: ${layer.getCurrentFont().getSize()}"
            }
        }
        layer.moveTo(layer.getPosition() + boundable.getPosition())
        layers.offer(layer)
        markLayerPositionsDirty(layer)
    }

    // TODO: regression test this!
    override fun popLayer() = layers.pollLast().also {
        it.map { markLayerPositionsDirty(it) }
    }

    override fun removeLayer(layer: Layer) {
        layers.remove(layer)
        markLayerPositionsDirty(layer)
    }

    override fun getLayers() = layers.toList()

    override fun drainLayers() = mutableListOf<Layer>().also {
        layers.drainTo(it)
        it.forEach {
            markLayerPositionsDirty(it)
        }
    }

    override fun fetchOverlayZIntersection(absolutePosition: Position): List<Pair<Tileset, Tile>> {
        return fetchZIntersectionFor(layers, absolutePosition)
    }

    private fun fetchZIntersectionFor(queue: ThreadSafeQueue<Layer>, position: Position): List<Pair<Tileset, Tile>> {
        return queue.filter { layer ->
            // TODO: optimize based on non-transparent backgrounds
            layer.containsPosition(position)
        }.map { layer ->
            Pair(layer.getCurrentFont(), layer.getCharacterAt(position).get())
        }
    }

    private fun markLayerPositionsDirty(layer: Layer) {
        layer.fetchFilledPositions().forEach {
            setPositionDirty(it)
        }
    }
}
