package org.codetome.zircon.jvm.internal.util.rex

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Represents a REX Paint File, which contains version and [REXLayer] information.
 */
data class REXFile(private val version: Int,
                   private val numberOfLayers: Int,
                   private val layers: List<REXLayer>) {

    fun getVersion() = version

    fun getNumberOfLayers() = numberOfLayers

    fun getLayers() = layers

    companion object {

        /**
         * Factory method for [REXFile]. It takes an uncompressed [ByteArray] argument and reads out the REX Paint
         * [REXFile] object as defined in the <a href="http://www.gridsagegames.com/rexpaint/manual.txt">REX
         * Paint manual</a>.
         */
        fun fromByteArray(data: ByteArray): REXFile {
            val buffer = ByteBuffer.wrap(data)
            buffer.order(ByteOrder.LITTLE_ENDIAN)

            val version = buffer.int
            val numberOfLayers = buffer.int

            val layers: MutableList<REXLayer> = mutableListOf()
            for (i in 0 until numberOfLayers) {
                layers.add(REXLayer.fromByteBuffer(buffer))
            }

            return REXFile(version, numberOfLayers, layers)
        }
    }
}
