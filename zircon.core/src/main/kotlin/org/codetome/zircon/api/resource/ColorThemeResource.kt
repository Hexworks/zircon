package org.codetome.zircon.api.resource

import org.codetome.zircon.platform.factory.TextColorFactory
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.builder.ColorThemeBuilder
import org.codetome.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.codetome.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

enum class ColorThemeResource(private val colorTheme: ColorTheme) {
    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#CFF09E"))
            .brightForegroundColor(TextColorFactory.fromString("#A8DBA8"))
            .darkForegroundColor(TextColorFactory.fromString("#79BD9A"))
            .brightBackgroundColor(TextColorFactory.fromString("#3B8686"))
            .darkBackgroundColor(TextColorFactory.fromString("#0B486B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#C5E0DC"))
            .brightForegroundColor(TextColorFactory.fromString("#ECE5CE"))
            .darkForegroundColor(TextColorFactory.fromString("#F1D4AF"))
            .brightBackgroundColor(TextColorFactory.fromString("#E08E79"))
            .darkBackgroundColor(TextColorFactory.fromString("#774F38"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/559428/lucky_bubble_gum
     */
    LUCKY_BUBBLE_GUM(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#E33258"))
            .brightForegroundColor(TextColorFactory.fromString("#CCBF82"))
            .darkForegroundColor(TextColorFactory.fromString("#B8AF03"))
            .brightBackgroundColor(TextColorFactory.fromString("#67917A"))
            .darkBackgroundColor(TextColorFactory.fromString("#170409"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#D1E751"))
            .brightForegroundColor(TextColorFactory.fromString("#FFFFFF"))
            .darkForegroundColor(TextColorFactory.fromString("#FFFFFF"))
            .brightBackgroundColor(TextColorFactory.fromString("#26ADE4"))
            .darkBackgroundColor(TextColorFactory.fromString("#000000"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#BF4D28"))
            .brightForegroundColor(TextColorFactory.fromString("#F6F7BD"))
            .darkForegroundColor(TextColorFactory.fromString("#E6AC27"))
            .brightBackgroundColor(TextColorFactory.fromString("#80BCA3"))
            .darkBackgroundColor(TextColorFactory.fromString("#655643"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#FF9900"))
            .brightForegroundColor(TextColorFactory.fromString("#E9E9E9"))
            .darkForegroundColor(TextColorFactory.fromString("#BCBCBC"))
            .brightBackgroundColor(TextColorFactory.fromString("#3299BB"))
            .darkBackgroundColor(TextColorFactory.fromString("#424242"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#7B3B3B"))
            .brightForegroundColor(TextColorFactory.fromString("#B9D7D9"))
            .darkForegroundColor(TextColorFactory.fromString("#668284"))
            .brightBackgroundColor(TextColorFactory.fromString("#493736"))
            .darkBackgroundColor(TextColorFactory.fromString("#2A2829"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#F21D41"))
            .brightForegroundColor(TextColorFactory.fromString("#EBEBBC"))
            .darkForegroundColor(TextColorFactory.fromString("#BCE3C5"))
            .brightBackgroundColor(TextColorFactory.fromString("#82B3AE"))
            .darkBackgroundColor(TextColorFactory.fromString("#230F2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#AB9597"))
            .brightForegroundColor(TextColorFactory.fromString("#FFFBF0"))
            .darkForegroundColor(TextColorFactory.fromString("#968F4B"))
            .brightBackgroundColor(TextColorFactory.fromString("#7A6248"))
            .darkBackgroundColor(TextColorFactory.fromString("#030506"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#95AC54"))
            .brightForegroundColor(TextColorFactory.fromString("#F6F1CB"))
            .darkForegroundColor(TextColorFactory.fromString("#DFD4A7"))
            .brightBackgroundColor(TextColorFactory.fromString("#7F7C69"))
            .darkBackgroundColor(TextColorFactory.fromString("#29210A"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#b86A6A"))
            .brightForegroundColor(TextColorFactory.fromString("#F8EDD1"))
            .darkForegroundColor(TextColorFactory.fromString("#C5CFC6"))
            .brightBackgroundColor(TextColorFactory.fromString("#9D9D93"))
            .darkBackgroundColor(TextColorFactory.fromString("#474843"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#E6324B"))
            .brightForegroundColor(TextColorFactory.fromString("#F2E3C6"))
            .darkForegroundColor(TextColorFactory.fromString("#FFC6A5"))
            .brightBackgroundColor(TextColorFactory.fromString("#353634"))
            .darkBackgroundColor(TextColorFactory.fromString("#2B2B2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/27905/threadless
     */
    THREADLESS(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#b24C2F"))
            .brightForegroundColor(TextColorFactory.fromString("#E9F2F9"))
            .darkForegroundColor(TextColorFactory.fromString("#9CC4E4"))
            .brightBackgroundColor(TextColorFactory.fromString("#3A89C9"))
            .darkBackgroundColor(TextColorFactory.fromString("#1B325F"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/38562/Hands_On
     */
    HANDS_ON(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#94BA65"))
            .brightForegroundColor(TextColorFactory.fromString("#2790B0"))
            .darkForegroundColor(TextColorFactory.fromString("#4B6E92"))
            .brightBackgroundColor(TextColorFactory.fromString("#4E4D4A"))
            .darkBackgroundColor(TextColorFactory.fromString("#353432"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(ColorThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#e99A44"))
            .brightForegroundColor(TextColorFactory.fromString("#EAE7D1"))
            .darkForegroundColor(TextColorFactory.fromString("#CCC58E"))
            .brightBackgroundColor(TextColorFactory.fromString("#7B8455"))
            .darkBackgroundColor(TextColorFactory.fromString("#485C2B"))
            .build()),
    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#b58900"))
            .build()),

    SOLARIZED_DARK_ORANGE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#cb4b16"))
            .build()),

    SOLARIZED_DARK_RED(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#dc322f"))
            .build()),

    SOLARIZED_DARK_MAGENTA(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#d33682"))
            .build()),

    SOLARIZED_DARK_VIOLET(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#6c71c4"))
            .build()),

    SOLARIZED_DARK_BLUE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#268bd2"))
            .build()),

    SOLARIZED_DARK_CYAN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#2aa198"))
            .build()),

    SOLARIZED_DARK_GREEN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#859900"))
            .build()),

    SOLARIZED_LIGHT_YELLOW(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#b58900"))
            .build()),

    SOLARIZED_LIGHT_ORANGE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#cb4b16"))
            .build()),

    SOLARIZED_LIGHT_RED(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#dc322f"))
            .build()),

    SOLARIZED_LIGHT_MAGENTA(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#d33682"))
            .build()),

    SOLARIZED_LIGHT_VIOLET(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#6c71c4"))
            .build()),

    SOLARIZED_LIGHT_BLUE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#268bd2"))
            .build()),

    SOLARIZED_LIGHT_CYAN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#2aa198"))
            .build()),

    SOLARIZED_LIGHT_GREEN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColorFactory.fromString("#859900"))
            .build());

    fun getTheme() = colorTheme

    enum class SolarizedBase(val colorThemeBuilder: ColorThemeBuilder) {
        SOLARIZED_DARK_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TextColorFactory.fromString("#93a1a1"))
                .darkForegroundColor(TextColorFactory.fromString("#839496"))
                .brightBackgroundColor(TextColorFactory.fromString("#073642"))
                .darkBackgroundColor(TextColorFactory.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TextColorFactory.fromString("#657b83"))
                .darkForegroundColor(TextColorFactory.fromString("#586e75"))
                .brightBackgroundColor(TextColorFactory.fromString("#fdf6e3"))
                .darkBackgroundColor(TextColorFactory.fromString("#eee8d5")))
    }
}
