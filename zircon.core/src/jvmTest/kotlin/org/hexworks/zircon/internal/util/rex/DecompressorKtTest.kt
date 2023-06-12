package org.hexworks.zircon.internal.util.rex

import org.hexworks.zircon.internal.util.unZipIt
import org.junit.Test

@Suppress("DEPRECATION")
class DecompressorKtTest {

    @Test
    fun test() {
        val tempFolder = createTempDir()
        unZipIt(
            zipSource = this.javaClass.getResourceAsStream("/graphical_tilesets/nethack_16x16.zip") ?: error("Can't open stream"),
            outputFolder = tempFolder
        )
    }


}
