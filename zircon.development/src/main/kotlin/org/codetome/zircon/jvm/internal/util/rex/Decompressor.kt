package org.codetome.zircon.jvm.internal.util.rex

import java.io.*
import java.io.File
import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.io.File as JFile
import java.io.ByteArrayOutputStream
import java.io.ByteArrayInputStream

/**
 * Takes a GZIP-compressed [ByteArray] and returns it decompressed.
 */
fun decompressGZIPByteArray(compressedData: ByteArray): ByteArray {
    ByteArrayInputStream(compressedData).use { bin ->
        GZIPInputStream(bin).use { gzipper ->
            val buffer = ByteArray(1024)
            val out = ByteArrayOutputStream()

            var len = gzipper.read(buffer)
            while (len > 0) {
                out.write(buffer, 0, len)
                len = gzipper.read(buffer)
            }

            gzipper.close()
            out.close()
            return out.toByteArray()
        }
    }
}

fun unZipIt(zipSource: InputStream, outputFolder: File): List<File> {

    val buffer = ByteArray(1024)
    val outputFolderPath = outputFolder.absolutePath
    val result = mutableListOf<File>()

    val zis = ZipInputStream(zipSource)
    var ze: ZipEntry? = zis.nextEntry

    while (ze != null) {
        val fileName = ze.name
        val newFile = File(outputFolderPath + File.separator + fileName)
        File(newFile.parent).mkdirs()
        val fos = FileOutputStream(newFile)
        result.add(newFile)

        var len = zis.read(buffer)
        while (len > 0) {
            fos.write(buffer, 0, len)
            len = zis.read(buffer)
        }

        fos.close()
        ze = zis.nextEntry
    }

    zis.closeEntry()
    zis.close()

    return result
}
