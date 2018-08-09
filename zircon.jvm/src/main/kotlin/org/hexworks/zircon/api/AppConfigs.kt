package org.hexworks.zircon.api

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.application.AppConfigBuilder

object AppConfigs {

    @JvmStatic
    fun newBuilder() = AppConfigBuilder.newBuilder()

    /**
     * Returns the default [AppConfig].
     */
    @JvmStatic
    fun defaultConfiguration() = AppConfig.defaultConfiguration()
}
