package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.terminal.DeviceConfigurationBuilder
import org.codetome.zircon.api.terminal.DeviceConfiguration

object DeviceConfigurations {

    @JvmStatic
    fun newBuilder() = DeviceConfigurationBuilder.newBuilder()

    @JvmStatic
    fun defaultConfiguration() = DeviceConfiguration.defaultConfiguration()
}
