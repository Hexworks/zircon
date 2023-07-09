package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetFactory
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetType
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetFactory
import org.hexworks.zircon.renderer.korge.tileset.*
import kotlin.reflect.KClass

@ZirconDsl
class TilesetFactoryBuilder<S : Any> : Builder<TilesetFactory<S>> {

    var targetType: KClass<S>? = null
    var tileType: TileType? = null
    var tilesetType: TilesetType? = null
    private var factoryFunction: ((TilesetResource) -> Tileset<S>)? = null

    fun factoryFunction(fn: (TilesetResource) -> Tileset<S>) {
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

    companion object {
        val DEFAULT_TILESET_FACTORIES: List<TilesetFactory<KorgeContext>> = listOf(
            tilesetFactory {
                targetType = KorgeContext::class
                tileType = TileType.CHARACTER_TILE
                tilesetType = TilesetType.CP437Tileset
                factoryFunction { resource: TilesetResource ->
                    KorgeCP437Tileset(
                        resource = resource,
                    )
                }
            },
            tilesetFactory {
                targetType = KorgeContext::class
                tileType = TileType.GRAPHICAL_TILE
                tilesetType = TilesetType.GraphicalTileset
                factoryFunction { resource: TilesetResource ->
                    KorgeGraphicalTileset(
                        resource = resource,
                    )
                }
            },
            tilesetFactory {
                targetType = KorgeContext::class
                tileType = TileType.CHARACTER_TILE
                tilesetType = TilesetType.TrueTypeFont
                factoryFunction { resource: TilesetResource ->
                    KorgeTrueTypeFontTileset(
                        resource = resource,
                    )
                }
            },
            tilesetFactory {
                targetType = KorgeContext::class
                tileType = TileType.IMAGE_TILE
                tilesetType = TilesetType.GraphicalTileset
                factoryFunction { resource: TilesetResource ->
                    KorgeImageDictionaryTileset(
                        resource = resource,
                    )
                }
            }
        )
    }
}

/**
 * Creates a new [TilesetFactory] using the builder DSL and returns it.
 */
fun <S : Any> tilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit): TilesetFactory<S> =
    TilesetFactoryBuilder<S>().apply(init).build()
