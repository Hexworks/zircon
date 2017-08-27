package org.codetome.zircon.internal.util.rex

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File as JFile
import java.util.zip.GZIPInputStream

/**
 * Takes a GZIP-compressed [ByteArray] and returns it decompressed.
 */
fun decompressGZIPByteArray(compressedInput: ByteArray): ByteArray {
    val gzipInputStream = GZIPInputStream(ByteArrayInputStream(compressedInput))
    val outputStream = ByteArrayOutputStream(compressedInput.size)
    val buffer = ByteArray(1024)

    while (gzipInputStream.available() > 0) {
        val count = gzipInputStream.read(buffer)
        if (count > 0) {
            outputStream.write(buffer)
        }
    }

    outputStream.close()
    gzipInputStream.close()

    return outputStream.toByteArray()
}
