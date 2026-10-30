package org.hexworks.zircon.internal.util

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults

private val LOGGER = LoggerFactory.getLogger(ErrorHandler::class)
private val reportedOnce = HashSet<Any>()

/**
 * Reports an exception Zircon contained: logs it, then hands it to [ZirconDefaults.onError].
 * [source] names the unit of work that failed. Never throws.
 */
public fun reportError(source: String, error: Throwable) {
    LOGGER.error(error) { "$source failed; Zircon carries on without it. $error" }
    try {
        ZirconDefaults.current.onError.onError(source, error)
    } catch (e: Exception) {
        LOGGER.error(e) { "The ZirconDefaults.onError handler threw while handling an error from $source. $e" }
    }
}

/**
 * Like [reportError], but only the first report for [key] goes out; later ones with the same
 * key are dropped. For failures a renderer would otherwise repeat on every frame, such as a
 * tileset it cannot draw.
 */
public fun reportErrorOnce(key: Any, source: String, error: Throwable) {
    if (reportedOnce.add(key)) reportError(source, error)
}
