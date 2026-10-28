package org.hexworks.zircon.api

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.resource.ColorThemeResource

public object ColorThemes {

    /**
     * This is a null object for color themes indicating that no
     * theme is chosen.
     */
    public fun defaultTheme(): ColorTheme = ColorThemeResource.DEFAULT.getTheme()

    /**
     * This is the theme that was made by Hexworks.
     */
    public fun hexworks(): ColorTheme = ColorThemeResource.HEXWORKS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    public fun adriftInDreams(): ColorTheme = ColorThemeResource.ADRIFT_IN_DREAMS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    public fun letThemEatCake(): ColorTheme = ColorThemeResource.LET_THEM_EAT_CAKE.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/15/tech_light
     */
    public fun techLight(): ColorTheme = ColorThemeResource.TECH_LIGHT.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    public fun headache(): ColorTheme = ColorThemeResource.HEADACHE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    public fun gamebookers(): ColorTheme = ColorThemeResource.GAMEBOOKERS.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    public fun entrappedInAPalette(): ColorTheme = ColorThemeResource.ENTRAPPED_IN_A_PALETTE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    public fun war(): ColorTheme = ColorThemeResource.WAR.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    public fun capturedByPirates(): ColorTheme = ColorThemeResource.CAPTURED_BY_PIRATES.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    public fun ghostOfAChance(): ColorTheme = ColorThemeResource.GHOST_OF_A_CHANCE.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    public fun afterTheHeist(): ColorTheme = ColorThemeResource.AFTER_THE_HEIST.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    public fun pabloNeruda(): ColorTheme = ColorThemeResource.PABLO_NERUDA.getTheme()

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    public fun oliveLeafTea(): ColorTheme = ColorThemeResource.OLIVE_LEAF_TEA.getTheme()

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    public fun cyberpunk(): ColorTheme = ColorThemeResource.CYBERPUNK.getTheme()

    // These come from the Slack themes: https://slackthemes.net
    public fun afterglow(): ColorTheme = ColorThemeResource.AFTERGLOW.getTheme()

    public fun amigaOs(): ColorTheme = ColorThemeResource.AMIGA_OS.getTheme()

    public fun ancestry(): ColorTheme = ColorThemeResource.ANCESTRY.getTheme()

    public fun arc(): ColorTheme = ColorThemeResource.ARC.getTheme()

    public fun forest(): ColorTheme = ColorThemeResource.FOREST.getTheme()

    public fun linuxMintDark(): ColorTheme = ColorThemeResource.LINUX_MINT_DARK.getTheme()

    public fun nord(): ColorTheme = ColorThemeResource.NORD.getTheme()

    public fun tron(): ColorTheme = ColorThemeResource.TRON.getTheme()

    public fun saiku(): ColorTheme = ColorThemeResource.SAIKU.getTheme()

    public fun ingressResistance(): ColorTheme = ColorThemeResource.INGRESS_RESISTANCE.getTheme()

    public fun ingressEnlightened(): ColorTheme = ColorThemeResource.INGRESS_ENLIGHTENED.getTheme()

    /**
     * Taken from the official Zenburn page.
     */
    public fun zenburnVanilla(): ColorTheme = ColorThemeResource.ZENBURN_VANILLA.getTheme()

    public fun zenburnPink(): ColorTheme = ColorThemeResource.ZENBURN_PINK.getTheme()

    public fun zenburnGreen(): ColorTheme = ColorThemeResource.ZENBURN_GREEN.getTheme()

    //Taken form the official Monokai page.

    public fun monokaiYellow(): ColorTheme = ColorThemeResource.MONOKAI_YELLOW.getTheme()

    public fun monokaiPink(): ColorTheme = ColorThemeResource.MONOKAI_PINK.getTheme()

    public fun monokaiGreen(): ColorTheme = ColorThemeResource.MONOKAI_GREEN.getTheme()

    public fun monokaiOrange(): ColorTheme = ColorThemeResource.MONOKAI_ORANGE.getTheme()

    public fun monokaiViolet(): ColorTheme = ColorThemeResource.MONOKAI_VIOLET.getTheme()

