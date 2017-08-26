package org.codetome.zircon.api.component

import org.codetome.zircon.api.component.builder.ThemeBuilder
import org.codetome.zircon.api.factory.TextColorFactory

enum class ThemeRepository(private val theme: Theme) {

    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ThemeBuilder.newBuilder()
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
    LET_THEM_EAT_CAKE(ThemeBuilder.newBuilder()
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
    LUCKY_BUBBLE_GUM(ThemeBuilder.newBuilder()
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
    TECH_LIGHT(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#D1E751"))
            .brightForegroundColor(TextColorFactory.fromString("#FFFFFF"))
            .darkForegroundColor(TextColorFactory.fromString("#FFFFFF"))
            .brightBackgroundColor(TextColorFactory.fromString("#26ADE4"))
            .darkBackgroundColor(TextColorFactory.fromString("#000000"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/940086/mystery_machine
     */
    MYSTERY_MACHINE(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#F77825"))
            .brightForegroundColor(TextColorFactory.fromString("#F1EFA5"))
            .darkForegroundColor(TextColorFactory.fromString("#D3CE3D"))
            .brightBackgroundColor(TextColorFactory.fromString("#60B99A"))
            .darkBackgroundColor(TextColorFactory.fromString("#554236"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#BF4D28"))
            .brightForegroundColor(TextColorFactory.fromString("#F6F7BD"))
            .darkForegroundColor(TextColorFactory.fromString("#E6AC27"))
            .brightBackgroundColor(TextColorFactory.fromString("#80BCA3"))
            .darkBackgroundColor(TextColorFactory.fromString("#655643"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/46688/fresh_cut_day
     */
    FRESH_CUT_DAY(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#F9F2E7"))
            .brightForegroundColor(TextColorFactory.fromString("#8FBE00"))
            .darkForegroundColor(TextColorFactory.fromString("#AEE239"))
            .brightBackgroundColor(TextColorFactory.fromString("#40C0CB"))
            .darkBackgroundColor(TextColorFactory.fromString("#00A8C6"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(ThemeBuilder.newBuilder()
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
    ENTRAPPED_IN_A_PALETTE(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#7B3B3B"))
            .brightForegroundColor(TextColorFactory.fromString("#B9D7D9"))
            .darkForegroundColor(TextColorFactory.fromString("#668284"))
            .brightBackgroundColor(TextColorFactory.fromString("#493736"))
            .darkBackgroundColor(TextColorFactory.fromString("#2A2829"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/444487/Curiosity_Killed
     */
    CURIOSITY_KILLED(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#99173C"))
            .brightForegroundColor(TextColorFactory.fromString("#EFFFCD"))
            .darkForegroundColor(TextColorFactory.fromString("#DCE9BE"))
            .brightBackgroundColor(TextColorFactory.fromString("#555152"))
            .darkBackgroundColor(TextColorFactory.fromString("#2E2633"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#F21D41"))
            .brightForegroundColor(TextColorFactory.fromString("#EBEBBC"))
            .darkForegroundColor(TextColorFactory.fromString("#BCE3C5"))
            .brightBackgroundColor(TextColorFactory.fromString("#82B3AE"))
            .darkBackgroundColor(TextColorFactory.fromString("#230F2B"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/634148/Thumbelina
     */
    THUMBELINA(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#AB526B"))
            .brightForegroundColor(TextColorFactory.fromString("#F0E2A4"))
            .darkForegroundColor(TextColorFactory.fromString("#F4EBC3"))
            .brightBackgroundColor(TextColorFactory.fromString("#C5CEAE"))
            .darkBackgroundColor(TextColorFactory.fromString("#BCA297"))
            .build()),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(ThemeBuilder.newBuilder()
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
    GHOST_OF_A_CHANCE(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#95AC54"))
            .brightForegroundColor(TextColorFactory.fromString("#F6F1CB"))
            .darkForegroundColor(TextColorFactory.fromString("#DFD4A7"))
            .brightBackgroundColor(TextColorFactory.fromString("#7F7C69"))
            .darkBackgroundColor(TextColorFactory.fromString("#29210A"))
            .build()),

    /**
     * Taken from
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_ORANGE(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#cb4b16"))
            .brightForegroundColor(TextColorFactory.fromString("#fdf6e3"))
            .darkForegroundColor(TextColorFactory.fromString("#eee8d5"))
            .brightBackgroundColor(TextColorFactory.fromString("#586e75"))
            .darkBackgroundColor(TextColorFactory.fromString("#002b36"))
            .build()),

    /**
     * Taken from
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(ThemeBuilder.newBuilder()
            .accentColor(TextColorFactory.fromString("#b58900"))
            .brightForegroundColor(TextColorFactory.fromString("#fdf6e3"))
            .darkForegroundColor(TextColorFactory.fromString("#eee8d5"))
            .brightBackgroundColor(TextColorFactory.fromString("#586e75"))
            .darkBackgroundColor(TextColorFactory.fromString("#002b36"))
            .build())


    ;

    fun getTheme() = theme
}