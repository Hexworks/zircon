package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.impl.SubTileGraphics
import org.hexworks.zircon.internal.component.modal.DefaultModal

class DefaultModalRenderer<T: ModalResult> : ComponentRenderer<DefaultModal<T>> {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<DefaultModal<T>>) {
        tileGraphics.applyStyle(StyleSetBuilder.newBuilder()
                .withBackgroundColor(TileColor.create(0, 0, 0, 125))
                .build())
    }
}
