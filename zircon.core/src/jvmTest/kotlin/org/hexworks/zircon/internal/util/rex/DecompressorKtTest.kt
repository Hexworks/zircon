package org.hexworks.zircon.internal.util.rex

import org.junit.Test

class DecompressorKtTest {

    @Test
    fun test() {
        val tempFolder = createTempDir()
        unZipIt(
                zipSource = this.javaClass.getResourceAsStream("/graphical_tilesets/nethack_16x16.zip"),
                outputFolder = tempFolder)
        println()
    }


}
