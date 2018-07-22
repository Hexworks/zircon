package org.codetome.zircon.api.interop

import org.codetome.zircon.api.terminal.builder.DeviceConfigurationBuilder

object DeviceConfigurations {

    @JvmStatic
    fun newBuilder() = DeviceConfigurationBuilder.newBuilder()
}
