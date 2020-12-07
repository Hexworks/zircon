package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.internal.component.InternalComponentContainer

/**
 * A [CompositeLayerStateHolder] can be used to compose an [InternalLayerable]
 * and an [InternalComponentContainer]'s [layerStates]. All [InternalLayerable]
 * operations are delegated to [layerable], but [layerStates] will contain the
 * states of [components] as well.
 */
class CompositeLayerStateHolder(
    private val layerable: Layerable,
    private val components: InternalComponentContainer
)
