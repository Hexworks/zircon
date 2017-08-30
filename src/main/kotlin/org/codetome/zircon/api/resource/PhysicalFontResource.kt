package org.codetome.zircon.api.resource

import org.codetome.zircon.api.util.FontUtils
import org.codetome.zircon.internal.font.cache.DefaultFontRegionCache
import org.codetome.zircon.internal.font.cache.PhysicalFontCachingStrategy
import org.codetome.zircon.internal.font.impl.PhysicalFont
import java.awt.Font
import java.awt.GraphicsEnvironment
import java.awt.image.BufferedImage
import java.io.InputStream

/**
 * This enum encapsulates the means of loading physical fonts from `.ttf` files.
 * You can either use a built-in font (downloaded from Google Fonts)
 * or you can load your own using [PhysicalFontResource.loadPhysicalFont].
 */
enum class PhysicalFontResource(private val fontName: String,
                                private val fileName: String = "$fontName.ttf",
                                private val path: String = "/monospace_fonts/$fileName") {

    ANONYMOUS_PRO("AnonymousPro-Regular"),
    COUSINE("Cousine-Regular"),
    CUTIVE_MONO("CutiveMono-Regular"),
    DROID_SANS_MONO("DroidSansMono"),
    FIRA_MONO("FiraMono-Regular"),
    INCONSOLATA("Inconsolata-Regular"),
    NOVA_MONO("NovaMono"),
    OXYGEN_MONO("OxygenMono-Regular"),
    PT_MONO("PtMono"),
    ROBOTO_MONO("RobotoMono-Regular"),
    SHARE_TECH_MONO("ShareTechMono-Regular"),
    SOURCE_CODE_PRO("SourceCodePro-Regular"),
    SPACE_MONO("SpaceMono-Regular"),
    UBUNTU_MONO("UbuntuMono-Regular"),
    VT323("VT323-Regular");

    /**
     * Loads a built-in [PhysicalFont].
     */
    @JvmOverloads
    fun toFont(size: Float = 18f, withAntiAlias: Boolean = true)
            = loadPhysicalFont(size, withAntiAlias, this.javaClass.getResourceAsStream(path))

    companion object {

        /**
         * Loads a physical font from the given `source` as a [PhysicalFont].
         * *Note that* it is your responsibility to supply the proper parameters for
         * this method!
         */
        @JvmOverloads
        fun loadPhysicalFont(size: Float = 18f, withAntiAlias: Boolean = true, source: InputStream): org.codetome.zircon.api.font.Font<BufferedImage> {
            val font = Font.createFont(Font.TRUETYPE_FONT, source).deriveFont(size)
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            ge.registerFont(font)

            return PhysicalFont(
                    source = font,
                    width = FontUtils.getFontWidth(font),
                    height = FontUtils.getFontHeight(font),
                    cache = DefaultFontRegionCache(
                            imageCachingStrategy = PhysicalFontCachingStrategy()),
                    withAntiAlias = withAntiAlias)
        }
    }

}