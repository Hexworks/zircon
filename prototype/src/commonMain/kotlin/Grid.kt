/**
 * Benchmark grid: every cell holds a CP437 code plus packed ARGB fore/background.
 * `randomize` rewrites every cell, which is the worst case the renderer must survive.
 */
class Grid(val cols: Int, val rows: Int) {
    val size = cols * rows
    val chars = IntArray(size)
    val fg = IntArray(size)
    val bg = IntArray(size)

    private var seed = 0x9E3779B9.toInt()

    private fun next(): Int {
        var x = seed
        x = x xor (x shl 13)
        x = x xor (x ushr 17)
        x = x xor (x shl 5)
        seed = x
        return x
    }

    fun randomize(mode: ColorMode) {
        for (i in 0 until size) {
            chars[i] = next() and 0xFF
            when (mode) {
                ColorMode.RANDOM -> {
                    fg[i] = OPAQUE or (next() and 0xFFFFFF)
                    bg[i] = OPAQUE or (next() and 0xFFFFFF)
                }
                ColorMode.PALETTE16 -> {
                    fg[i] = PALETTE[next() and 0xF]
                    bg[i] = PALETTE[next() and 0xF]
                }
                ColorMode.MONO -> {
                    fg[i] = PALETTE[7]
                    bg[i] = PALETTE[0]
                }
            }
        }
    }

    companion object {
        const val OPAQUE = 0xFF000000.toInt()

        /** Classic 16-colour CGA palette. */
        val PALETTE = intArrayOf(
            0xFF000000.toInt(), 0xFF0000AA.toInt(), 0xFF00AA00.toInt(), 0xFF00AAAA.toInt(),
            0xFFAA0000.toInt(), 0xFFAA00AA.toInt(), 0xFFAA5500.toInt(), 0xFFAAAAAA.toInt(),
            0xFF555555.toInt(), 0xFF5555FF.toInt(), 0xFF55FF55.toInt(), 0xFF55FFFF.toInt(),
            0xFFFF5555.toInt(), 0xFFFF55FF.toInt(), 0xFFFFFF55.toInt(), 0xFFFFFFFF.toInt(),
        )
    }
}

enum class ColorMode { RANDOM, PALETTE16, MONO }
