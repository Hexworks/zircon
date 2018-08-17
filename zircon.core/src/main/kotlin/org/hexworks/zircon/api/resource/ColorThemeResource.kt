package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

enum class ColorThemeResource(private val colorTheme: ColorTheme) {
    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#CFF09E"))
            .primaryForegroundColor(TileColor.fromString("#A8DBA8"))
            .secondaryForegroundColor(TileColor.fromString("#79BD9A"))
            .primaryBackgroundColor(TileColor.fromString("#3B8686"))
            .secondaryBackgroundColor(TileColor.fromString("#0B486B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#C5E0DC"))
            .primaryForegroundColor(TileColor.fromString("#ECE5CE"))
            .secondaryForegroundColor(TileColor.fromString("#F1D4AF"))
            .primaryBackgroundColor(TileColor.fromString("#E08E79"))
            .secondaryBackgroundColor(TileColor.fromString("#774F38"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#D1E751"))
            .primaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .secondaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .primaryBackgroundColor(TileColor.fromString("#26ADE4"))
            .secondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#BF4D28"))
            .primaryForegroundColor(TileColor.fromString("#F6F7BD"))
            .secondaryForegroundColor(TileColor.fromString("#E6AC27"))
            .primaryBackgroundColor(TileColor.fromString("#80BCA3"))
            .secondaryBackgroundColor(TileColor.fromString("#655643"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#FF9900"))
            .primaryForegroundColor(TileColor.fromString("#E9E9E9"))
            .secondaryForegroundColor(TileColor.fromString("#BCBCBC"))
            .primaryBackgroundColor(TileColor.fromString("#3299BB"))
            .secondaryBackgroundColor(TileColor.fromString("#424242"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#7B3B3B"))
            .primaryForegroundColor(TileColor.fromString("#B9D7D9"))
            .secondaryForegroundColor(TileColor.fromString("#668284"))
            .primaryBackgroundColor(TileColor.fromString("#493736"))
            .secondaryBackgroundColor(TileColor.fromString("#2A2829"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#F21D41"))
            .primaryForegroundColor(TileColor.fromString("#EBEBBC"))
            .secondaryForegroundColor(TileColor.fromString("#BCE3C5"))
            .primaryBackgroundColor(TileColor.fromString("#82B3AE"))
            .secondaryBackgroundColor(TileColor.fromString("#230F2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#AB9597"))
            .primaryForegroundColor(TileColor.fromString("#FFFBF0"))
            .secondaryForegroundColor(TileColor.fromString("#968F4B"))
            .primaryBackgroundColor(TileColor.fromString("#7A6248"))
            .secondaryBackgroundColor(TileColor.fromString("#030506"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#95AC54"))
            .primaryForegroundColor(TileColor.fromString("#F6F1CB"))
            .secondaryForegroundColor(TileColor.fromString("#DFD4A7"))
            .primaryBackgroundColor(TileColor.fromString("#7F7C69"))
            .secondaryBackgroundColor(TileColor.fromString("#29210A"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#b86A6A"))
            .primaryForegroundColor(TileColor.fromString("#F8EDD1"))
            .secondaryForegroundColor(TileColor.fromString("#C5CFC6"))
            .primaryBackgroundColor(TileColor.fromString("#9D9D93"))
            .secondaryBackgroundColor(TileColor.fromString("#474843"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#E6324B"))
            .primaryForegroundColor(TileColor.fromString("#F2E3C6"))
            .secondaryForegroundColor(TileColor.fromString("#FFC6A5"))
            .primaryBackgroundColor(TileColor.fromString("#353634"))
            .secondaryBackgroundColor(TileColor.fromString("#2B2B2B"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#e99A44"))
            .primaryForegroundColor(TileColor.fromString("#EAE7D1"))
            .secondaryForegroundColor(TileColor.fromString("#CCC58E"))
            .primaryBackgroundColor(TileColor.fromString("#7B8455"))
            .secondaryBackgroundColor(TileColor.fromString("#485C2B"))
            .build()),


    AFTERGLOW(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ADBA4E"))
            .primaryForegroundColor(TileColor.fromString("#DEDEDE"))
            .secondaryForegroundColor(TileColor.fromString("#D2D6D6"))
            .primaryBackgroundColor(TileColor.fromString("#2F2C2F"))
            .secondaryBackgroundColor(TileColor.fromString("#252525"))
            .build()),

    AMIGA_OS(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#F08000"))
            .primaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .secondaryForegroundColor(TileColor.fromString("#dddddd"))
            .primaryBackgroundColor(TileColor.fromString("#0050A0"))
            .secondaryBackgroundColor(TileColor.fromString("#000020"))
            .build()),

    ANCESTRY(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#F3B670"))
            .primaryForegroundColor(TileColor.fromString("#9CBE30"))
            .secondaryForegroundColor(TileColor.fromString("#7A9C0F"))
            .primaryBackgroundColor(TileColor.fromString("#706B63"))
            .secondaryBackgroundColor(TileColor.fromString("#534D46"))
            .build()),

    ARC(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#5294E2"))
            .primaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .secondaryForegroundColor(TileColor.fromString("#dddddd"))
            .primaryBackgroundColor(TileColor.fromString("#4A5664"))
            .secondaryBackgroundColor(TileColor.fromString("#303641"))
            .build()),

    FOREST(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#94E864"))
            .primaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .secondaryForegroundColor(TileColor.fromString("#dddddd"))
            .primaryBackgroundColor(TileColor.fromString("#077A07"))
            .secondaryBackgroundColor(TileColor.fromString("#033313"))
            .build()),

    LINUX_MINT_DARK(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#8FA876"))
            .primaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .secondaryForegroundColor(TileColor.fromString("#818181"))
            .primaryBackgroundColor(TileColor.fromString("#353535"))
            .secondaryBackgroundColor(TileColor.fromString("#2F2F2F"))
            .build()),

    NORD(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#A3BE8C"))
            .primaryForegroundColor(TileColor.fromString("#D8DEE9"))
            .secondaryForegroundColor(TileColor.fromString("#81A1C1"))
            .primaryBackgroundColor(TileColor.fromString("#3B4252"))
            .secondaryBackgroundColor(TileColor.fromString("#2E3440"))
            .build()),

    TRON(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#1EB8EB"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#dddddd"))
            .primaryBackgroundColor(TileColor.fromString("#424242"))
            .secondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),

    SAIKU(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ffffff"))
            .primaryForegroundColor(TileColor.fromString("#cccccc"))
            .secondaryForegroundColor(TileColor.fromString("#999999"))
            .primaryBackgroundColor(TileColor.fromString("#AE1817"))
            .secondaryBackgroundColor(TileColor.fromString("#232323"))
            .build()),

    INGRESS_RESISTANCE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#F1C248"))
            .primaryForegroundColor(TileColor.fromString("#34EAF5"))
            .secondaryForegroundColor(TileColor.fromString("#0492D0"))
            .primaryBackgroundColor(TileColor.fromString("#393218"))
            .secondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    INGRESS_ENLIGHTENED(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#02BF02"))
            .primaryForegroundColor(TileColor.fromString("#34EAF5"))
            .secondaryForegroundColor(TileColor.fromString("#0492D0"))
            .primaryBackgroundColor(TileColor.fromString("#393218"))
            .secondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    ZENBURN_VANILLA(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#f0dfaf"))
            .primaryForegroundColor(TileColor.fromString("#dcdccc"))
            .secondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .primaryBackgroundColor(TileColor.fromString("#333333"))
            .secondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_PINK(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ecbcbc"))
            .primaryForegroundColor(TileColor.fromString("#dcdccc"))
            .secondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .primaryBackgroundColor(TileColor.fromString("#333333"))
            .secondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_GREEN(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#709080"))
            .primaryForegroundColor(TileColor.fromString("#dcdccc"))
            .secondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .primaryBackgroundColor(TileColor.fromString("#333333"))
            .secondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    MONOKAI_YELLOW(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ffd866"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_PINK(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ff6188"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_GREEN(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#a9dc76"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_ORANGE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#fc9867"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_VIOLET(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#ab9df2"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_BLUE(ColorThemeBuilder.newBuilder()
            .accentColor(TileColor.fromString("#78dce8"))
            .primaryForegroundColor(TileColor.fromString("#ffffff"))
            .secondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .primaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .secondaryBackgroundColor(TileColor.fromString("#2c292d"))
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
                .primaryForegroundColor(TileColor.fromString("#93a1a1"))
                .secondaryForegroundColor(TileColor.fromString("#839496"))
                .primaryBackgroundColor(TileColor.fromString("#073642"))
                .secondaryBackgroundColor(TileColor.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .primaryForegroundColor(TileColor.fromString("#657b83"))
                .secondaryForegroundColor(TileColor.fromString("#586e75"))
                .primaryBackgroundColor(TileColor.fromString("#fdf6e3"))
                .secondaryBackgroundColor(TileColor.fromString("#eee8d5")))
    }
}
