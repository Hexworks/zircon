package org.hexworks.zircon.terminal

/**
 * A growable byte buffer for one frame's escape sequences. Reused across frames so a frame
 * allocates nothing once the buffer has grown to its working size.
 */
public class ByteSink(initialCapacity: Int = 64 * 1024) {

    private var bytes = ByteArray(initialCapacity)
    public var size: Int = 0
        private set

    public fun reset() {
        size = 0
    }

    public fun byte(value: Int) {
        ensure(1)
        bytes[size++] = value.toByte()
    }

    /** Appends an ASCII string. */
    public fun ascii(text: String) {
        ensure(text.length)
        for (ch in text) bytes[size++] = ch.code.toByte()
    }

    /** Appends a non-negative integer in decimal. */
    public fun int(value: Int) {
        if (value < 256) {
            small(value)
            return
        }
        int(value / 10)
        byte('0'.code + value % 10)
    }

    /** Appends an integer in `0..255` in decimal, from a table: colour channels mostly. */
    public fun small(value: Int) {
        val offset = value * 4
        val length = SMALL[offset].toInt()
        ensure(length)
        for (i in 1..length) bytes[size++] = SMALL[offset + i]
    }

    /** Appends `ESC [ 38;2;r;g;b m`-style colour parameters: [selector] then the channels. */
    public fun rgb(selector: Int, packed: Int) {
        ensure(16)
        small(selector)
        bytes[size++] = SEMICOLON
        bytes[size++] = TWO
        bytes[size++] = SEMICOLON
        small((packed shr 16) and 0xFF)
        bytes[size++] = SEMICOLON
        small((packed shr 8) and 0xFF)
        bytes[size++] = SEMICOLON
        small(packed and 0xFF)
    }

    /** Appends [codePoint] as UTF-8. */
    public fun codePoint(codePoint: Int) {
        when {
            codePoint < 0x80 -> byte(codePoint)
            codePoint < 0x800 -> {
                byte(0xC0 or (codePoint shr 6))
                byte(0x80 or (codePoint and 0x3F))
            }
            codePoint < 0x10000 -> {
                byte(0xE0 or (codePoint shr 12))
                byte(0x80 or ((codePoint shr 6) and 0x3F))
                byte(0x80 or (codePoint and 0x3F))
            }
            else -> {
                byte(0xF0 or (codePoint shr 18))
                byte(0x80 or ((codePoint shr 12) and 0x3F))
                byte(0x80 or ((codePoint shr 6) and 0x3F))
                byte(0x80 or (codePoint and 0x3F))
            }
        }
    }

    public fun toByteArray(): ByteArray = bytes.copyOf(size)

    /** The backing array; only the first [size] bytes are valid. */
    public fun array(): ByteArray = bytes

    private fun ensure(extra: Int) {
        if (size + extra > bytes.size) {
            bytes = bytes.copyOf(maxOf(bytes.size * 2, size + extra))
        }
    }

    private companion object {
        const val SEMICOLON: Byte = ';'.code.toByte()
        const val TWO: Byte = '2'.code.toByte()

        // For each value in 0..255: its digit count, then up to three ASCII digits.
        val SMALL: ByteArray = ByteArray(256 * 4).also { table ->
            for (value in 0..255) {
                val digits = value.toString()
                table[value * 4] = digits.length.toByte()
                for (i in digits.indices) table[value * 4 + 1 + i] = digits[i].code.toByte()
            }
        }
    }
}
