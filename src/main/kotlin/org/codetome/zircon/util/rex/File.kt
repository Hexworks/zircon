package org.codetome.zircon.util.rex

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represents a REX Paint File, which contains version and [Layer] information.
 */
data class File(private val version: Int,
                private val numberOfLayers: Int,
                private val layers: List<Layer>) {

    fun getVersion() = version

    fun getNumberOfLayers() = numberOfLayers

    fun getLayers() = layers

    companion object {

        /**
         * Factory method for [File]. It takes an uncompressed [ByteArray] argument and reads out the REX Paint [File]
         * object as defined in the <a href="http://www.gridsagegames.com/rexpaint/manual.txt">REX Paint manual</a>.
         */
        fun fromByteArray(data: ByteArray): File {
            val buffer = ByteBuffer.wrap(data)
            buffer.order(ByteOrder.LITTLE_ENDIAN)

            val version = buffer.int
            val numberOfLayers = buffer.int

            val layers: MutableList<Layer> = mutableListOf()
            for (i in 0 until numberOfLayers) {
                layers.add(Layer.fromByteBuffer(buffer))
            }

            return File(version, numberOfLayers, layers)
        }
    }
}
