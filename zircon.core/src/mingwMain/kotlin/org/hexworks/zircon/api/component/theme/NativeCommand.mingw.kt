@file:OptIn(ExperimentalForeignApi::class)

package org.hexworks.zircon.api.component.theme

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fgets
import platform.posix._pclose
import platform.posix._popen

internal actual fun runNativeCommand(command: String): Pair<Int, String>? = memScoped {
    val pipe = _popen(command, "r") ?: return null
    val output = StringBuilder()
    val buffer = allocArray<ByteVar>(BUFFER_SIZE)
    while (true) {
        val line = fgets(buffer, BUFFER_SIZE, pipe) ?: break
        output.append(line.toKString())
    }
    val status = _pclose(pipe)
    status to output.toString()
}

private const val BUFFER_SIZE = 512
