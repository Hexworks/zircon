@file:Suppress("unused")

package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.resource.ColorThemeResource
import kotlin.jvm.JvmStatic

object ColorThemes {

    @JvmStatic
    fun newBuilder() = ColorThemeBuilder()

    /**
     * The empty theme has transparent colors for all colors.
     */
    @JvmStatic
    fun empty() = ColorThemeBuilder.newBuilder()
            .withAccentColor(TileColor.transparent())
            .withPrimaryForegroundColor(TileColor.transparent())
            .withSecondaryForegroundColor(TileColor.transparent())
            .withPrimaryBackgroundColor(TileColor.transparent())
            .withSecondaryBackgroundColor(TileColor.transparent())
            .build()

    /**
     * This is a null object for color themes indicating that no
     * theme is chosen.
     */
    @JvmStatic
    fun defaultTheme() = ColorThemeResource.DEFAULT.getTheme()

    /**
     * This is the theme that was made by Hexworks.
     */
    @JvmStatic
    fun hexworks() = ColorThemeResource.HEXWORKS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    @JvmStatic
    fun adriftInDreams() = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    @JvmStatic
    fun letThemEatCake() = ColorThemeResource.LET_THEM_EAT_CAKE.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/15/tech_light
     */
    @JvmStatic
    fun techLight() = ColorThemeResource.TECH_LIGHT.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    @JvmStatic
    fun headache() = ColorThemeResource.HEADACHE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    @JvmStatic
    fun gamebookers() = ColorThemeResource.GAMEBOOKERS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    @JvmStatic
    fun entrappedInAPalette() = ColorThemeResource.ENTRAPPED_IN_A_PALETTE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    @JvmStatic
    fun war() = ColorThemeResource.WAR.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    @JvmStatic
    fun capturedByPirates() = ColorThemeResource.CAPTURED_BY_PIRATES.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    @JvmStatic
    fun ghostOfAChance() = ColorThemeResource.GHOST_OF_A_CHANCE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    @JvmStatic
    fun afterTheHeist() = ColorThemeResource.AFTER_THE_HEIST.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    @JvmStatic
    fun pabloNeruda() = ColorThemeResource.PABLO_NERUDA.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    @JvmStatic
    fun oliveLeafTea() = ColorThemeResource.OLIVE_LEAF_TEA.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    @JvmStatic
    fun cyberpunk() = ColorThemeResource.CYBERPUNK.getTheme()

    // These come from the Slack themes: https://slackthemes.net
    @JvmStatic
    fun afterglow() = ColorThemeResource.AFTERGLOW.getTheme()

    @JvmStatic
    fun amigaOs() = ColorThemeResource.AMIGA_OS.getTheme()

    @JvmStatic
    fun ancestry() = ColorThemeResource.ANCESTRY.getTheme()

    @JvmStatic
    fun arc() = ColorThemeResource.ARC.getTheme()

    @JvmStatic
    fun forest() = ColorThemeResource.FOREST.getTheme()

    @JvmStatic
    fun linuxMintDark() = ColorThemeResource.LINUX_MINT_DARK.getTheme()

    @JvmStatic
    fun nord() = ColorThemeResource.NORD.getTheme()

    @JvmStatic
    fun tron() = ColorThemeResource.TRON.getTheme()

    @JvmStatic
    fun saiku() = ColorThemeResource.SAIKU.getTheme()

    @JvmStatic
    fun ingressResistance() = ColorThemeResource.INGRESS_RESISTANCE.getTheme()

    @JvmStatic
    fun ingressEnlightened() = ColorThemeResource.INGRESS_ENLIGHTENED.getTheme()

    /**
     * Taken from the official Zenburn page.
     */
    @JvmStatic
    fun zenburnVanilla() = ColorThemeResource.ZENBURN_VANILLA.getTheme()

    @JvmStatic
    fun zenburnPink() = ColorThemeResource.ZENBURN_PINK.getTheme()

    @JvmStatic
    fun zenburnGreen() = ColorThemeResource.ZENBURN_GREEN.getTheme()

    //Taken form the official Monokai page.

    @JvmStatic
    fun monokaiYellow() = ColorThemeResource.MONOKAI_YELLOW.getTheme()

    @JvmStatic
    fun monokaiPink() = ColorThemeResource.MONOKAI_PINK.getTheme()

    @JvmStatic
    fun monokaiGreen() = ColorThemeResource.MONOKAI_GREEN.getTheme()

    @JvmStatic
    fun monokaiOrange() = ColorThemeResource.MONOKAI_ORANGE.getTheme()

    @JvmStatic
    fun monokaiViolet() = ColorThemeResource.MONOKAI_VIOLET.getTheme()

    @JvmStatic
    fun monokaiBlue() = ColorThemeResource.MONOKAI_BLUE.getTheme()

    // All Solarized themes are taken from:
    // http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/

