package org.hexworks.zircon.internal.config

import org.hexworks.zircon.api.application.AppConfig

public object RuntimeConfig {

    public var config: AppConfig = AppConfig.defaultAppConfig()
}
