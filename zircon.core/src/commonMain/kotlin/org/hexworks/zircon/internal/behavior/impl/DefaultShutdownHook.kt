package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.platform.util.RuntimeUtils

class DefaultShutdownHook : ShutdownHook {

    override fun onShutdown(listener: () -> Unit) {
        RuntimeUtils.onShutdown {
            listener()
        }
    }
}
