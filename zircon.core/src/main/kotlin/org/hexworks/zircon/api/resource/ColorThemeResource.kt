package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

enum class ColorThemeResource(private val colorTheme: ColorTheme) {
    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#CFF09E"))
            .brightForegroundColor(TileColor.fromString("#A8DBA8"))
            .darkForegroundColor(TileColor.fromString("#79BD9A"))
            .brightBackgroundColor(TileColor.fromString("#3B8686"))
            .darkBackgroundColor(TileColor.fromString("#0B486B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#C5E0DC"))
            .brightForegroundColor(TileColor.fromString("#ECE5CE"))
            .darkForegroundColor(TileColor.fromString("#F1D4AF"))
            .brightBackgroundColor(TileColor.fromString("#E08E79"))
            .darkBackgroundColor(TileColor.fromString("#774F38"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/559428/lucky_bubble_gum
     */
    LUCKY_BUBBLE_GUM(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#E33258"))
            .brightForegroundColor(TileColor.fromString("#CCBF82"))
            .darkForegroundColor(TileColor.fromString("#B8AF03"))
            .brightBackgroundColor(TileColor.fromString("#67917A"))
            .darkBackgroundColor(TileColor.fromString("#170409"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#D1E751"))
            .brightForegroundColor(TileColor.fromString("#FFFFFF"))
            .darkForegroundColor(TileColor.fromString("#FFFFFF"))
            .brightBackgroundColor(TileColor.fromString("#26ADE4"))
            .darkBackgroundColor(TileColor.fromString("#000000"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#BF4D28"))
            .brightForegroundColor(TileColor.fromString("#F6F7BD"))
            .darkForegroundColor(TileColor.fromString("#E6AC27"))
            .brightBackgroundColor(TileColor.fromString("#80BCA3"))
            .darkBackgroundColor(TileColor.fromString("#655643"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#FF9900"))
            .brightForegroundColor(TileColor.fromString("#E9E9E9"))
            .darkForegroundColor(TileColor.fromString("#BCBCBC"))
            .brightBackgroundColor(TileColor.fromString("#3299BB"))
            .darkBackgroundColor(TileColor.fromString("#424242"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#7B3B3B"))
            .brightForegroundColor(TileColor.fromString("#B9D7D9"))
            .darkForegroundColor(TileColor.fromString("#668284"))
            .brightBackgroundColor(TileColor.fromString("#493736"))
            .darkBackgroundColor(TileColor.fromString("#2A2829"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#F21D41"))
            .brightForegroundColor(TileColor.fromString("#EBEBBC"))
            .darkForegroundColor(TileColor.fromString("#BCE3C5"))
            .brightBackgroundColor(TileColor.fromString("#82B3AE"))
            .darkBackgroundColor(TileColor.fromString("#230F2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#AB9597"))
            .brightForegroundColor(TileColor.fromString("#FFFBF0"))
            .darkForegroundColor(TileColor.fromString("#968F4B"))
            .brightBackgroundColor(TileColor.fromString("#7A6248"))
            .darkBackgroundColor(TileColor.fromString("#030506"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#95AC54"))
            .brightForegroundColor(TileColor.fromString("#F6F1CB"))
            .darkForegroundColor(TileColor.fromString("#DFD4A7"))
            .brightBackgroundColor(TileColor.fromString("#7F7C69"))
            .darkBackgroundColor(TileColor.fromString("#29210A"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#b86A6A"))
            .brightForegroundColor(TileColor.fromString("#F8EDD1"))
            .darkForegroundColor(TileColor.fromString("#C5CFC6"))
            .brightBackgroundColor(TileColor.fromString("#9D9D93"))
            .darkBackgroundColor(TileColor.fromString("#474843"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#E6324B"))
            .brightForegroundColor(TileColor.fromString("#F2E3C6"))
            .darkForegroundColor(TileColor.fromString("#FFC6A5"))
            .brightBackgroundColor(TileColor.fromString("#353634"))
            .darkBackgroundColor(TileColor.fromString("#2B2B2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/27905/threadless
     */
    THREADLESS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#b24C2F"))
            .brightForegroundColor(TileColor.fromString("#E9F2F9"))
            .darkForegroundColor(TileColor.fromString("#9CC4E4"))
            .brightBackgroundColor(TileColor.fromString("#3A89C9"))
            .darkBackgroundColor(TileColor.fromString("#1B325F"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/38562/Hands_On
     */
    HANDS_ON(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#94BA65"))
            .brightForegroundColor(TileColor.fromString("#2790B0"))
            .darkForegroundColor(TileColor.fromString("#4B6E92"))
            .brightBackgroundColor(TileColor.fromString("#4E4D4A"))
            .darkBackgroundColor(TileColor.fromString("#353432"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#e99A44"))
            .brightForegroundColor(TileColor.fromString("#EAE7D1"))
            .darkForegroundColor(TileColor.fromString("#CCC58E"))
            .brightBackgroundColor(TileColor.fromString("#7B8455"))
            .darkBackgroundColor(TileColor.fromString("#485C2B"))
            .build()),
    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_DARK_ORANGE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_DARK_RED(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_DARK_MAGENTA(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_DARK_VIOLET(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_DARK_BLUE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_DARK_CYAN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_DARK_GREEN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#859900"))
            .build()),

    SOLARIZED_LIGHT_YELLOW(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_LIGHT_ORANGE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_LIGHT_RED(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_LIGHT_MAGENTA(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_LIGHT_VIOLET(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_LIGHT_BLUE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_LIGHT_CYAN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_LIGHT_GREEN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .accentColor(TileColor.fromString("#859900"))
            .build());

    fun getTheme() = colorTheme

    enum class SolarizedBase(val colorThemeBuilder: ColorThemeBuilder) {
        SOLARIZED_DARK_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TileColor.fromString("#93a1a1"))
                .darkForegroundColor(TileColor.fromString("#839496"))
                .brightBackgroundColor(TileColor.fromString("#073642"))
                .darkBackgroundColor(TileColor.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .brightForegroundColor(TileColor.fromString("#657b83"))
                .darkForegroundColor(TileColor.fromString("#586e75"))
                .brightBackgroundColor(TileColor.fromString("#fdf6e3"))
                .darkBackgroundColor(TileColor.fromString("#eee8d5")))
    }
}
