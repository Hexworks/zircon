package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultLogArea

class DefaultLogAreaRenderer : ComponentRenderer<DefaultLogArea> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultLogArea>) {
        tileGraphics.applyStyle(context.componentStyle.currentStyle())
    }

}
