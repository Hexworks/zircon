package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.AppConfigBuilder

/**
 * This simple interface is used to set and retrieve custom properties on [AppConfig]
 * in a typesafe way.
 *
 * @see AppConfigBuilder.withProperty
 * @see AppConfig.getOrNull
 * @see AppConfig.getOrElse
 */
interface AppConfigKey<T>
