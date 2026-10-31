package org.hexworks.zircon.internal.component.modal

import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.graphics.darkenByPercent
import org.hexworks.zircon.internal.graphics.withTilesetUnless

/**
 * Builds the backdrop a [modal] shows behind its content: the part of [frame] (the
 * screen as it looked when the modal opened) under the modal's bounds, darkened by the
 * modal's `darkenPercent`. Tiles keep the tileset they were captured with when the modal
 * draws with another one.
 */
internal fun buildModalBackdrop(frame: TileGraphics, modal: Modal<out ModalResult>): TileGraphics {
    val backdrop = FastTileGraphics(initialSize = modal.size, initialTileset = modal.tileset)
    val offset = modal.position
    val percent = modal.darkenPercent
    for (pos in modal.size.fetchPositions()) {
        val tile = frame.getTileAtOrNull(pos + offset) ?: continue
        val tagged = tile.withTilesetUnless(frame.tileset, backdrop.tileset)
        backdrop.draw(if (percent > 0.0) tagged.darkenByPercent(percent) else tagged, pos)
    }
    return backdrop
}
