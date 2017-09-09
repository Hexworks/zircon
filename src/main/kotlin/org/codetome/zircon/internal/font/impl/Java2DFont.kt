package org.codetome.zircon.internal.font.impl

import org.codetome.zircon.api.Modifiers.*
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.CharacterMetadata
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.internal.extensions.isNotPresent
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.MetadataPickingStrategy
import org.codetome.zircon.internal.font.cache.DefaultFontRegionCache
import org.codetome.zircon.internal.font.transformer.*
import java.awt.image.BufferedImage

/**
 * Represents a font which is backed by a sprite sheet.
 */
class Java2DFont(private val source: BufferedImage,
                 private val metadata: Map<Char, List<CharacterMetadata>>,
                 private val width: Int,
                 private val height: Int,
                 private val regionTransformers: List<FontRegionTransformer<BufferedImage>>,
                 private val cache: DefaultFontRegionCache<BufferedImage>,
                 private val metadataPickingStrategy: MetadataPickingStrategy = PickFirstMetaStrategy())
    : Font<BufferedImage> {

    override fun getWidth() = width

    override fun getHeight() = height

    override fun hasDataForChar(char: Char) = metadata.containsKey(char)

    override fun fetchMetadataForChar(char: Char): List<CharacterMetadata> {
        return metadata[char] ?: listOf()
    }

    override fun fetchRegionForChar(textCharacter: TextCharacter, vararg tags: String): BufferedImage {
        val meta = fetchMetaFor(textCharacter, tags)
        val maybeRegion = cache.retrieveIfPresent(textCharacter)

        var region = if (maybeRegion.isNotPresent()) {
            var image = source.getSubimage(meta.x * width, meta.y * height, width, height)
            regionTransformers.forEach {
                image = it.transform(image, textCharacter)
            }
            cache.store(textCharacter, image)
            image
        } else {
            maybeRegion.get()
        }
        textCharacter.getModifiers().forEach {
            region = MODIFIER_TRANSFORMER_LOOKUP[it::class]?.transform(region, textCharacter) ?: region
        }
        return region
    }

    private fun fetchMetaFor(textCharacter: TextCharacter, tags: Array<out String>): CharacterMetadata {
        require(hasDataForChar(textCharacter.getCharacter())) {
            "No metadata exists for '${textCharacter.getCharacter()}'!"
        }
        return metadataPickingStrategy.pickMetadata(metadata[textCharacter.getCharacter()]!!.filter {
            it.tags.containsAll(tags.toList())
        })
    }

    companion object {
        val MODIFIER_TRANSFORMER_LOOKUP = mapOf(
                Pair(Underline::class, Java2DUnderlineTransformer()),
                Pair(VerticalFlip::class, Java2DVerticalFlipper()),
                Pair(HorizontalFlip::class, Java2DHorizontalFlipper()),
                Pair(CrossedOut::class, Java2DCrossedOutTransformer()),
                Pair(Border::class, Java2DBorderTransformer()),
                Pair(Blink::class, NoOpTransformer()),
                Pair(Hidden::class, Java2DHiddenTransformer())

        ).toMap()
    }
}