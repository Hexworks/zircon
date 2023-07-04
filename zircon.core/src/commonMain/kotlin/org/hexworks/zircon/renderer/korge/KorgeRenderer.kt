package org.hexworks.zircon.renderer.korge

import korlibs.korge.annotations.KorgeExperimental
import korlibs.korge.input.keys
import korlibs.korge.input.mouse
import korlibs.korge.scene.PixelatedScene
import korlibs.korge.time.interval
import korlibs.korge.view.SContainer
import korlibs.korge.view.renderableView
import korlibs.math.geom.Anchor
import korlibs.math.geom.ScaleMode
import korlibs.time.DateTime.Companion.nowUnixMillisLong
import korlibs.time.milliseconds
import org.hexworks.zircon.api.application.RenderData
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.impl.BaseRenderer
import org.hexworks.zircon.renderer.korge.KorgeRenderer.KorgeScene
import org.hexworks.zircon.renderer.korge.tileset.KorgeContext

class KorgeRenderer(
    tileGrid: InternalTileGrid,
    tilesetLoader: TilesetLoader<KorgeContext>,
) : BaseRenderer<KorgeContext, KorgeApplication, KorgeScene>(tileGrid, tilesetLoader) {

    private val config = tileGrid.config

    private val keyboardEventListener = KeyboardEventListener(tileGrid)
    private val mouseEventListener = MouseEventListener(tileGrid)

    inner class KorgeScene : PixelatedScene(
        tileGrid.size.width,
        tileGrid.size.height,
        sceneScaleMode = ScaleMode.NO_SCALE,
        sceneAnchor = Anchor.TOP_LEFT,
        sceneSmoothing = false
    ) {

        @OptIn(KorgeExperimental::class)
        override suspend fun SContainer.sceneMain() {

            mouse {
                mouseEventListener.handleMouseEvent(this)
            }
            keys {
                keyboardEventListener.handleEvents(this)
            }

            var blinkOn = false
            interval(config.blinkLengthInMilliSeconds.milliseconds) {
                blinkOn = !blinkOn
            }

            // 📙 Note that this starts continuous rendering
            renderableView {
                val now = nowUnixMillisLong()
                val view = this
                beforeRenderData = RenderData(now)
                this.ctx.useBatcher { batch ->
                    render(
                        KorgeContext(
                            this.ctx,
                            batch,
                            view
                        )
                    )
                }
                afterRenderData = RenderData(nowUnixMillisLong())
            }
        }

    }

    override fun create() = KorgeScene()

    override fun processInputEvents() {
        keyboardEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
        mouseEventListener.drainEvents().forEach { (event, phase) ->
            tileGrid.process(event, phase)
        }
    }

    override fun prepareRender(conetxt: KorgeContext) {
        // TODO: do we need this?
    }

}