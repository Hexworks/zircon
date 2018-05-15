package org.codetome.zircon.api

import org.codetome.zircon.api.component.Component

/**
 * A 2D position in terminal space. Please note that the coordinates are 0-indexed, meaning 0x0 is the
 * top left corner of the terminal. This object is immutable so you cannot change it after it has been created.
 * Instead, you can easily create modified clones by using the `with*` methods.
 *
 * **Note that** the `copy` operation is not supported for this class. Use the `of` method to create a new
 * instance of [Position]!
 */
data class JvmPosition(
        /**
         * Represents the `x` in a terminal.
         */
        override val x: Int,
        /**
         * Represents the `y` in a terminal
         */
        override val y: Int) : Position {

    init {
        require(x >= 0 && y >= 0) {
            "A position must have a x and a y which is greater than or equal to 0!"
        }
    }

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    fun relativeToTopOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.create(compX + x, maxOf(compY - y, 0))
    }

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToRightOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.create(
                x = compX + component.getBoundableSize().xLength + x,
                y = compY + y)
    }

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToBottomOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.create(
                x = compX + x,
                y = compY + component.getBoundableSize().yLength + y)
    }

    /**
     * Creates a [Position] which is relative to the left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    fun relativeToLeftOf(component: Component) = component.getPosition().let { (compX, compY) ->
        Position.create(maxOf(compX - x, 0), compY + y)
    }

}

