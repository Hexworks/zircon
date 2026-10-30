package org.hexworks.zircon.internal.util

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults

private val LOGGER = LoggerFactory.getLogger(ErrorHandler::class)

/**
 * Reports an exception Zircon contained: logs it, then hands it to [ZirconDefaults.onError].
 * [source] names the unit of work that failed. Never throws.
 */
internal fun reportError(source: String, error: Throwable) {
    LOGGER.error(error) { "$source failed; Zircon carries on without it. $error" }
    try {
        ZirconDefaults.current.onError.onError(source, error)
    } catch (e: Exception) {
        LOGGER.error(e) { "The ZirconDefaults.onError handler threw while handling an error from $source. $e" }
    }
}
