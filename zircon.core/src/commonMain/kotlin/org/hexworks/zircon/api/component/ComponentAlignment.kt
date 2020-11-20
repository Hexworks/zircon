package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.behavior.Boundable

/**
 * Contains the possible alignment options for a [Boundable] object
 * relative to other [Boundable] objects.
 */
// TODO: wouldn't BoundableAlignment be a better name?
enum class ComponentAlignment(
        private val withinFn: (other: Rect, target: Size) -> Position,
        private val aroundFn: (other: Rect, target: Size) -> Position
) {

    TOP_LEFT(withinFn = { _, _ ->
        Position.zero()
    }, aroundFn = { other, target ->
        other.position.withRelativeX(-target.width)
    }),
    TOP_CENTER(withinFn = { other, target ->
        other.topCenter.withRelativeX(-target.width / 2)
    }, aroundFn = { other, target ->
        other.topCenter.withRelativeY(-target.height)
                .withRelativeX(-target.width / 2)
    }),
    TOP_RIGHT(withinFn = { other, target ->
        other.topRight.withRelativeX(-target.width)
    }, aroundFn = { other, _ ->
        other.topRight
    }),
    RIGHT_CENTER(withinFn = { other, target ->
        other.rightCenter.withRelativeX(-target.width)
                .withRelativeY(-target.height / 2)
    }, aroundFn = { other, target ->
        other.rightCenter.withRelativeY(-target.height / 2)
    }),
    BOTTOM_RIGHT(withinFn = { other, target ->
        other.bottomRight.withRelativeX(-target.width)
                .withRelativeY(-target.height)
    }, aroundFn = { other, _ ->
        other.bottomRight
    }),
    BOTTOM_CENTER(withinFn = { other, target ->
        other.bottomCenter.withRelativeY(-target.height)
                .withRelativeX(-target.width / 2)
    }, aroundFn = { other, target ->
        other.bottomCenter
                .withRelativeX(-target.width / 2)
    }),
    BOTTOM_LEFT(withinFn = { other, target ->
        other.bottomLeft.withRelativeY(-target.height)
    }, aroundFn = { other, target ->
        other.bottomLeft.withRelativeX(-target.width)
    }),
    LEFT_CENTER(withinFn = { other, target ->
        other.leftCenter.withRelativeY(-target.height / 2)
    }, aroundFn = { other, target ->
        other.leftCenter.withRelativeX(-target.width)
                .withRelativeY(-target.height / 2)
    }),
    CENTER(withinFn = { other, target ->
        other.center.withRelativeY(-target.height / 2)
                .withRelativeX(-target.width / 2)
    }, aroundFn = { _, _ ->
        throw UnsupportedOperationException("Can't use CENTER alignment around a container.")
    });

    /**
     * Returns the [Position] which can be used to properly align a boundable
     * with [target] size **within** [other].
     */
    fun alignWithin(other: Rect, target: Size): Position {
        return withinFn(other, target)
    }

    /**
     * Returns the [Position] which can be used to properly align a boundable
     * with [target] size **around** [other].
     */
    fun alignAround(other: Rect, target: Size): Position {
        return aroundFn(other, target)
    }
}
