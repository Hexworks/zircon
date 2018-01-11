package org.codetome.zircon.internal.util.rex

import org.junit.Test

class DecompressorKtTest {

    @Test
    fun test() {
        val tempFolder = createTempDir()
        unZipIt(
                zipFilePath = "src/main/resources/graphic_tilesets/nethack_16x16.zip",
                outputFolder = tempFolder)
        println()
    }


}