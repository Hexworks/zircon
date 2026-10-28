package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.modifier.TextureModifier
import org.hexworks.zircon.api.tileset.TextureTransformer
import kotlin.reflect.KClass

/**
 * Contains all the necessary data / functionality to support drawing a specific [TextureModifier]
 * @param T the texture object that the renderer will draw on (like Graphics2D in Swing)
 * @param C the rendering context that contains all related objects that are necessary for rendering
 */
public class TextureModifierStrategy<T : Any, C : Any> internal constructor(
    public val modifierType: KClass<out TextureModifier>,
    public val targetType: KClass<T>,
    public val transformer: TextureTransformer<T, C>
)