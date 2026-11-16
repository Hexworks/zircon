package org.hexworks.zircon.api.component.theme

/**
 * Runs a shell command and returns its exit status and output, or `null` when it could not be
 * started. Each native target that can run commands implements it with `popen`.
 */
internal expect fun runNativeCommand(command: String): Pair<Int, String>?
