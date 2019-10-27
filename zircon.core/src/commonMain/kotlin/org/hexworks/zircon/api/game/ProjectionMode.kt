package org.hexworks.zircon.api.game

import org.hexworks.zircon.internal.game.ProjectionStrategy
import org.hexworks.zircon.internal.game.impl.TopDownObliqueProjectionStrategy
import org.hexworks.zircon.internal.game.impl.TopDownProjectionStrategy

enum class ProjectionMode(internal val projectionStrategy: ProjectionStrategy) {
    TOP_DOWN(TopDownProjectionStrategy()),
    TOP_DOWN_OBLIQUE(TopDownObliqueProjectionStrategy())
}
