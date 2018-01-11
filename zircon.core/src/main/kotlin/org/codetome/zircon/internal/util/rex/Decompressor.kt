package org.codetome.zircon.internal.util.rex

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.io.File as JFile


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

fun unZipIt(zipFilePath: String, outputFolder: File): List<File> {

    val buffer = ByteArray(1024)
    val outputFolderPath = outputFolder.absolutePath
    val result = mutableListOf<File>()

    val zis = ZipInputStream(FileInputStream(zipFilePath))
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
