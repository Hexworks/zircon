package org.hexworks.zircon.api.builder.tileset

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.tileset.DefaultTilesetFactory
import kotlin.reflect.KClass

/**
 * Builds [TilesetFactory] instances. Pass the result to the renderer (for example
 * `TileGridView(extraTilesetFactories = ...)` in `zircon.compose`) to teach it a new tileset kind.
 */
@ZirconDsl
public class TilesetFactoryBuilder<S : Any> : Builder<TilesetFactory<S>> {

    public var targetType: KClass<S>? = null
    public var tileType: TileType? = null
    public var tilesetType: TilesetType? = null
    private var factoryFunction: ((TilesetResource) -> Tileset<S>)? = null

    public fun factoryFunction(fn: (TilesetResource) -> Tileset<S>) {
        factoryFunction = fn
    }

    override fun build(): TilesetFactory<S> {
        // TODO: use contract here for checking
        requireNotNull(targetType) {
            "Target type is missing."
        }
        requireNotNull(tileType) {
            "Supported tile type is missing."
        }
        requireNotNull(tilesetType) {
            "Supported tileset type is missing."
        }
        requireNotNull(factoryFunction) {
            "Factory function is missing"
        }
        return DefaultTilesetFactory(
            targetType = targetType!!,
            supportedTileType = tileType!!,
            supportedTilesetType = tilesetType!!,
            factoryFunction = factoryFunction!!
        )
    }
}

/**
 * Creates a new [TilesetFactory] using the builder DSL and returns it.
 */
public fun <S : Any> tilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit): TilesetFactory<S> =
    TilesetFactoryBuilder<S>().apply(init).build()
