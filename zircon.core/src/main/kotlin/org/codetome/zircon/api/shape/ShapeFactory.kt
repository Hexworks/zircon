package org.codetome.zircon.api.shape

/**
 * A [ShapeFactory] is responsible for creating a single
 * kind of shape (like a rectangle or a triangle) using
 * a specialized version of [ShapeParameters].
 */
interface ShapeFactory<in T : ShapeParameters> {

    /**
     * Creates the [Shape] this [ShapeFactory] is responsible for building.
     * Note that all [Shape]s are offset to the (0x0) position!
     */
    fun createShape(shapeParameters: T): Shape
}
