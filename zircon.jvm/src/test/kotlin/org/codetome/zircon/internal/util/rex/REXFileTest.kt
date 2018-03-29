package org.codetome.zircon.internal.util.rex

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class REXFileTest {

    @Test
    fun test() {
        val raw = arrayOf(
                // file header
                0x01, 0x00, 0x00, 0x00,     // version
                0x02, 0x00, 0x00, 0x00,     // number of layers
                // layer #1
                0x02, 0x00, 0x00, 0x00,     // xLength
                0x01, 0x00, 0x00, 0x00,     // yLength
                    // cell #1
                    0x41, 0x00, 0x00, 0x00, // character (hex 41 -> dec 65 -> 'A')
                    0xaa, 0xaa, 0xaa,       // foreground
                    0x55, 0x55, 0x55,       // background
                    // cell #2
                    0x42, 0x00, 0x00, 0x00, // character (hex 42 -> dec 66 -> 'B')
                    0x85, 0x85, 0x85,       // foreground
                    0x23, 0x23, 0x23,       // background
                // layer #2
                0x02, 0x00, 0x00, 0x00,     // xLength
                0x01, 0x00, 0x00, 0x00,     // yLength
                    // cell #1
                    0x41, 0x00, 0x00, 0x00, // character (hex 41 -> dec 65 -> 'A')
                    0xbb, 0xbb, 0xbb,       // foreground
                    0x55, 0x55, 0x55,       // background
                    // cell #2
                    0x42, 0x00, 0x00, 0x00, // character (hex 42 -> dec 66 -> 'B')
                    0x85, 0x85, 0x85,       // foreground
                    0x23, 0x23, 0x23        // background
        )
        val ba = ByteArray(raw.size)
        for ((i, b) in raw.withIndex()) {
            ba[i] = b.toByte()
        }

        val file = REXFile.fromByteArray(ba)

        assertThat(file.getVersion()).isEqualTo(1)
        assertThat(file.getLayers().size).isEqualTo(file.getNumberOfLayers())
    }
}
