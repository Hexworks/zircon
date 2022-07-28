package org.hexworks.zircon.internal.tileset.impl.korge

import com.soywiz.kds.IntArray2
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addTo
import com.soywiz.korge.view.fast.FSprite
import com.soywiz.korge.view.fast.FSprites
import com.soywiz.korim.bitmap.Bitmap32
import com.soywiz.korim.bitmap.BmpSlice
import com.soywiz.korim.bitmap.slice
import com.soywiz.korim.color.Colors
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

class KorGECP437DrawSurface(
    tileSize: Size,
    gridSize: Size,
    private val glyphs: Array<out BmpSlice>
) : Container() {

    private val tileWidth = tileSize.width
    private val tileHeight = tileSize.height
    private val columns = gridSize.width
    private val rows = gridSize.height

    private val bgBitmap = Bitmap32(tileWidth, tileHeight, Colors.WHITE).slice()

    private val bgFSprites = FSprites(columns * rows)
    private val bgView = bgFSprites.createView(bgBitmap.bmp).addTo(this)
    private val bgMat = IntArray2(columns, rows) { bgFSprites.alloc().id }

    private val fgFSprites = FSprites(columns * rows)
    private val fgView = fgFSprites.createView(glyphs.first().bmp).addTo(this)
    private val fgMat = IntArray2(columns, rows) { fgFSprites.alloc().id }

    init {
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                fgFSprites.apply {
                    val fsprite = FSprite(fgMat[col, row])
                    fsprite.x = col * tileWidth.toFloat()
                    fsprite.y = row * tileHeight.toFloat()
                }
                bgFSprites.apply {
                    val fsprite = FSprite(bgMat[col, row])
                    fsprite.x = col * tileWidth.toFloat()
                    fsprite.y = row * tileHeight.toFloat()
                    fsprite.colorMul = Colors.BLACK
                    fsprite.setTex(bgBitmap)
                }
            }
        }
    }

    fun drawTile(tile: CharacterTile, position: Position) {
        val character = tile.character
        val (foregroundColor, backgroundColor) = tile.styleSet
        val (x, y) = position
        bgFSprites.apply {
            FSprite(bgMat[x, y]).colorMul = backgroundColor.toRGBA()
        }
        fgFSprites.apply {
            val fsprite = FSprite(fgMat[x, y])
            fsprite.colorMul = foregroundColor.toRGBA()
            fsprite.setTex(glyphs[character.code])
        }
    }
}