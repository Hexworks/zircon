package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.builder.grid.AppConfigBuilder
import org.codetome.zircon.api.grid.AppConfig

object AppConfigs {

    @JvmStatic
    fun newBuilder() = AppConfigBuilder.newBuilder()

    @JvmStatic
    fun defaultConfiguration() = AppConfig.defaultConfiguration()
}
