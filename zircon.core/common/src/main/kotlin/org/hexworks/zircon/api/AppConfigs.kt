package org.hexworks.zircon.api

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import kotlin.jvm.JvmStatic

object AppConfigs {

    @JvmStatic
    fun newConfig() = AppConfigBuilder.newBuilder()

    /**
     * Returns the default [AppConfig].
     */
    @JvmStatic
    fun defaultConfiguration() = AppConfig.defaultConfiguration()
}
