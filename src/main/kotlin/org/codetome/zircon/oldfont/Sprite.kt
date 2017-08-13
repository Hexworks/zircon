package org.codetome.zircon.oldfont

/**
 * This interface represents a sprite which containsPosition multiple
 * textures and they can be accessed by extracting sub-images
 * from the sprite.
 */
interface Sprite <out I>{

    /**
     * Returns a part of this [Sprite].
     * The size of the returned image depends on the `width` / `height`
     * and the position from which the image is extracted depends on
     * `x` / `y`.
     */
    fun getSubImage(x: Int, y: Int, width: Int, height: Int): I
}