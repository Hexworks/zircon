package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.colorTheme
import org.hexworks.zircon.api.color.TileColor.Companion.transparent
import org.hexworks.zircon.internal.resource.ColorThemeResource

object ColorThemes {

    /**
     * The empty theme has transparent colors for all colors.
     */
    fun empty() = colorTheme {
        name = "empty"
        accentColor = transparent()
        primaryForegroundColor = transparent()
        secondaryForegroundColor = transparent()
        primaryBackgroundColor = transparent()
        secondaryBackgroundColor = transparent()
    }

    /**
     * This is a null object for color themes indicating that no
     * theme is chosen.
     */
    fun defaultTheme() = ColorThemeResource.DEFAULT.getTheme()

    /**
     * This is the theme that was made by Hexworks.
     */
    fun hexworks() = ColorThemeResource.HEXWORKS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    fun adriftInDreams() = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    fun letThemEatCake() = ColorThemeResource.LET_THEM_EAT_CAKE.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/15/tech_light
     */
    fun techLight() = ColorThemeResource.TECH_LIGHT.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    fun headache() = ColorThemeResource.HEADACHE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    fun gamebookers() = ColorThemeResource.GAMEBOOKERS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    fun entrappedInAPalette() = ColorThemeResource.ENTRAPPED_IN_A_PALETTE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    fun war() = ColorThemeResource.WAR.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    fun capturedByPirates() = ColorThemeResource.CAPTURED_BY_PIRATES.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    fun ghostOfAChance() = ColorThemeResource.GHOST_OF_A_CHANCE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    fun afterTheHeist() = ColorThemeResource.AFTER_THE_HEIST.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    fun pabloNeruda() = ColorThemeResource.PABLO_NERUDA.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    fun oliveLeafTea() = ColorThemeResource.OLIVE_LEAF_TEA.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    fun cyberpunk() = ColorThemeResource.CYBERPUNK.getTheme()

    // These come from the Slack themes: https://slackthemes.net
    fun afterglow() = ColorThemeResource.AFTERGLOW.getTheme()

    fun amigaOs() = ColorThemeResource.AMIGA_OS.getTheme()

    fun ancestry() = ColorThemeResource.ANCESTRY.getTheme()

    fun arc() = ColorThemeResource.ARC.getTheme()

    fun forest() = ColorThemeResource.FOREST.getTheme()

    fun linuxMintDark() = ColorThemeResource.LINUX_MINT_DARK.getTheme()

    fun nord() = ColorThemeResource.NORD.getTheme()

    fun tron() = ColorThemeResource.TRON.getTheme()

    fun saiku() = ColorThemeResource.SAIKU.getTheme()

    fun ingressResistance() = ColorThemeResource.INGRESS_RESISTANCE.getTheme()

    fun ingressEnlightened() = ColorThemeResource.INGRESS_ENLIGHTENED.getTheme()

    /**
     * Taken from the official Zenburn page.
     */
    fun zenburnVanilla() = ColorThemeResource.ZENBURN_VANILLA.getTheme()

    fun zenburnPink() = ColorThemeResource.ZENBURN_PINK.getTheme()

    fun zenburnGreen() = ColorThemeResource.ZENBURN_GREEN.getTheme()

    //Taken form the official Monokai page.

    fun monokaiYellow() = ColorThemeResource.MONOKAI_YELLOW.getTheme()

    fun monokaiPink() = ColorThemeResource.MONOKAI_PINK.getTheme()

    fun monokaiGreen() = ColorThemeResource.MONOKAI_GREEN.getTheme()

    fun monokaiOrange() = ColorThemeResource.MONOKAI_ORANGE.getTheme()

    fun monokaiViolet() = ColorThemeResource.MONOKAI_VIOLET.getTheme()

    fun monokaiBlue() = ColorThemeResource.MONOKAI_BLUE.getTheme()

    // All Solarized themes are taken from:
    // http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/

    fun solarizedDarkYellow() = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme()

