package org.hexworks.zircon.api.dsl.fragment

import org.hexworks.zircon.api.builder.fragment.SelectorBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.component.buildFragmentFor
import org.hexworks.zircon.api.fragment.Selector

/**
 * Creates a new [Selector] using the fragment builder DSL and returns it.
 */
fun <T : Any> buildSelector(
    init: SelectorBuilder<T>.() -> Unit
): Selector<T> = SelectorBuilder.newBuilder<T>().apply(init).build()

/**
 * Creates a new [Selector] using the fragment builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [Selector].
 */
fun <S : Any> AnyContainerBuilder.selector(
    init: SelectorBuilder<S>.() -> Unit
): Selector<S> = buildFragmentFor(this, SelectorBuilder.newBuilder(), init)
