package org.hexworks.zircon.internal.util.compression

import org.hexworks.zircon.internal.util.io.ByteReader

/**
 * Read-only ZIP archive over a [ByteArray]. Entries are located through the central
 * directory and inflated on demand. Supports the stored and deflate methods, which is
 * what every tool writes by default; no ZIP64, no encryption.
 */
internal class ZipArchive(private val data: ByteArray) {

    private val entries: Map<String, Entry> = readCentralDirectory()

    val names: Set<String> get() = entries.keys

    fun contains(name: String): Boolean = name in entries

    fun read(name: String): ByteArray {
        val entry = entries[name] ?: error("No entry '$name' in zip archive")
        val reader = ByteReader(data, entry.localHeaderOffset)
        check(reader.readIntLE() == LOCAL_HEADER) { "Bad local header for '$name'" }
        reader.skip(22) // version, flags, method, time, date, crc, sizes
        val nameLength = reader.readUShortLE()
        val extraLength = reader.readUShortLE()
        reader.skip(nameLength + extraLength)
        return when (entry.method) {
            STORED -> reader.readBytes(entry.compressedSize)
            DEFLATED -> Inflate.inflate(data, reader.position, sizeHint = entry.uncompressedSize)
            else -> error("Unsupported zip compression method ${entry.method} for '$name'")
        }
    }

    private fun readCentralDirectory(): Map<String, Entry> {
        // The end-of-central-directory record is at the tail, possibly followed by a comment.
        var eocd = data.size - 22
        while (eocd >= 0 && ByteReader(data, eocd).readIntLE() != END_OF_CENTRAL_DIRECTORY) eocd--
        check(eocd >= 0) { "Not a zip archive (no end of central directory)" }
        val tail = ByteReader(data, eocd + 10)
        val count = tail.readUShortLE()
        tail.skip(4) // central directory size
        val offset = tail.readIntLE()

        val reader = ByteReader(data, offset)
        val result = LinkedHashMap<String, Entry>(count)
        repeat(count) {
            check(reader.readIntLE() == CENTRAL_HEADER) { "Bad central directory header" }
            reader.skip(6) // version made by, version needed, flags
            val method = reader.readUShortLE()
            reader.skip(8) // time, date, crc
            val compressedSize = reader.readIntLE()
            val uncompressedSize = reader.readIntLE()
            val nameLength = reader.readUShortLE()
            val extraLength = reader.readUShortLE()
            val commentLength = reader.readUShortLE()
            reader.skip(8) // disk number, internal attrs, external attrs
            val localHeaderOffset = reader.readIntLE()
            val name = reader.readBytes(nameLength).decodeToString()
            reader.skip(extraLength + commentLength)
            result[name] = Entry(method, compressedSize, uncompressedSize, localHeaderOffset)
        }
        return result
    }

    private class Entry(
        val method: Int,
        val compressedSize: Int,
        val uncompressedSize: Int,
        val localHeaderOffset: Int,
    )

    private companion object {
        const val LOCAL_HEADER = 0x04034b50
        const val CENTRAL_HEADER = 0x02014b50
        const val END_OF_CENTRAL_DIRECTORY = 0x06054b50
        const val STORED = 0
        const val DEFLATED = 8
    }
}