    fun solarizedDarkOrange() = ColorThemeResource.SOLARIZED_DARK_ORANGE.getTheme()

    fun solarizedDarkRed() = ColorThemeResource.SOLARIZED_DARK_RED.getTheme()

    fun solarizedDarkMagenta() = ColorThemeResource.SOLARIZED_DARK_MAGENTA.getTheme()

    fun solarizedDarkViolet() = ColorThemeResource.SOLARIZED_DARK_VIOLET.getTheme()

    fun solarizedDarkBlue() = ColorThemeResource.SOLARIZED_DARK_BLUE.getTheme()

    fun solarizedDarkCyan() = ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme()

    fun solarizedDarkGreen() = ColorThemeResource.SOLARIZED_DARK_GREEN.getTheme()

    fun solarizedLightYellow() = ColorThemeResource.SOLARIZED_LIGHT_YELLOW.getTheme()

    fun solarizedLightOrange() = ColorThemeResource.SOLARIZED_LIGHT_ORANGE.getTheme()

    fun solarizedLightRed() = ColorThemeResource.SOLARIZED_LIGHT_RED.getTheme()

    fun solarizedLightMagenta() = ColorThemeResource.SOLARIZED_LIGHT_MAGENTA.getTheme()

    fun solarizedLightViolet() = ColorThemeResource.SOLARIZED_LIGHT_VIOLET.getTheme()

    fun solarizedLightBlue() = ColorThemeResource.SOLARIZED_LIGHT_BLUE.getTheme()

    fun solarizedLightCyan() = ColorThemeResource.SOLARIZED_LIGHT_CYAN.getTheme()

    fun solarizedLightGreen() = ColorThemeResource.SOLARIZED_LIGHT_GREEN.getTheme()

    fun staleSunset() = ColorThemeResource.STALE_SUNSET.getTheme()

    fun afternoonHaze() = ColorThemeResource.AFTERNOON_HAZE.getTheme()

    fun winterWonderland() = ColorThemeResource.WINTER_WONDERLAND.getTheme()

    fun petite() = ColorThemeResource.PETITE.getTheme()

    fun waverator() = ColorThemeResource.WAVERATOR.getTheme()

    fun justparchment() = ColorThemeResource.JUSTPARCHMENT.getTheme()

    fun ammo() = ColorThemeResource.AMMO.getTheme()

    fun nyx() = ColorThemeResource.NYX.getTheme()

    fun slso() = ColorThemeResource.SLSO.getTheme()

    fun beyondTheSea() = ColorThemeResource.BEYOND_THE_SEA.getTheme()

    fun forestGlow() = ColorThemeResource.FOREST_GLOW.getTheme()

    fun cherryBear() = ColorThemeResource.CHERRY_BEAR.getTheme()

    fun supernova() = ColorThemeResource.SUPERNOVA.getTheme()

    fun rust() = ColorThemeResource.RUST.getTheme()

    fun cursedTurkey() = ColorThemeResource.CURSED_TURKEY.getTheme()

    fun molten() = ColorThemeResource.MOLTEN.getTheme()

    fun stormyRed() = ColorThemeResource.STORMY_RED.getTheme()

    fun stormyGreen() = ColorThemeResource.STORMY_GREEN.getTheme()

    fun oil() = ColorThemeResource.OIL.getTheme()

    fun naturesAtmosphere() = ColorThemeResource.NATURES_ATMOSPHERE.getTheme()

    fun infiniteIkea() = ColorThemeResource.INFINITE_IKEA.getTheme()

    fun fireWeeds() = ColorThemeResource.FIRE_WEEDS.getTheme()

    fun pola() = ColorThemeResource.POLA.getTheme()

    fun spanishSunset() = ColorThemeResource.SPANISH_SUNSET.getTheme()

    fun discord() = ColorThemeResource.DISCORD.getTheme()

    fun afterDark() = ColorThemeResource.AFTER_DARK.getTheme()

}
