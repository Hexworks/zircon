package org.codetome.zircon.api.resource

import org.codetome.zircon.api.color.TextColor
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
            .accentColor(TextColor.fromString("#CFF09E"))
            .brightForegroundColor(TextColor.fromString("#A8DBA8"))
            .darkForegroundColor(TextColor.fromString("#79BD9A"))
            .brightBackgroundColor(TextColor.fromString("#3B8686"))
            .darkBackgroundColor(TextColor.fromString("#0B486B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#C5E0DC"))
            .brightForegroundColor(TextColor.fromString("#ECE5CE"))
            .darkForegroundColor(TextColor.fromString("#F1D4AF"))
            .brightBackgroundColor(TextColor.fromString("#E08E79"))
            .darkBackgroundColor(TextColor.fromString("#774F38"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/559428/lucky_bubble_gum
     */
    LUCKY_BUBBLE_GUM(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#E33258"))
            .brightForegroundColor(TextColor.fromString("#CCBF82"))
            .darkForegroundColor(TextColor.fromString("#B8AF03"))
            .brightBackgroundColor(TextColor.fromString("#67917A"))
            .darkBackgroundColor(TextColor.fromString("#170409"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#D1E751"))
            .brightForegroundColor(TextColor.fromString("#FFFFFF"))
            .darkForegroundColor(TextColor.fromString("#FFFFFF"))
            .brightBackgroundColor(TextColor.fromString("#26ADE4"))
            .darkBackgroundColor(TextColor.fromString("#000000"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#BF4D28"))
            .brightForegroundColor(TextColor.fromString("#F6F7BD"))
            .darkForegroundColor(TextColor.fromString("#E6AC27"))
            .brightBackgroundColor(TextColor.fromString("#80BCA3"))
            .darkBackgroundColor(TextColor.fromString("#655643"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#FF9900"))
            .brightForegroundColor(TextColor.fromString("#E9E9E9"))
            .darkForegroundColor(TextColor.fromString("#BCBCBC"))
            .brightBackgroundColor(TextColor.fromString("#3299BB"))
            .darkBackgroundColor(TextColor.fromString("#424242"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#7B3B3B"))
            .brightForegroundColor(TextColor.fromString("#B9D7D9"))
            .darkForegroundColor(TextColor.fromString("#668284"))
            .brightBackgroundColor(TextColor.fromString("#493736"))
            .darkBackgroundColor(TextColor.fromString("#2A2829"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#F21D41"))
            .brightForegroundColor(TextColor.fromString("#EBEBBC"))
            .darkForegroundColor(TextColor.fromString("#BCE3C5"))
            .brightBackgroundColor(TextColor.fromString("#82B3AE"))
            .darkBackgroundColor(TextColor.fromString("#230F2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#AB9597"))
            .brightForegroundColor(TextColor.fromString("#FFFBF0"))
            .darkForegroundColor(TextColor.fromString("#968F4B"))
            .brightBackgroundColor(TextColor.fromString("#7A6248"))
            .darkBackgroundColor(TextColor.fromString("#030506"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#95AC54"))
            .brightForegroundColor(TextColor.fromString("#F6F1CB"))
            .darkForegroundColor(TextColor.fromString("#DFD4A7"))
            .brightBackgroundColor(TextColor.fromString("#7F7C69"))
            .darkBackgroundColor(TextColor.fromString("#29210A"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#b86A6A"))
            .brightForegroundColor(TextColor.fromString("#F8EDD1"))
            .darkForegroundColor(TextColor.fromString("#C5CFC6"))
            .brightBackgroundColor(TextColor.fromString("#9D9D93"))
            .darkBackgroundColor(TextColor.fromString("#474843"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#E6324B"))
            .brightForegroundColor(TextColor.fromString("#F2E3C6"))
            .darkForegroundColor(TextColor.fromString("#FFC6A5"))
            .brightBackgroundColor(TextColor.fromString("#353634"))
            .darkBackgroundColor(TextColor.fromString("#2B2B2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/27905/threadless
     */
    THREADLESS(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#b24C2F"))
            .brightForegroundColor(TextColor.fromString("#E9F2F9"))
            .darkForegroundColor(TextColor.fromString("#9CC4E4"))
            .brightBackgroundColor(TextColor.fromString("#3A89C9"))
            .darkBackgroundColor(TextColor.fromString("#1B325F"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/38562/Hands_On
     */
    HANDS_ON(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#94BA65"))
            .brightForegroundColor(TextColor.fromString("#2790B0"))
            .darkForegroundColor(TextColor.fromString("#4B6E92"))
            .brightBackgroundColor(TextColor.fromString("#4E4D4A"))
            .darkBackgroundColor(TextColor.fromString("#353432"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(ColorThemeBuilder.newBuilder()
            .accentColor(TextColor.fromString("#e99A44"))
            .brightForegroundColor(TextColor.fromString("#EAE7D1"))
            .darkForegroundColor(TextColor.fromString("#CCC58E"))
            .brightBackgroundColor(TextColor.fromString("#7B8455"))
            .darkBackgroundColor(TextColor.fromString("#485C2B"))
            .build()),
    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#b58900"))
            .build()),

    SOLARIZED_DARK_ORANGE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_DARK_RED(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_DARK_MAGENTA(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#d33682"))
            .build()),

    SOLARIZED_DARK_VIOLET(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_DARK_BLUE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_DARK_CYAN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_DARK_GREEN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#859900"))
            .build()),

    SOLARIZED_LIGHT_YELLOW(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#b58900"))
            .build()),

    SOLARIZED_LIGHT_ORANGE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_LIGHT_RED(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_LIGHT_MAGENTA(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#d33682"))
            .build()),

    SOLARIZED_LIGHT_VIOLET(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_LIGHT_BLUE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_LIGHT_CYAN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_LIGHT_GREEN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TextColor.fromString("#859900"))
            .build());

    fun getTheme() = colorTheme

    enum class SolarizedBase(val colorThemeBuilder: ColorThemeBuilder) {
        SOLARIZED_DARK_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TextColor.fromString("#93a1a1"))
                .darkForegroundColor(TextColor.fromString("#839496"))
                .brightBackgroundColor(TextColor.fromString("#073642"))
                .darkBackgroundColor(TextColor.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TextColor.fromString("#657b83"))
                .darkForegroundColor(TextColor.fromString("#586e75"))
                .brightBackgroundColor(TextColor.fromString("#fdf6e3"))
                .darkBackgroundColor(TextColor.fromString("#eee8d5")))
    }
}
