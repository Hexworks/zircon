package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {
        SwingApplications.startTileGrid(AppConfig.newBuilder()
            .withDebugMode(true)
            .withDebugConfig(DebugConfig.newBuilder()
                .withRelaxBoundsCheck(true)
                .build())
            .build())
    }

}

