package org.hexworks.zircon.internal.util
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

private const val VERTICAL_HEIGHT_OFFSET = 0

fun gridFillByScreenSize(screenWidth: Int, screenHeight: Int, tileWidth: Int, tileHeight: Int) = Size.create(screenWidth / tileWidth, screenHeight / tileHeight - VERTICAL_HEIGHT_OFFSET)

fun Position.fromRight(container: Container) : Position {
    val topRight = Position.topRightOf(container)
    return Position.create(topRight.x - this.x, topRight.y + this.y)
}

fun Position.fromBottom(container: Container) : Position {
    val bottomLeft = Position.bottomLeftOf(container)
    return Position.create(bottomLeft.x + this.x, bottomLeft.y - this.y)
}