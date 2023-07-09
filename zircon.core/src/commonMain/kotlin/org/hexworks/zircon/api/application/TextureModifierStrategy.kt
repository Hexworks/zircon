package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.tileset.TextureTransformer
import kotlin.reflect.KClass

/**
 * Contains all the necessary data / functionality to support drawing a specific [modifierType].
 */
class TextureModifierStrategy<T : Any, C: Any> internal constructor(
    val modifierType: KClass<out TextureModifier>,
    val targetType: KClass<T>,
    val transformer: TextureTransformer<T, C>
)