    public fun monokaiBlue(): ColorTheme = ColorThemeResource.MONOKAI_BLUE.getTheme()

    // All Solarized themes are taken from:
    // http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/

    public fun solarizedDarkYellow(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_YELLOW.getTheme()

    public fun solarizedDarkOrange(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_ORANGE.getTheme()

    public fun solarizedDarkRed(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_RED.getTheme()

    public fun solarizedDarkMagenta(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_MAGENTA.getTheme()

    public fun solarizedDarkViolet(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_VIOLET.getTheme()

    public fun solarizedDarkBlue(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_BLUE.getTheme()

    public fun solarizedDarkCyan(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_CYAN.getTheme()

    public fun solarizedDarkGreen(): ColorTheme = ColorThemeResource.SOLARIZED_DARK_GREEN.getTheme()

    public fun solarizedLightYellow(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_YELLOW.getTheme()

    public fun solarizedLightOrange(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_ORANGE.getTheme()

    public fun solarizedLightRed(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_RED.getTheme()

    public fun solarizedLightMagenta(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_MAGENTA.getTheme()

    public fun solarizedLightViolet(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_VIOLET.getTheme()

    public fun solarizedLightBlue(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_BLUE.getTheme()

    public fun solarizedLightCyan(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_CYAN.getTheme()

    public fun solarizedLightGreen(): ColorTheme = ColorThemeResource.SOLARIZED_LIGHT_GREEN.getTheme()

    public fun staleSunset(): ColorTheme = ColorThemeResource.STALE_SUNSET.getTheme()

    public fun afternoonHaze(): ColorTheme = ColorThemeResource.AFTERNOON_HAZE.getTheme()

    public fun winterWonderland(): ColorTheme = ColorThemeResource.WINTER_WONDERLAND.getTheme()

    public fun petite(): ColorTheme = ColorThemeResource.PETITE.getTheme()

    public fun waverator(): ColorTheme = ColorThemeResource.WAVERATOR.getTheme()

    public fun justparchment(): ColorTheme = ColorThemeResource.JUSTPARCHMENT.getTheme()

    public fun ammo(): ColorTheme = ColorThemeResource.AMMO.getTheme()

    public fun nyx(): ColorTheme = ColorThemeResource.NYX.getTheme()

    public fun slso(): ColorTheme = ColorThemeResource.SLSO.getTheme()

    public fun beyondTheSea(): ColorTheme = ColorThemeResource.BEYOND_THE_SEA.getTheme()

    public fun forestGlow(): ColorTheme = ColorThemeResource.FOREST_GLOW.getTheme()

    public fun cherryBear(): ColorTheme = ColorThemeResource.CHERRY_BEAR.getTheme()

    public fun supernova(): ColorTheme = ColorThemeResource.SUPERNOVA.getTheme()

    public fun rust(): ColorTheme = ColorThemeResource.RUST.getTheme()

    public fun cursedTurkey(): ColorTheme = ColorThemeResource.CURSED_TURKEY.getTheme()

    public fun molten(): ColorTheme = ColorThemeResource.MOLTEN.getTheme()

    public fun stormyRed(): ColorTheme = ColorThemeResource.STORMY_RED.getTheme()

    public fun stormyGreen(): ColorTheme = ColorThemeResource.STORMY_GREEN.getTheme()

    public fun oil(): ColorTheme = ColorThemeResource.OIL.getTheme()

    public fun naturesAtmosphere(): ColorTheme = ColorThemeResource.NATURES_ATMOSPHERE.getTheme()

    public fun infiniteIkea(): ColorTheme = ColorThemeResource.INFINITE_IKEA.getTheme()

    public fun fireWeeds(): ColorTheme = ColorThemeResource.FIRE_WEEDS.getTheme()

    public fun pola(): ColorTheme = ColorThemeResource.POLA.getTheme()

    public fun spanishSunset(): ColorTheme = ColorThemeResource.SPANISH_SUNSET.getTheme()

    public fun discord(): ColorTheme = ColorThemeResource.DISCORD.getTheme()

    public fun afterDark(): ColorTheme = ColorThemeResource.AFTER_DARK.getTheme()

}
