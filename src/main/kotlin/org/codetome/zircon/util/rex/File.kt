package org.codetome.zircon.util.rex

import java.nio.ByteBuffer
import java.nio.ByteOrder

data class File(val version: Int, val numOfLayers: Int, val layers: List<Layer>) {

    companion object {
        fun fromByteArray(data: ByteArray): File {
            val buffer = ByteBuffer.wrap(data)
            buffer.order(ByteOrder.LITTLE_ENDIAN)

            val version = buffer.int
            val numOfLayers = buffer.int

            val layers: MutableList<Layer> = mutableListOf()
            var currentLayer = 0
            while (currentLayer < numOfLayers) {
                layers.add(Layer.fromByteBuffer(buffer))
                currentLayer++
            }

            return File(version, numOfLayers, layers)
        }
    }
}
