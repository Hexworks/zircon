package org.codetome.zircon.graphics.shape.test

import org.codetome.zircon.graphics.shape.Shape

interface ShapeBuilder<T : ShapeParameters> {

    fun buildShape(shapeParameters: T): Shape
}