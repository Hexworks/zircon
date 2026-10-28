package org.hexworks.zircon.internal.util.compression

/**
 * DEFLATE (RFC 1951) decoder in plain Kotlin so every target can read gzip, zlib and zip
 * payloads without a platform library. Follows the structure of Mark Adler's `puff`
 * reference decoder: canonical Huffman tables are stored as per-length counts plus a
 * symbol list and decoded bit by bit, which is small and plenty fast for asset files.
 */
internal object Inflate {

    /**
     * Inflates a raw deflate stream starting at [offset]. [sizeHint] pre-sizes the output.
     */
    fun inflate(input: ByteArray, offset: Int = 0, sizeHint: Int = input.size * 4): ByteArray {
        val bits = BitReader(input, offset)
        val out = Output(sizeHint.coerceAtLeast(256))
        var last: Boolean
        do {
            last = bits.bits(1) == 1
            when (val type = bits.bits(2)) {
                0 -> stored(bits, out)
                1 -> codes(bits, out, FIXED_LENGTH_CODE, FIXED_DISTANCE_CODE)
                2 -> dynamic(bits, out)
                else -> error("Invalid deflate block type $type")
            }
        } while (!last)
        return out.toByteArray()
    }

    private fun stored(bits: BitReader, out: Output) {
        bits.alignToByte()
        val len = bits.bits(16)
        val nlen = bits.bits(16)
        check(len == nlen.inv() and 0xFFFF) { "Stored block length check failed" }
        repeat(len) { out.write(bits.bits(8)) }
    }

    private fun dynamic(bits: BitReader, out: Output) {
        val nlen = bits.bits(5) + 257
        val ndist = bits.bits(5) + 1
        val ncode = bits.bits(4) + 4
        check(nlen <= MAX_LENGTH_CODES && ndist <= MAX_DISTANCE_CODES) { "Bad dynamic block counts" }

        val lengths = IntArray(MAX_LENGTH_CODES + MAX_DISTANCE_CODES)
        for (i in 0 until ncode) lengths[CODE_LENGTH_ORDER[i]] = bits.bits(3)
        val lengthCode = Huffman(lengths, 19)

        var index = 0
        while (index < nlen + ndist) {
            var symbol = lengthCode.decode(bits)
            when {
                symbol < 16 -> lengths[index++] = symbol
                else -> {
                    var repeatValue = 0
                    val repeat: Int
                    when (symbol) {
                        16 -> {
                            check(index > 0) { "Repeat with no previous length" }
                            repeatValue = lengths[index - 1]
                            repeat = 3 + bits.bits(2)
                        }
                        17 -> repeat = 3 + bits.bits(3)
                        else -> repeat = 11 + bits.bits(7)
                    }
                    check(index + repeat <= nlen + ndist) { "Too many code lengths" }
                    repeat(repeat) { lengths[index++] = repeatValue }
                }
            }
            symbol = 0
        }
        check(lengths[256] != 0) { "No end-of-block code" }

        val lengthHuffman = Huffman(lengths, nlen)
        val distanceHuffman = Huffman(lengths.copyOfRange(nlen, nlen + ndist), ndist)
        codes(bits, out, lengthHuffman, distanceHuffman)
    }

    private fun codes(bits: BitReader, out: Output, lengthCode: Huffman, distanceCode: Huffman) {
        while (true) {
            var symbol = lengthCode.decode(bits)
            when {
                symbol < 256 -> out.write(symbol)
                symbol == 256 -> return
                else -> {
                    symbol -= 257
                    check(symbol < 29) { "Invalid length symbol" }
                    val length = LENGTH_BASE[symbol] + bits.bits(LENGTH_EXTRA[symbol])
                    val distSymbol = distanceCode.decode(bits)
                    check(distSymbol < 30) { "Invalid distance symbol" }
                    val distance = DISTANCE_BASE[distSymbol] + bits.bits(DISTANCE_EXTRA[distSymbol])
                    out.copyBack(distance, length)
                }
            }
        }
    }

    private class BitReader(private val data: ByteArray, private var pos: Int) {
        private var buffer = 0
        private var count = 0

        fun bits(n: Int): Int {
            while (count < n) {
                check(pos < data.size) { "Unexpected end of deflate stream" }
                buffer = buffer or ((data[pos++].toInt() and 0xFF) shl count)
                count += 8
            }
            val value = buffer and ((1 shl n) - 1)
            buffer = buffer ushr n
            count -= n
            return value
        }

        /** Bytes are only pulled on demand, so fewer than 8 bits can be buffered here. */
        fun alignToByte() {
            buffer = 0
            count = 0
        }
    }

    private class Huffman(lengths: IntArray, symbols: Int) {
        private val count = IntArray(MAX_BITS + 1)
        private val symbol = IntArray(symbols)

        init {
            for (i in 0 until symbols) count[lengths[i]]++
            var left = 1
            for (len in 1..MAX_BITS) {
                left = left shl 1
                left -= count[len]
                check(left >= 0) { "Over-subscribed Huffman code" }
            }
            val offsets = IntArray(MAX_BITS + 1)
            for (len in 1 until MAX_BITS) offsets[len + 1] = offsets[len] + count[len]
            for (i in 0 until symbols) {
                if (lengths[i] != 0) symbol[offsets[lengths[i]]++] = i
            }
        }

        fun decode(bits: BitReader): Int {
            var code = 0
            var first = 0
            var index = 0
            for (len in 1..MAX_BITS) {
                code = code or bits.bits(1)
                val n = count[len]
                if (code - n < first) return symbol[index + (code - first)]
                index += n
                first += n
                first = first shl 1
                code = code shl 1
            }
            error("Invalid Huffman code")
        }
    }

    private class Output(initial: Int) {
        private var buf = ByteArray(initial)
        private var size = 0

        fun write(b: Int) {
            if (size == buf.size) grow(size + 1)
            buf[size++] = b.toByte()
        }

        fun copyBack(distance: Int, length: Int) {
            check(distance <= size) { "Distance too far back" }
            if (size + length > buf.size) grow(size + length)
            var from = size - distance
            repeat(length) { buf[size++] = buf[from++] }
        }

        private fun grow(min: Int) {
            buf = buf.copyOf(maxOf(min, buf.size * 2))
        }

        fun toByteArray(): ByteArray = buf.copyOf(size)
    }

    private const val MAX_BITS = 15
    private const val MAX_LENGTH_CODES = 286
    private const val MAX_DISTANCE_CODES = 30

    private val LENGTH_BASE = intArrayOf(
        3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31,
        35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258,
    )
    private val LENGTH_EXTRA = intArrayOf(
        0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,
        3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0,
    )
    private val DISTANCE_BASE = intArrayOf(
        1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193,
        257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577,
    )
    private val DISTANCE_EXTRA = intArrayOf(
        0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6,
        7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13,
    )
    private val CODE_LENGTH_ORDER = intArrayOf(16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15)

    private val FIXED_LENGTH_CODE = Huffman(
        IntArray(288) { if (it < 144) 8 else if (it < 256) 9 else if (it < 280) 7 else 8 },
        288,
    )
    private val FIXED_DISTANCE_CODE = Huffman(IntArray(30) { 5 }, 30)
}
