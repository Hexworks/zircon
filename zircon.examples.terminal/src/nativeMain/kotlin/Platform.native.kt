@file:OptIn(ExperimentalForeignApi::class)

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.toKString
import kotlinx.cinterop.usePinned
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.fclose
import platform.posix.fopen
import platform.posix.fprintf
import platform.posix.fread
import platform.posix.fseek
import platform.posix.ftell
import platform.posix.getenv
import platform.posix.stderr

actual fun readFile(path: String): ByteArray {
    val file = fopen(path, "rb") ?: error("Cannot open '$path'")
    try {
        fseek(file, 0, SEEK_END)
        val size = ftell(file).toInt()
        fseek(file, 0, SEEK_SET)
        val bytes = ByteArray(size)
        if (size > 0) {
            val read = bytes.usePinned { fread(it.addressOf(0), 1.convert(), size.convert(), file) }
            check(read.toInt() == size) { "Short read on '$path'" }
        }
        return bytes
    } finally {
        fclose(file)
    }
}

actual fun environmentVariable(name: String): String? = getenv(name)?.toKString()

actual fun printError(text: String) {
    fprintf(stderr, "%s\n", text)
}
