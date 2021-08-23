package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetFactory
import kotlin.reflect.KClass
import kotlin.jvm.JvmStatic

@ZirconDsl
class TilesetFactoryBuilder<S : Any> private constructor(
    var targetType: KClass<S>? = null,
    var supportedTileType: TileType? = null,
    var supportedTilesetType: TilesetType? = null,
    var factoryFunction: ((TilesetResource) -> Tileset<S>)? = null

) : Builder<TilesetFactory<S>> {

    override fun createCopy(): Builder<TilesetFactory<S>> {
        TODO("not implemented")
    }

    override fun build(): TilesetFactory<S> {
        requireNotNull(targetType) {
            "Target type is missing."
        }
        requireNotNull(supportedTileType) {
            "Supported tile type is missing."
        }
        requireNotNull(supportedTilesetType) {
            "Supported tileset type is missing."
        }
        requireNotNull(factoryFunction) {
            "Factory function is missing"
        }
        return DefaultTilesetFactory(
            targetType = targetType!!,
            supportedTileType = supportedTileType!!,
            supportedTilesetType = supportedTilesetType!!,
            factoryFunction = factoryFunction!!
        )
    }

    companion object {

        @JvmStatic
        fun <S : Any> newBuilder() = TilesetFactoryBuilder<S>()

    }
}
