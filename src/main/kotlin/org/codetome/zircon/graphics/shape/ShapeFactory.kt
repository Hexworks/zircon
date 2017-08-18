package org.codetome.zircon.graphics.shape

import org.codetome.zircon.graphics.shape.factory.ShapeParameters

/**
 * A [ShapeFactory] is responsible for creating a single
 * kind of shape (like a rectangle or a triangle) using
 * a specialized version of [ShapeParameters].
 */
interface ShapeFactory<in T : ShapeParameters> {

    /**
     * Creates the [Shape] this [ShapeFactory] is responsible for building.
     */
    fun createShape(shapeParameters: T): Shape
}