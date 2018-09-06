package org.hexworks.zircon.examples

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

object KotlinPlayground {


    @JvmStatic
    fun main(args: Array<String>) {


        val someComponent = SomeComponent(
                position = Positions.create(1, 1),
                size = Sizes.create(5, 5))

        val decoratedComponent = BoxComponentDecoration.decorate(someComponent)

        decoratedComponent.getComponent().getFoo()

        decoratedComponent.moveTo(Positions.create(4, 4))
    }


    class BoxComponentDecoration<T : Component>(private val component: T)
        : Component, ComponentDecoration<T> {

        // a box decoration takes up one cell on all sides
        private var bounds = component.bounds().withRelativeSize(Sizes.create(2, 2))

        init {
            // we move the component once it is wrapped to its proper position
            // and the decoration takes the component's original position
            component.moveTo(bounds.position().withRelative(Position.offset1x1()))

            // draw the box around the component here ...
        }

        override fun getComponent() = component

        // a decoration implements all the functionality of a Component and delegates
        // events to its child (like mouse click) effectively making this a
        // decorator in GoF terminology as well
        override fun bounds() = bounds

        override fun moveTo(position: Position) {
            // a box offsets the component's position by 1x1
            bounds = bounds.withPosition(position)
            component.moveTo(position.withRelative(Position.offset1x1()))
        }

        companion object {

            fun <T : Component> decorate(component: T): ComponentDecoration<T> {
                return BoxComponentDecoration(component)
            }
        }
    }

    interface ComponentDecoration<T : Component> : Component {

        fun getComponent(): T
    }

    interface Component {

        fun bounds(): Bounds

        fun moveTo(position: Position)
    }

    class SomeComponent(position: Position,
                        size: Size) : Component {

        private var bounds = Bounds.create(position, size)

        override fun bounds() = bounds

        override fun moveTo(position: Position) {
            this.bounds = bounds.withPosition(position)
        }

        fun getFoo() = "foo"

    }

}
