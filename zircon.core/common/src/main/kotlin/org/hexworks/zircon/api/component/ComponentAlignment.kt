package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect

enum class ComponentAlignment(private val withinFn: (target: Rect, subject: Rect) -> Position,
                              private val aroundFn: (target: Rect, subject: Rect) -> Position) {
    TOP_LEFT(withinFn = { target, _ ->
        Positions.zero()
    }, aroundFn = { target, subject ->
        target.position.withRelativeX(-subject.width)
    }),
    TOP_CENTER(withinFn = { target, subject ->
        target.topCenter.withRelativeX(-subject.width / 2)
    }, aroundFn = { target, subject ->
        target.topCenter.withRelativeY(-subject.height)
                .withRelativeX(-subject.width / 2)
    }),
    TOP_RIGHT(withinFn = { target, subject ->
        target.topRight.withRelativeX(-subject.width)
    }, aroundFn = { target, _ ->
        target.topRight
    }),
    RIGHT_CENTER(withinFn = { target, subject ->
        target.rightCenter.withRelativeX(-subject.width)
                .withRelativeY(-subject.height / 2)
    }, aroundFn = { target, subject ->
        target.rightCenter.withRelativeY(-subject.height / 2)
    }),
    BOTTOM_RIGHT(withinFn = { target, subject ->
        target.bottomRight.withRelativeX(-subject.width)
                .withRelativeY(-subject.height)
    }, aroundFn = { target, _ ->
        target.bottomRight
    }),
    BOTTOM_CENTER(withinFn = { target, subject ->
        target.bottomCenter.withRelativeY(-subject.height)
                .withRelativeX(-subject.width / 2)
    }, aroundFn = { target, subject ->
        target.bottomCenter
                .withRelativeX(-subject.width / 2)
    }),
    BOTTOM_LEFT(withinFn = { target, subject ->
        target.bottomLeft.withRelativeY(-subject.height)
    }, aroundFn = { target, subject ->
        target.bottomLeft.withRelativeX(-subject.width)
    }),
    LEFT_CENTER(withinFn = { target, subject ->
        target.leftCenter.withRelativeY(-subject.height / 2)
    }, aroundFn = { target, subject ->
        target.leftCenter.withRelativeX(-subject.width)
                .withRelativeY(-subject.height / 2)
    }),
    CENTER(withinFn = { target, subject ->
        target.center.withRelativeY(-subject.height / 2)
                .withRelativeX(-subject.width / 2)
    }, aroundFn = { _, _ ->
        throw UnsupportedOperationException("Can't use CENTER alignment around a container.")
    });

    /**
     * Returns the [Position] which can be used to properly align
     * `subject` **within** `target`.
     */
    fun alignWithin(target: Rect, subject: Rect): Position {
        return withinFn(target, subject)
    }

    /**
     * Returns the [Position] which can be used to properly align
     * `subject` **around** `target`.
     */
    fun alignAround(target: Rect, subject: Rect): Position {
        return aroundFn(target, subject)
    }
}
