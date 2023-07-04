package org.hexworks.zircon.renderer.korge

import korlibs.korge.Korge
import korlibs.korge.KorgeDisplayMode
import korlibs.korge.render.BatchBuilder2D
import korlibs.korge.scene.sceneContainer
import korlibs.math.geom.Size
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.EventBus
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.application.InternalApplication
import org.hexworks.zircon.internal.event.ZirconScope
import org.hexworks.zircon.internal.renderer.Renderer
import org.hexworks.zircon.renderer.korge.KorgeRenderer.KorgeScene

class KorgeApplication(
    override val config: AppConfig,
    override val tileGrid: TileGrid,
    override val eventBus: EventBus,
    private val renderer: Renderer<BatchBuilder2D, KorgeApplication, KorgeScene>,
    override val eventScope: ZirconScope = ZirconScope(),
) : InternalApplication {
    init {
        tileGrid.asInternal().application = this
    }

    val scene = renderer.create()

    override val closed: Boolean
        get() = closedValue.value
    override val closedValue: Property<Boolean> = false.toProperty()

    override suspend fun start() {
        val size = Size(tileGrid.widthInPixels, tileGrid.heightInPixels);
        Korge(
            virtualSize = size,
            windowSize = size,
            displayMode = KorgeDisplayMode.TOP_LEFT_NO_CLIP
        ) {
            sceneContainer().changeTo({ scene })
        }
    }

    override fun beforeRender(listener: (RenderData) -> Unit) = renderer.beforeRender(listener)

    override fun afterRender(listener: (RenderData) -> Unit) = renderer.afterRender(listener)

    override fun asInternal(): InternalApplication = this

    override fun close() {
        closedValue.value = true
        tileGrid.close()
    }
}