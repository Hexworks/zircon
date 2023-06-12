package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.ModifierSupportBuilder
import org.hexworks.zircon.api.modifier.TextureTransformModifier
import org.hexworks.zircon.api.tileset.TextureTransformer
import kotlin.reflect.KClass

/**
 * Contains all the necessary data / functionality to support drawing a specific [modifierType].
 */
class ModifierSupport<T : Any> internal constructor(
    val modifierType: KClass<out TextureTransformModifier>,
    val targetType: KClass<T>,
    val transformer: TextureTransformer<T>
) {
    companion object {

        fun <T : Any> newBuilder() = ModifierSupportBuilder.newBuilder<T>()
    }
}
