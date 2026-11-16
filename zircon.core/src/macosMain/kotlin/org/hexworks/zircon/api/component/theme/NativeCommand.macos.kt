@file:OptIn(ExperimentalForeignApi::class)

package org.hexworks.zircon.api.component.theme

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

internal actual fun runNativeCommand(command: String): Pair<Int, String>? = memScoped {
    val pipe = popen(command, "r") ?: return null
    val output = StringBuilder()
    val buffer = allocArray<ByteVar>(BUFFER_SIZE)
    while (true) {
        val line = fgets(buffer, BUFFER_SIZE, pipe) ?: break
        output.append(line.toKString())
    }
    val status = pclose(pipe)
    status to output.toString()
}

private const val BUFFER_SIZE = 512