    @JvmStatic
    fun solarizedDarkYellow() = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme()

    @JvmStatic
    fun solarizedDarkOrange() = ColorThemeResource.SOLARIZED_DARK_ORANGE.getTheme()

    @JvmStatic
    fun solarizedDarkRed() = ColorThemeResource.SOLARIZED_DARK_RED.getTheme()

    @JvmStatic
    fun solarizedDarkMagenta() = ColorThemeResource.SOLARIZED_DARK_MAGENTA.getTheme()

    @JvmStatic
    fun solarizedDarkViolet() = ColorThemeResource.SOLARIZED_DARK_VIOLET.getTheme()

    @JvmStatic
    fun solarizedDarkBlue() = ColorThemeResource.SOLARIZED_DARK_BLUE.getTheme()

    @JvmStatic
    fun solarizedDarkCyan() = ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme()

    @JvmStatic
    fun solarizedDarkGreen() = ColorThemeResource.SOLARIZED_DARK_GREEN.getTheme()

    @JvmStatic
    fun solarizedLightYellow() = ColorThemeResource.SOLARIZED_LIGHT_YELLOW.getTheme()

    @JvmStatic
    fun solarizedLightOrange() = ColorThemeResource.SOLARIZED_LIGHT_ORANGE.getTheme()

    @JvmStatic
    fun solarizedLightRed() = ColorThemeResource.SOLARIZED_LIGHT_RED.getTheme()

    @JvmStatic
    fun solarizedLightMagenta() = ColorThemeResource.SOLARIZED_LIGHT_MAGENTA.getTheme()

    @JvmStatic
    fun solarizedLightViolet() = ColorThemeResource.SOLARIZED_LIGHT_VIOLET.getTheme()

    @JvmStatic
    fun solarizedLightBlue() = ColorThemeResource.SOLARIZED_LIGHT_BLUE.getTheme()

    @JvmStatic
    fun solarizedLightCyan() = ColorThemeResource.SOLARIZED_LIGHT_CYAN.getTheme()

    @JvmStatic
    fun solarizedLightGreen() = ColorThemeResource.SOLARIZED_LIGHT_GREEN.getTheme()

    @JvmStatic
    fun staleSunset() = ColorThemeResource.STALE_SUNSET.getTheme()

    @JvmStatic
    fun afternoonHaze() = ColorThemeResource.AFTERNOON_HAZE.getTheme()

    @JvmStatic
    fun winterWonderland() = ColorThemeResource.WINTER_WONDERLAND.getTheme()

    @JvmStatic
    fun petite() = ColorThemeResource.PETITE.getTheme()

    @JvmStatic
    fun waverator() = ColorThemeResource.WAVERATOR.getTheme()

    @JvmStatic
    fun justparchment() = ColorThemeResource.JUSTPARCHMENT.getTheme()

    @JvmStatic
    fun ammo() = ColorThemeResource.AMMO.getTheme()

    @JvmStatic
    fun nyx() = ColorThemeResource.NYX.getTheme()

    @JvmStatic
    fun slso() = ColorThemeResource.SLSO.getTheme()

    @JvmStatic
    fun beyondTheSea() = ColorThemeResource.BEYOND_THE_SEA.getTheme()

    @JvmStatic
    fun forestGlow() = ColorThemeResource.FOREST_GLOW.getTheme()

    @JvmStatic
    fun cherryBear() = ColorThemeResource.CHERRY_BEAR.getTheme()

    @JvmStatic
    fun supernova() = ColorThemeResource.SUPERNOVA.getTheme()

    @JvmStatic
    fun rust() = ColorThemeResource.RUST.getTheme()

    @JvmStatic
    fun cursedTurkey() = ColorThemeResource.CURSED_TURKEY.getTheme()

    @JvmStatic
    fun molten() = ColorThemeResource.MOLTEN.getTheme()

    @JvmStatic
    fun stormyRed() = ColorThemeResource.STORMY_RED.getTheme()

    @JvmStatic
    fun stormyGreen() = ColorThemeResource.STORMY_GREEN.getTheme()

    @JvmStatic
    fun oil() = ColorThemeResource.OIL.getTheme()

    @JvmStatic
    fun naturesAtmosphere() = ColorThemeResource.NATURES_ATMOSPHERE.getTheme()

    @JvmStatic
    fun infiniteIkea() = ColorThemeResource.INFINITE_IKEA.getTheme()

    @JvmStatic
    fun fireWeeds() = ColorThemeResource.FIRE_WEEDS.getTheme()

    @JvmStatic
    fun pola() = ColorThemeResource.POLA.getTheme()

    @JvmStatic
    fun spanishSunset() = ColorThemeResource.SPANISH_SUNSET.getTheme()

    @JvmStatic
    fun discord() = ColorThemeResource.DISCORD.getTheme()

    @JvmStatic
    fun afterDark() = ColorThemeResource.AFTER_DARK.getTheme()

}
