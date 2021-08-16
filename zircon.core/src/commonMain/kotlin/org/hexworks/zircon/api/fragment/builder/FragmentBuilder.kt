package org.hexworks.zircon.api.fragment.builder

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position

/**
 * @param T type of the fragment that will be built
 * @param U type of this builder that is returned by the with...() methods
 */
interface FragmentBuilder<T, U : FragmentBuilder<T, U>> : Builder<T> {

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(position))`
     */
    fun withPosition(position: Position): U

    /**
     * Aligns the resulting [Component] positionally, relative to its parent.
     * Same as calling `withAlignment(positionalAlignment(x, y))`
     */
    fun withPosition(x: Int, y: Int): U = withPosition(Position.create(x, y))
}
