package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.util.Runnable
import org.hexworks.zircon.platform.util.RuntimeUtils

class DefaultShutdownHook : ShutdownHook {

    override fun onShutdown(listener: Runnable) {
        RuntimeUtils.onShutdown {
            listener.run()
        }
    }
}
