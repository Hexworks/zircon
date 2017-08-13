package org.codetome.zircon.font

import com.github.benmanes.caffeine.cache.Caffeine
import org.codetome.zircon.TextCharacter
import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*
import java.util.concurrent.TimeUnit

class Java2DFont(private val source: BufferedImage,
                 private val metadata: Map<Char, List<CharacterMetadata>>,
                 private val width: Int,
                 private val height: Int) : Font<BufferedImage> {

    private val cachedImages = Caffeine.newBuilder()
            .initialCapacity(100)
            .maximumSize(10000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build<Int, BufferedImage>()

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char) = metadata.containsKey(char)

    override fun fetchMetadataForChar(char: Char): List<CharacterMetadata> {
        return metadata[char] ?: listOf()
    }

    override fun fetchRegionForChar(textCharacter: TextCharacter, vararg tags: String): BufferedImage {
        require(hasDataForChar(textCharacter.getCharacter())) {
            "No metadata exists for '${textCharacter.getCharacter()}'!"
        }
        val metas = metadata[textCharacter.getCharacter()]!!.filter {
            it.tags.containsAll(tags.toList())
        }
        require(metas.size == 1) {
            "There are more than 1 metadata entries for char: '${textCharacter.getCharacter()}' and tags: '${tags.toList().joinToString()}'"
        }
        val meta = metas.first()
        val hash = Objects.hash(textCharacter.getCharacter(), textCharacter.getBackgroundColor(), textCharacter.getForegroundColor())

        var image: BufferedImage? = cachedImages.getIfPresent(hash)

        if (image == null) {
            image = applyColorSwap(
                    source.getSubimage(meta.x * width, meta.y * height, width, height),
                    textCharacter.getBackgroundColor().toAWTColor(),
                    textCharacter.getForegroundColor().toAWTColor())
            cachedImages.put(hash, image!!)
        }

        return image
    }

    private fun applyColorSwap(source: BufferedImage, bg: Color, fg: Color): BufferedImage {
        val backgroundRGB = bg.rgb
        val foregroundRGB = fg.rgb
        val image = cloneImage(source)

        for (y in 0..image.height - 1) {
            for (x in 0..image.width - 1) {
                val pixel = image.getRGB(x, y)
                val alpha = pixel shr 24 and 0xff
                val red = pixel shr 16 and 0xff
                val green = pixel shr 8 and 0xff
                val blue = pixel and 0xff

                var isTransparent = alpha != 255
                isTransparent = isTransparent and (red == 0)
                isTransparent = isTransparent and (green == 0)
                isTransparent = isTransparent and (blue == 0)

                if (isTransparent) {
                    image.setRGB(x, y, backgroundRGB)
                } else {
                    image.setRGB(x, y, foregroundRGB)
                }
            }
        }

        return image
    }

    private fun cloneImage(image: BufferedImage): BufferedImage {
        val newImage = BufferedImage(image.width, image.height, image.type)
        val g = newImage.graphics
        g.drawImage(image, 0, 0, null)
        g.dispose()
        return newImage
    }
}