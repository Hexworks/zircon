package org.codetome.zircon.api.resource

import org.codetome.zircon.internal.font.FontRegistry
import java.awt.Font
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
     * Loads a built-in physical [Font].
     */
    @JvmOverloads
    fun toFont(size: Float = 18f,
               cacheFonts: Boolean = true,
               withAntiAlias: Boolean = true) =
            loadPhysicalFont(
                    size = size,
                    withAntiAlias = withAntiAlias,
                    source = this.javaClass.getResourceAsStream(path),
                    cacheFonts = cacheFonts)

    companion object {

        /**
         * Loads a physical font from the given `source` as a physical [org.codetome.zircon.api.font.Font].
         * *Note that* it is your responsibility to supply the proper parameters for
         * this method!
         */
        @JvmOverloads
        @JvmStatic
        fun loadPhysicalFont(size: Float,
                             source: InputStream,
                             cacheFonts: Boolean = true,
                             withAntiAlias: Boolean = true) =
                FontRegistry.fetchPhysicalFont(
                        size = size,
                        source = source,
                        withAntiAlias = withAntiAlias,
                        cacheFonts = cacheFonts)
    }

}