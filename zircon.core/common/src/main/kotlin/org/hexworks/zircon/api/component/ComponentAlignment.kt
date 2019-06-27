package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size

enum class ComponentAlignment(
        private val withinFn: (other: Rect, target: Size) -> Position,
        private val aroundFn: (other: Rect, target: Size) -> Position) {
    TOP_LEFT(withinFn = { _, _ ->
        Positions.zero()
    }, aroundFn = { other, target ->
        other.position.withRelativeX(-target.width)
    }),
    TOP_CENTER(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .topCenter
                .withRelativeX(-(target.width / 2 + 1))
    }, aroundFn = { other, target ->
        other.topCenter.withRelativeY(-(target.height + 1))
                .withRelativeX(-(target.width / 2 + 1))
    }),
    TOP_RIGHT(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .topRight
                .withRelativeX(-(target.width + 1))
    }, aroundFn = { other, _ ->
        other.topRight
    }),
    RIGHT_CENTER(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .rightCenter
                .withRelativeX(-(target.width + 1)).withRelativeY(-(target.height / 2 + 1))
    }, aroundFn = { other, target ->
        other.rightCenter.withRelativeY(-target.height / 2)
    }),
    BOTTOM_RIGHT(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .bottomRight
                .withRelativeX(-(target.width + 1))
                .withRelativeY(-(target.height + 1))
    }, aroundFn = { other, _ ->
        other.bottomRight
    }),
    BOTTOM_CENTER(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .bottomCenter
                .withRelativeY(-(target.height + 1))
                .withRelativeX(-(target.width / 2 + 1))
    }, aroundFn = { other, target ->
        other.bottomCenter
                .withRelativeX(-target.width / 2)
    }),
    BOTTOM_LEFT(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .bottomLeft
                .withRelativeY(-(target.height + 1))
    }, aroundFn = { other, target ->
        other.bottomLeft.withRelativeX(-target.width)
    }),
    LEFT_CENTER(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .leftCenter
                .withRelativeY(-(target.height / 2 + 1))
    }, aroundFn = { other, target ->
        other.leftCenter.withRelativeX(-target.width)
                .withRelativeY(-target.height / 2)
    }),
    CENTER(withinFn = { other, target ->
        Rect.create(Position.defaultPosition(), other.size.minus(Size.create(1,1)))
                .center
                .withRelativeY(-(target.height / 2 + 1))
                .withRelativeX(-(target.width / 2 + 1))
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
