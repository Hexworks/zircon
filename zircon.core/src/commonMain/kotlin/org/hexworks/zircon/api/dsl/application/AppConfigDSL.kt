package org.hexworks.zircon.api.dsl.application

import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.DebugConfig
import org.hexworks.zircon.api.application.ModifierSupport
import org.hexworks.zircon.api.application.ShortcutsConfig
import org.hexworks.zircon.api.builder.application.*
import org.hexworks.zircon.api.tileset.TilesetFactory

/**
 * Creates a new [AppConfig] using the builder DSL and returns it.
 */
fun appConfig(init: AppConfigBuilder.() -> Unit): AppConfig =
    AppConfigBuilder.newBuilder().apply(init).build()

fun <S : Any> AppConfigBuilder.tilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit): TilesetFactory<S> {
    return TilesetFactoryBuilder.newBuilder<S>().apply(init).build().apply {
        withTilesetFactories(this)
    }
}

fun <T : Any> AppConfigBuilder.modifierSupport(init: ModifierSupportBuilder<T>.() -> Unit): ModifierSupport<T> {
    return ModifierSupportBuilder.newBuilder<T>().apply(init).build().apply {
        withModifierSupports(this)
    }
}

fun AppConfigBuilder.shortcutsConfig(init: ShortcutsConfigBuilder.() -> Unit): ShortcutsConfig {
    return ShortcutsConfigBuilder.newBuilder().apply(init).build().apply {
        withShortcutsConfig(this)
    }
}

fun AppConfigBuilder.debugConfig(init: DebugConfigBuilder.() -> Unit): DebugConfig {
    return DebugConfigBuilder.newBuilder().apply(init).build().apply {
        withDebugConfig(this)
    }
}
