package org.hexworks.zircon

import org.hexworks.zircon.api.builder.config.zirconDefaults
import org.hexworks.zircon.api.config.ErrorHandler
import org.hexworks.zircon.api.config.ZirconDefaults

/**
 * Installs itself as [ZirconDefaults.onError] and keeps every reported error; [uninstall]
 * restores the previous defaults. For tests of the paths that contain exceptions.
 */
class ErrorRecorder : ErrorHandler {

    val errors = mutableListOf<Pair<String, Throwable>>()
    val sources: List<String> get() = errors.map { it.first }

    private val previous = ZirconDefaults.current

    init {
        zirconDefaults { onError = this@ErrorRecorder }
    }

    override fun onError(source: String, error: Throwable) {
        errors += source to error
    }

    fun uninstall() {
        ZirconDefaults.current = previous
    }
}
