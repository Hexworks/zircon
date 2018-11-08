package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.api.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

enum class ColorThemeResource(private val colorTheme: ColorTheme) {

    EMPTY(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.transparent())
            .withPrimaryForegroundColor(TileColor.transparent())
            .withSecondaryForegroundColor(TileColor.transparent())
            .withPrimaryBackgroundColor(TileColor.transparent())
            .withSecondaryBackgroundColor(TileColor.transparent())
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#CFF09E"))
            .withPrimaryForegroundColor(TileColor.fromString("#A8DBA8"))
            .withSecondaryForegroundColor(TileColor.fromString("#79BD9A"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3B8686"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0B486B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#C5E0DC"))
            .withPrimaryForegroundColor(TileColor.fromString("#ECE5CE"))
            .withSecondaryForegroundColor(TileColor.fromString("#F1D4AF"))
            .withPrimaryBackgroundColor(TileColor.fromString("#E08E79"))
            .withSecondaryBackgroundColor(TileColor.fromString("#774F38"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#D1E751"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withPrimaryBackgroundColor(TileColor.fromString("#26ADE4"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#BF4D28"))
            .withPrimaryForegroundColor(TileColor.fromString("#F6F7BD"))
            .withSecondaryForegroundColor(TileColor.fromString("#E6AC27"))
            .withPrimaryBackgroundColor(TileColor.fromString("#80BCA3"))
            .withSecondaryBackgroundColor(TileColor.fromString("#655643"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#FF9900"))
            .withPrimaryForegroundColor(TileColor.fromString("#E9E9E9"))
            .withSecondaryForegroundColor(TileColor.fromString("#BCBCBC"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3299BB"))
            .withSecondaryBackgroundColor(TileColor.fromString("#424242"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#7B3B3B"))
            .withPrimaryForegroundColor(TileColor.fromString("#B9D7D9"))
            .withSecondaryForegroundColor(TileColor.fromString("#668284"))
            .withPrimaryBackgroundColor(TileColor.fromString("#493736"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2A2829"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#F21D41"))
            .withPrimaryForegroundColor(TileColor.fromString("#EBEBBC"))
            .withSecondaryForegroundColor(TileColor.fromString("#BCE3C5"))
            .withPrimaryBackgroundColor(TileColor.fromString("#82B3AE"))
            .withSecondaryBackgroundColor(TileColor.fromString("#230F2B"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#AB9597"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFBF0"))
            .withSecondaryForegroundColor(TileColor.fromString("#968F4B"))
            .withPrimaryBackgroundColor(TileColor.fromString("#7A6248"))
            .withSecondaryBackgroundColor(TileColor.fromString("#030506"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#95AC54"))
            .withPrimaryForegroundColor(TileColor.fromString("#F6F1CB"))
            .withSecondaryForegroundColor(TileColor.fromString("#DFD4A7"))
            .withPrimaryBackgroundColor(TileColor.fromString("#7F7C69"))
            .withSecondaryBackgroundColor(TileColor.fromString("#29210A"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#b86A6A"))
            .withPrimaryForegroundColor(TileColor.fromString("#F8EDD1"))
            .withSecondaryForegroundColor(TileColor.fromString("#C5CFC6"))
            .withPrimaryBackgroundColor(TileColor.fromString("#9D9D93"))
            .withSecondaryBackgroundColor(TileColor.fromString("#474843"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#E6324B"))
            .withPrimaryForegroundColor(TileColor.fromString("#F2E3C6"))
            .withSecondaryForegroundColor(TileColor.fromString("#FFC6A5"))
            .withPrimaryBackgroundColor(TileColor.fromString("#353634"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2B2B2B"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#e99A44"))
            .withPrimaryForegroundColor(TileColor.fromString("#EAE7D1"))
            .withSecondaryForegroundColor(TileColor.fromString("#CCC58E"))
            .withPrimaryBackgroundColor(TileColor.fromString("#7B8455"))
            .withSecondaryBackgroundColor(TileColor.fromString("#485C2B"))
            .build()),

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    CYBERPUNK(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#61D6C4"))
            .withPrimaryForegroundColor(TileColor.fromString("#71918C"))
            .withSecondaryForegroundColor(TileColor.fromString("#3D615F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#25343B"))
            .withSecondaryBackgroundColor(TileColor.fromString("#212429"))
            .build()),

    // these are slack themes

    AFTERGLOW(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ADBA4E"))
            .withPrimaryForegroundColor(TileColor.fromString("#DEDEDE"))
            .withSecondaryForegroundColor(TileColor.fromString("#D2D6D6"))
            .withPrimaryBackgroundColor(TileColor.fromString("#2F2C2F"))
            .withSecondaryBackgroundColor(TileColor.fromString("#252525"))
            .build()),

    AMIGA_OS(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#F08000"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#0050A0"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000020"))
            .build()),

    ANCESTRY(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#F3B670"))
            .withPrimaryForegroundColor(TileColor.fromString("#9CBE30"))
            .withSecondaryForegroundColor(TileColor.fromString("#7A9C0F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#706B63"))
            .withSecondaryBackgroundColor(TileColor.fromString("#534D46"))
            .build()),

    ARC(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#5294E2"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#4A5664"))
            .withSecondaryBackgroundColor(TileColor.fromString("#303641"))
            .build()),

    FOREST(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#94E864"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#077A07"))
            .withSecondaryBackgroundColor(TileColor.fromString("#033313"))
            .build()),

    LINUX_MINT_DARK(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#8FA876"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#818181"))
            .withPrimaryBackgroundColor(TileColor.fromString("#353535"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2F2F2F"))
            .build()),

    NORD(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#A3BE8C"))
            .withPrimaryForegroundColor(TileColor.fromString("#D8DEE9"))
            .withSecondaryForegroundColor(TileColor.fromString("#81A1C1"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3B4252"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2E3440"))
            .build()),

    TRON(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#1EB8EB"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#424242"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),

    SAIKU(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#cccccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#999999"))
            .withPrimaryBackgroundColor(TileColor.fromString("#AE1817"))
            .withSecondaryBackgroundColor(TileColor.fromString("#232323"))
            .build()),

    INGRESS_RESISTANCE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#F1C248"))
            .withPrimaryForegroundColor(TileColor.fromString("#34EAF5"))
            .withSecondaryForegroundColor(TileColor.fromString("#0492D0"))
            .withPrimaryBackgroundColor(TileColor.fromString("#393218"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    INGRESS_ENLIGHTENED(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#02BF02"))
            .withPrimaryForegroundColor(TileColor.fromString("#34EAF5"))
            .withSecondaryForegroundColor(TileColor.fromString("#0492D0"))
            .withPrimaryBackgroundColor(TileColor.fromString("#393218"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    ZENBURN_VANILLA(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#f0dfaf"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_PINK(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ecbcbc"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_GREEN(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#709080"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    MONOKAI_YELLOW(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ffd866"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_PINK(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ff6188"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_GREEN(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#a9dc76"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_ORANGE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#fc9867"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_VIOLET(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#ab9df2"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_BLUE(ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.fromString("#78dce8"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_DARK_ORANGE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_DARK_RED(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_DARK_MAGENTA(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_DARK_VIOLET(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_DARK_BLUE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_DARK_CYAN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_DARK_GREEN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#859900"))
            .build()),

    SOLARIZED_LIGHT_YELLOW(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_LIGHT_ORANGE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_LIGHT_RED(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_LIGHT_MAGENTA(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_LIGHT_VIOLET(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_LIGHT_BLUE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_LIGHT_CYAN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_LIGHT_GREEN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withAccentColor(TileColor.fromString("#859900"))
            .build());

    fun getTheme() = colorTheme

    enum class SolarizedBase(val colorThemeBuilder: ColorThemeBuilder) {
        SOLARIZED_DARK_BASE(ColorThemeBuilder.newBuilder()
                .withPrimaryForegroundColor(TileColor.fromString("#fdf6e3"))
                .withSecondaryForegroundColor(TileColor.fromString("#eee8d5"))
                .withPrimaryBackgroundColor(TileColor.fromString("#073642"))
                .withSecondaryBackgroundColor(TileColor.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .withPrimaryForegroundColor(TileColor.fromString("#002b36"))
                .withSecondaryForegroundColor(TileColor.fromString("#586e75"))
                .withPrimaryBackgroundColor(TileColor.fromString("#fdf6e3"))
                .withSecondaryBackgroundColor(TileColor.fromString("#eee8d5")))
    }
}
