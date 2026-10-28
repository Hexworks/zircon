package org.hexworks.zircon.internal.util.compression

import org.hexworks.zircon.internal.util.io.ByteReader

/**
 * gzip (RFC 1952) container around [Inflate]. The trailer's CRC is not verified; the
 * stored uncompressed size is used to pre-size the output and to sanity-check it.
 */
internal object Gzip {

    fun isGzip(data: ByteArray): Boolean =
        data.size >= 2 && data[0] == 0x1f.toByte() && data[1] == 0x8b.toByte()

    fun decompress(data: ByteArray): ByteArray {
        val reader = ByteReader(data)
        check(reader.readUByte() == 0x1f && reader.readUByte() == 0x8b) { "Not a gzip stream" }
        check(reader.readUByte() == 8) { "Unsupported gzip compression method" }
        val flags = reader.readUByte()
        reader.skip(6) // mtime, xfl, os
        if (flags and FEXTRA != 0) reader.skip(reader.readUShortLE())
        if (flags and FNAME != 0) reader.readCString()
        if (flags and FCOMMENT != 0) reader.readCString()
        if (flags and FHCRC != 0) reader.skip(2)

        val trailer = ByteReader(data, data.size - 8)
        trailer.skip(4) // crc32
        val size = trailer.readIntLE()
        val result = Inflate.inflate(data, reader.position, sizeHint = size)
        check(size < 0 || result.size == size) { "gzip size mismatch: expected $size, got ${result.size}" }
        return result
    }

    private const val FHCRC = 0x02
    private const val FEXTRA = 0x04
    private const val FNAME = 0x08
    private const val FCOMMENT = 0x10
}
