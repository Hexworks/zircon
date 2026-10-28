package org.hexworks.zircon.internal.util.io

/**
 * Sequential little-endian reader over a [ByteArray].
 */
internal class ByteReader(private val data: ByteArray, var position: Int = 0) {

    val remaining: Int get() = data.size - position

    fun readUByte(): Int {
        check(position < data.size) { "Unexpected end of data at $position" }
        return data[position++].toInt() and 0xFF
    }

    fun readUShortLE(): Int = readUByte() or (readUByte() shl 8)

    fun readIntLE(): Int = readUShortLE() or (readUShortLE() shl 16)

    fun readBytes(count: Int): ByteArray {
        check(position + count <= data.size) { "Unexpected end of data: need $count bytes at $position" }
        return data.copyOfRange(position, position + count).also { position += count }
    }

    fun skip(count: Int) {
        check(position + count <= data.size) { "Unexpected end of data: can't skip $count bytes at $position" }
        position += count
    }

    /** Reads a NUL-terminated byte string (the terminator is consumed, not returned). */
    fun readCString(): ByteArray {
        val start = position
        while (readUByte() != 0) Unit
        return data.copyOfRange(start, position - 1)
    }
}
