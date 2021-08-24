package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.ModifierSupport
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TextureTransformModifier
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.tileset.impl.DefaultTextureTransformer
import kotlin.reflect.KClass
import kotlin.jvm.JvmStatic

@ZirconDsl
class ModifierSupportBuilder<T : Any> private constructor(
    var modifierType: KClass<out TextureTransformModifier>? = null,
    var targetType: KClass<T>? = null,
    var transformerFunction: ((texture: TileTexture<T>, tile: Tile) -> TileTexture<T>)? = null,
    var transformer: TextureTransformer<T>? = null
) : Builder<ModifierSupport<T>> {

    override fun createCopy() = ModifierSupportBuilder(
        modifierType = modifierType,
        targetType = targetType,
        transformerFunction = transformerFunction
    )

    override fun build(): ModifierSupport<T> {
        requireNotNull(targetType) {
            "Target type is missing."
        }
        requireNotNull(modifierType) {
            "Modifier type is missing"
        }
        require(transformerFunction != null || transformer != null) {
            "Transformer is missing."
        }
        return ModifierSupport(
            modifierType = modifierType!!,
            targetType = targetType!!,
            transformer = transformer ?: DefaultTextureTransformer(targetType!!, transformerFunction!!)
        )
    }

    companion object {

        @JvmStatic
        fun <T : Any> newBuilder() = ModifierSupportBuilder<T>()

    }
}
