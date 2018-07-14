package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.internal.behavior.ShutdownHook
import org.codetome.zircon.api.util.Runnable
import org.codetome.zircon.platform.util.RuntimeUtils

class DefaultShutdownHook : ShutdownHook {

    override fun onShutdown(listener: () -> Unit) {
        RuntimeUtils.onShutdown(listener)
    }

    override fun onShutdown(listener: Runnable) {
        RuntimeUtils.onShutdown {
            listener.run()
        }
    }
}
