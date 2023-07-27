package org.hexworks.zircon.api.builder.application

import org.hexworks.zircon.api.application.TextureModifierStrategy
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.tileset.TextureContext
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.tileset.impl.DefaultTextureTransformer
import kotlin.reflect.KClass

@ZirconDsl
class TextureModifierBuilder<T : Any, C : Any> : Builder<TextureModifierStrategy<T, C>> {

    var modifierType: KClass<out TextureModifier>? = null
    var targetType: KClass<T>? = null
    var transformer: TextureTransformer<T, C>? = null

    private var transformerFunction: ((texture: TextureContext<T, C>, tile: Tile) -> TextureContext<T, C>)? = null

    fun transformer(fn: (context: TextureContext<T, C>, tile: Tile) -> TextureContext<T, C>) {
        transformerFunction = fn
    }

    override fun build(): TextureModifierStrategy<T, C> {
        requireNotNull(targetType) {
            "Target type is missing."
        }
        requireNotNull(modifierType) {
            "Modifier type is missing"
        }
        require(transformerFunction != null || transformer != null) {
            "Transformer is missing."
        }
        return TextureModifierStrategy(
            modifierType = modifierType!!,
            targetType = targetType!!,
            transformer = transformer ?: DefaultTextureTransformer(targetType!!, transformerFunction!!)
        )
    }
}