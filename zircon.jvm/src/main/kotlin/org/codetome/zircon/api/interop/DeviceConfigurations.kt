package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.grid.DeviceConfigurationBuilder
import org.codetome.zircon.api.grid.DeviceConfiguration

object DeviceConfigurations {

    @JvmStatic
    fun newBuilder() = DeviceConfigurationBuilder.newBuilder()

    @JvmStatic
    fun defaultConfiguration() = DeviceConfiguration.defaultConfiguration()
}
