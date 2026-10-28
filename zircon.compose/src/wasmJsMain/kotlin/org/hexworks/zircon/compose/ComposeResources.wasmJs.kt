package org.hexworks.zircon.compose

import org.hexworks.zircon.compose.resources.Res

/**
 * In the browser a "file system" path is a URL relative to the page, fetched the same way
 * bundled resources are.
 */
internal actual suspend fun readFileBytes(path: String): ByteArray = Res.readBytes(path)
