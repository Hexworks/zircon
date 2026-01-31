package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.builder.component.colorTheme
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.ANSIColor.*
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.extensions.copy
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE
import org.hexworks.zircon.api.color.Color.Companion.fromString as color

@Suppress("SpellCheckingInspection")
enum class ColorThemeResource(private val colorTheme: ColorTheme) {

    /**
     * This is a null object for color themes indicating that no
     * theme is chosen. It is not all black and/or invisible so that
     * users won't think that the lib is broken. 😅
     */
    DEFAULT(
        colorTheme {
            name = "Default"
            accentColor = DefaultAnsiPalette[BRIGHT_GREEN]
            primaryForegroundColor = DefaultAnsiPalette[BRIGHT_MAGENTA]
            secondaryForegroundColor = DefaultAnsiPalette[BRIGHT_BLUE]
            primaryBackgroundColor = DefaultAnsiPalette[BRIGHT_YELLOW]
            secondaryBackgroundColor = DefaultAnsiPalette[BRIGHT_RED]
        }
    ),

    /**
     * This is the theme that was made by Hexworks.
     */

    /**
     * This is the theme that was made by Hexworks.
     */
    HEXWORKS(
        colorTheme {
            name = "Hexworks"
            accentColor = color("#52b61d")
            primaryForegroundColor = color("#b1d5d7")
            secondaryForegroundColor = color("#91b5b7")
            primaryBackgroundColor = color("#5c335a")
            secondaryBackgroundColor = color("#341139")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stale-sunset
     */

    /**
     * Taken from
     * https://lospec.com/palette-list/stale-sunset
     */
    STALE_SUNSET(
        colorTheme {
            name = "Stale Sunset"
            accentColor = color("#ffe377")
            primaryForegroundColor = color("#cdba76")
            secondaryForegroundColor = color("#bda576")
            primaryBackgroundColor = color("#52484e")
            secondaryBackgroundColor = color("#292442")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/afternoon-haze
     */

    /**
     * Taken from
     * https://lospec.com/palette-list/afternoon-haze
     */
    AFTERNOON_HAZE(
        colorTheme {
            name = "Afternoon Haze"
            accentColor = color("#b0523f")
            primaryForegroundColor = color("#2f6f87")
            secondaryForegroundColor = color("#243a56")
            primaryBackgroundColor = color("#cbbfb2")
            secondaryBackgroundColor = color("#0d1221")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/winter-wonderland
     */

    /**
     * Taken from
     * https://lospec.com/palette-list/winter-wonderland
     */
    WINTER_WONDERLAND(
        colorTheme {
            name = "Winter Wonderland"
            accentColor = color("#8bcadd")
            primaryForegroundColor = color("#d6e1e9")
            secondaryForegroundColor = color("#a7bcc9")
            primaryBackgroundColor = color("#2c4a78")
            secondaryBackgroundColor = color("#20284e")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8
     */

    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8
     */
    PETITE(
        colorTheme {
            name = "Petite"
            accentColor = color("#f8f4e4")
            primaryForegroundColor = color("#efd98d")
            secondaryForegroundColor = color("#c5af63")
            primaryBackgroundColor = color("#272f3b")
            secondaryBackgroundColor = color("#181b22")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/waverator
     */

    /**
     * Taken from
     * https://lospec.com/palette-list/waverator
     */
    WAVERATOR(
        colorTheme {
            name = "Waverator"
            accentColor = color("#cbffd8")
            primaryForegroundColor = color("#70d38b")
            secondaryForegroundColor = color("#53a788")
            primaryBackgroundColor = color("#23313d")
            secondaryBackgroundColor = color("#0c0d14")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/justparchment8
     */
    JUSTPARCHMENT(
        colorTheme {
            name = "Justparchment"
            accentColor = color("#524839")
            primaryForegroundColor = color("#73654a")
            secondaryForegroundColor = color("#8b7d62")
            primaryBackgroundColor = color("#e6ceac")
            secondaryBackgroundColor = color("#292418")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/ammo-8
     */
    AMMO(
        colorTheme {
            name = "Ammo"
            accentColor = color("#eeffcc")
            primaryForegroundColor = color("#bedc7f")
            secondaryForegroundColor = color("#4d8061")
            primaryBackgroundColor = color("#112318")
            secondaryBackgroundColor = color("#040c06")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/nyx8
     */
    NYX(
        colorTheme {
            name = "Nyx"
            accentColor = color("#f6d6bd")
            primaryForegroundColor = color("#c3a38a")
            secondaryForegroundColor = color("#997577")
            primaryBackgroundColor = color("#20394f")
            secondaryBackgroundColor = color("#0f2a3f")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/slso8
     */
    SLSO(
        colorTheme {
            name = "SLSO"
            accentColor = color("#ffaa5e")
            primaryForegroundColor = color("#ffecd6")
            secondaryForegroundColor = color("#ffd4a3")
            primaryBackgroundColor = color("#203c56")
            secondaryBackgroundColor = color("#0d2b45")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/beyond-the-sea-7
     */
    BEYOND_THE_SEA(
        colorTheme {
            name = "Beyond The Sea"
            accentColor = color("#f4fff7")
            primaryForegroundColor = color("#aceed1")
            secondaryForegroundColor = color("#6bd0b5")
            primaryBackgroundColor = color("#313b55")
            secondaryBackgroundColor = color("#1e1a2a")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/forest-glow
     */
    FOREST_GLOW(
        colorTheme {
            name = "Forest Glow"
            accentColor = color("#deca54")
            primaryForegroundColor = color("#97933a")
            secondaryForegroundColor = color("#5f6d43")
            primaryBackgroundColor = color("#1f2c3d")
            secondaryBackgroundColor = color("#00070d")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/cherry-bear
     */
    CHERRY_BEAR(
        colorTheme {
            name = "Cherry Bear"
            accentColor = color("#fbb396")
            primaryForegroundColor = color("#bc6a6a")
            secondaryForegroundColor = color("#a35454")
            primaryBackgroundColor = color("#472a36")
            secondaryBackgroundColor = color("#0d0c11")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/supernova-7
     */
    SUPERNOVA(
        colorTheme {
            name = "Supernova"
            accentColor = color("#ffce9c")
            primaryForegroundColor = color("#cf7862")
            secondaryForegroundColor = color("#a75252")
            primaryBackgroundColor = color("#3d203b")
            secondaryBackgroundColor = color("#1a080e")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/cp_rust-5
     */
    RUST(
        colorTheme {
            name = "Rust"
            accentColor = color("#ffe2c6")
            primaryForegroundColor = color("#f0bb9c")
            secondaryForegroundColor = color("#e18866")
            primaryBackgroundColor = color("#712f30")
            secondaryBackgroundColor = color("#230000")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/cursed-turkey
     */
    CURSED_TURKEY(
        colorTheme {
            name = "Cursed Turkey"
            accentColor = color("#d7ac64")
            primaryForegroundColor = color("#df8c00")
            secondaryForegroundColor = color("#db7209")
            primaryBackgroundColor = color("#b32a12")
            secondaryBackgroundColor = color("#1e110c")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/molten
     */
    MOLTEN(
        colorTheme {
            name = "Molten"
            accentColor = color("#fd724e")
            primaryForegroundColor = color("#a02f40")
            secondaryForegroundColor = color("#5f2f45")
            primaryBackgroundColor = color("#261b2e")
            secondaryBackgroundColor = color("#201727")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_RED(
        colorTheme {
            name = "Stormy Red"
            accentColor = color("#a95a3f")
            primaryForegroundColor = color("#f8eebf")
            secondaryForegroundColor = color("#edbb70")
            primaryBackgroundColor = color("#3a5043")
            secondaryBackgroundColor = color("#242828")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_GREEN(
        colorTheme {
            name = "Stormy Green"
            accentColor = color("#7f9860")
            primaryForegroundColor = color("#f8eebf")
            secondaryForegroundColor = color("#edbb70")
            primaryBackgroundColor = color("#3a5043")
            secondaryBackgroundColor = color("#242828")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    OIL(
        colorTheme {
            name = "Oil"
            accentColor = color("#c69fa5")
            primaryForegroundColor = color("#fbf5ef")
            secondaryForegroundColor = color("#f2d3ab")
            primaryBackgroundColor = color("#494d7e")
            secondaryBackgroundColor = color("#272744")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    NATURES_ATMOSPHERE(
        colorTheme {
            name = "Nature's Atmosphere"
            accentColor = color("#a6a220")
            primaryForegroundColor = color("#efbe8e")
            secondaryForegroundColor = color("#a6d3ff")
            primaryBackgroundColor = color("#45619e")
            secondaryBackgroundColor = color("#040404")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/infinite-ikea
     */
    INFINITE_IKEA(
        colorTheme {
            name = "Infinite IKEA"
            accentColor = color("#f6d76b")
            primaryForegroundColor = color("#f6f0f7")
            secondaryForegroundColor = color("#45a9ff")
            primaryBackgroundColor = color("#104a7d")
            secondaryBackgroundColor = color("#12223c")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/fire-weeds
     */
    FIRE_WEEDS(
        colorTheme {
            name = "Fire Weeds"
            accentColor = color("#e2d6fe")
            primaryForegroundColor = color("#fea631")
            secondaryForegroundColor = color("#e5371b")
            primaryBackgroundColor = color("#630f19")
            secondaryBackgroundColor = color("#060013")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/pola5
     */
    POLA(
        colorTheme {
            name = "Pola"
            accentColor = color("#ebf9ff")
            primaryForegroundColor = color("#acd6f6")
            secondaryForegroundColor = color("#52a5de")
            primaryBackgroundColor = color("#18284a")
            secondaryBackgroundColor = color("#070810")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/spanish-sunset
     */
    SPANISH_SUNSET(
        colorTheme {
            name = "Spanish Sunset"
            accentColor = color("#fd724e")
            primaryForegroundColor = color("#f5ddbc")
            secondaryForegroundColor = color("#fabb64")
            primaryBackgroundColor = color("#a02f40")
            secondaryBackgroundColor = color("#5f2f45")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/discord
     */
    DISCORD(
        colorTheme {
            name = "Discord"
            accentColor = color("#7289da")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#99aab5")
            primaryBackgroundColor = color("#2c2f33")
            secondaryBackgroundColor = color("#23272a")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8-afterdark
     */
    AFTER_DARK(
        colorTheme {
            name = "After Dark"
            accentColor = color("#bb9a67")
            primaryForegroundColor = color("#a07c43")
            secondaryForegroundColor = color("#776131")
            primaryBackgroundColor = color("#272b29")
            secondaryBackgroundColor = color("#161c20")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(
        colorTheme {
            name = "Adrift In Dreams"
            accentColor = color("#CFF09E")
            primaryForegroundColor = color("#A8DBA8")
            secondaryForegroundColor = color("#79BD9A")
            primaryBackgroundColor = color("#3B8686")
            secondaryBackgroundColor = color("#0B486B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(
        colorTheme {
            name = "Let Them Eat Cake"
            accentColor = color("#C5E0DC")
            primaryForegroundColor = color("#ECE5CE")
            secondaryForegroundColor = color("#F1D4AF")
            primaryBackgroundColor = color("#E08E79")
            secondaryBackgroundColor = color("#774F38")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(
        colorTheme {
            name = "Tech Light"
            accentColor = color("#D1E751")
            primaryForegroundColor = color("#FFFFFF")
            secondaryForegroundColor = color("#FFFFFF")
            primaryBackgroundColor = color("#26ADE4")
            secondaryBackgroundColor = color("#000000")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(
        colorTheme {
            name = "Headache"
            accentColor = color("#BF4D28")
            primaryForegroundColor = color("#F6F7BD")
            secondaryForegroundColor = color("#E6AC27")
            primaryBackgroundColor = color("#80BCA3")
            secondaryBackgroundColor = color("#655643")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(
        colorTheme {
            name = "Gamebookers"
            accentColor = color("#FF9900")
            primaryForegroundColor = color("#E9E9E9")
            secondaryForegroundColor = color("#BCBCBC")
            primaryBackgroundColor = color("#3299BB")
            secondaryBackgroundColor = color("#424242")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(
        colorTheme {
            name = "Entrapped In A Palette"
            accentColor = color("#7B3B3B")
            primaryForegroundColor = color("#B9D7D9")
            secondaryForegroundColor = color("#668284")
            primaryBackgroundColor = color("#493736")
            secondaryBackgroundColor = color("#2A2829")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(
        colorTheme {
            name = "War"
            accentColor = color("#F21D41")
            primaryForegroundColor = color("#EBEBBC")
            secondaryForegroundColor = color("#BCE3C5")
            primaryBackgroundColor = color("#82B3AE")
            secondaryBackgroundColor = color("#230F2B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(
        colorTheme {
            name = "Captured By Pirates"
            accentColor = color("#AB9597")
            primaryForegroundColor = color("#FFFBF0")
            secondaryForegroundColor = color("#968F4B")
            primaryBackgroundColor = color("#7A6248")
            secondaryBackgroundColor = color("#030506")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(
        colorTheme {
            name = "Ghost Of A Chance"
            accentColor = color("#95AC54")
            primaryForegroundColor = color("#F6F1CB")
            secondaryForegroundColor = color("#DFD4A7")
            primaryBackgroundColor = color("#7F7C69")
            secondaryBackgroundColor = color("#29210A")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(
        colorTheme {
            name = "After The Heist"
            accentColor = color("#b86A6A")
            primaryForegroundColor = color("#F8EDD1")
            secondaryForegroundColor = color("#C5CFC6")
            primaryBackgroundColor = color("#9D9D93")
            secondaryBackgroundColor = color("#474843")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(
        colorTheme {
            name = "Pablo Neruda"
            accentColor = color("#E6324B")
            primaryForegroundColor = color("#F2E3C6")
            secondaryForegroundColor = color("#FFC6A5")
            primaryBackgroundColor = color("#353634")
            secondaryBackgroundColor = color("#2B2B2B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(
        colorTheme {
            name = "Olive Leaf Tea"
            accentColor = color("#e99A44")
            primaryForegroundColor = color("#EAE7D1")
            secondaryForegroundColor = color("#CCC58E")
            primaryBackgroundColor = color("#7B8455")
            secondaryBackgroundColor = color("#485C2B")
        }
    ),

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    CYBERPUNK(
        colorTheme {
            name = "Cyberpunk"
            accentColor = color("#61D6C4")
            primaryForegroundColor = color("#71918C")
            secondaryForegroundColor = color("#3D615F")
            primaryBackgroundColor = color("#25343B")
            secondaryBackgroundColor = color("#212429")
        }
    ),

    // these are slack themes

    AFTERGLOW(
        colorTheme {
            name = "Afterglow"
            accentColor = color("#ADBA4E")
            primaryForegroundColor = color("#DEDEDE")
            secondaryForegroundColor = color("#D2D6D6")
            primaryBackgroundColor = color("#2F2C2F")
            secondaryBackgroundColor = color("#252525")
        }
    ),

    AMIGA_OS(
        colorTheme {
            name = "Amiga OS"
            accentColor = color("#F08000")
            primaryForegroundColor = color("#FFFFFF")
            secondaryForegroundColor = color("#dddddd")
            primaryBackgroundColor = color("#0050A0")
            secondaryBackgroundColor = color("#000020")
        }
    ),

    ANCESTRY(
        colorTheme {
            name = "Ancestry"
            accentColor = color("#F3B670")
            primaryForegroundColor = color("#9CBE30")
            secondaryForegroundColor = color("#7A9C0F")
            primaryBackgroundColor = color("#706B63")
            secondaryBackgroundColor = color("#534D46")
        }
    ),

    ARC(
        colorTheme {
            name = "Arc"
            accentColor = color("#5294E2")
            primaryForegroundColor = color("#FFFFFF")
            secondaryForegroundColor = color("#dddddd")
            primaryBackgroundColor = color("#4A5664")
            secondaryBackgroundColor = color("#303641")
        }
    ),

    FOREST(
        colorTheme {
            name = "Forest"
            accentColor = color("#94E864")
            primaryForegroundColor = color("#FFFFFF")
            secondaryForegroundColor = color("#dddddd")
            primaryBackgroundColor = color("#077A07")
            secondaryBackgroundColor = color("#033313")
        }
    ),

    LINUX_MINT_DARK(
        colorTheme {
            name = "Linux Mint Dark"
            accentColor = color("#8FA876")
            primaryForegroundColor = color("#FFFFFF")
            secondaryForegroundColor = color("#818181")
            primaryBackgroundColor = color("#353535")
            secondaryBackgroundColor = color("#2F2F2F")
        }
    ),

    NORD(
        colorTheme {
            name = "Nord"
            accentColor = color("#A3BE8C")
            primaryForegroundColor = color("#D8DEE9")
            secondaryForegroundColor = color("#81A1C1")
            primaryBackgroundColor = color("#3B4252")
            secondaryBackgroundColor = color("#2E3440")
        }
    ),

    TRON(
        colorTheme {
            name = "Tron"
            accentColor = color("#1EB8EB")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#dddddd")
            primaryBackgroundColor = color("#424242")
            secondaryBackgroundColor = color("#000000")
        }
    ),

    SAIKU(
        colorTheme {
            name = "Saiku"
            accentColor = color("#ffffff")
            primaryForegroundColor = color("#cccccc")
            secondaryForegroundColor = color("#999999")
            primaryBackgroundColor = color("#AE1817")
            secondaryBackgroundColor = color("#232323")
        }
    ),

    INGRESS_RESISTANCE(
        colorTheme {
            name = "Ingress Resistance"
            accentColor = color("#F1C248")
            primaryForegroundColor = color("#34EAF5")
            secondaryForegroundColor = color("#0492D0")
            primaryBackgroundColor = color("#393218")
            secondaryBackgroundColor = color("#000E0F")
        }
    ),

    INGRESS_ENLIGHTENED(
        colorTheme {
            name = "Ingress Enlightened"
            accentColor = color("#02BF02")
            primaryForegroundColor = color("#34EAF5")
            secondaryForegroundColor = color("#0492D0")
            primaryBackgroundColor = color("#393218")
            secondaryBackgroundColor = color("#000E0F")
        }
    ),

    ZENBURN_VANILLA(
        colorTheme {
            name = "Zenburn Vanilla"
            accentColor = color("#f0dfaf")
            primaryForegroundColor = color("#dcdccc")
            secondaryForegroundColor = color("#9fafaf")
            primaryBackgroundColor = color("#333333")
            secondaryBackgroundColor = color("#1e2320")
        }
    ),

    ZENBURN_PINK(
        colorTheme {
            name = "Zenburn Pink"
            accentColor = color("#ecbcbc")
            primaryForegroundColor = color("#dcdccc")
            secondaryForegroundColor = color("#9fafaf")
            primaryBackgroundColor = color("#333333")
            secondaryBackgroundColor = color("#1e2320")
        }
    ),

    ZENBURN_GREEN(
        colorTheme {
            name = "Zenburn Green"
            accentColor = color("#709080")
            primaryForegroundColor = color("#dcdccc")
            secondaryForegroundColor = color("#9fafaf")
            primaryBackgroundColor = color("#333333")
            secondaryBackgroundColor = color("#1e2320")
        }
    ),

    MONOKAI_YELLOW(
        colorTheme {
            name = "Monokai Yellow"
            accentColor = color("#ffd866")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    MONOKAI_PINK(
        colorTheme {
            name = "Monokai Pink"
            accentColor = color("#ff6188")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    MONOKAI_GREEN(
        colorTheme {
            name = "Monokai Green"
            accentColor = color("#a9dc76")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    MONOKAI_ORANGE(
        colorTheme {
            name = "Monokai Orange"
            accentColor = color("#fc9867")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    MONOKAI_VIOLET(
        colorTheme {
            name = "Monokai Violet"
            accentColor = color("#ab9df2")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    MONOKAI_BLUE(
        colorTheme {
            name = "Monokai Blue"
            accentColor = color("#78dce8")
            primaryForegroundColor = color("#ffffff")
            secondaryForegroundColor = color("#fdf9f3")
            primaryBackgroundColor = color("#3e3b3f")
            secondaryBackgroundColor = color("#2c292d")
        }
    ),

    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Yellow"
            accentColor = color("#b58900")
        }
    ),

    SOLARIZED_DARK_ORANGE(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Orange"
            accentColor = color("#cb4b16")
        }
    ),

    SOLARIZED_DARK_RED(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Red"
            accentColor = color("#dc322f")
        }
    ),

    SOLARIZED_DARK_MAGENTA(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Magenta"
            accentColor = color("#d33682")
        }
    ),

    SOLARIZED_DARK_VIOLET(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Violet"
            accentColor = color("#6c71c4")
        }
    ),

    SOLARIZED_DARK_BLUE(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Blue"
            accentColor = color("#268bd2")
        }
    ),

    SOLARIZED_DARK_CYAN(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Cyan"
            accentColor = color("#2aa198")
        }
    ),

    SOLARIZED_DARK_GREEN(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Green"
            accentColor = color("#859900")
        }
    ),

    SOLARIZED_LIGHT_YELLOW(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Yellow"
            accentColor = color("#b58900")
        }
    ),

    SOLARIZED_LIGHT_ORANGE(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Orange"
            accentColor = color("#cb4b16")
        }
    ),

    SOLARIZED_LIGHT_RED(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Red"
            accentColor = color("#dc322f")
        }
    ),

    SOLARIZED_LIGHT_MAGENTA(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Magenta"
            accentColor = color("#d33682")
        }
    ),

    SOLARIZED_LIGHT_VIOLET(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Violet"
            accentColor = color("#6c71c4")
        }
    ),

    SOLARIZED_LIGHT_BLUE(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Blue"
            accentColor = color("#268bd2")
        }
    ),

    SOLARIZED_LIGHT_CYAN(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Cyan"
            accentColor = color("#2aa198")
        }
    ),

    SOLARIZED_LIGHT_GREEN(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Green"
            accentColor = color("#859900")
        }
    );

    fun getTheme() = colorTheme

    enum class SolarizedBase(val theme: ColorTheme) {
        SOLARIZED_DARK_BASE(
            colorTheme {
                primaryForegroundColor = color("#fdf6e3")
                // note that this was made darker because there was not enough contrast
                secondaryForegroundColor = color("#cec8b5")
                primaryBackgroundColor = color("#073642")
                secondaryBackgroundColor = color("#002b36")
            }
        ),

        SOLARIZED_LIGHT_BASE(
            colorTheme {
                primaryForegroundColor = color("#002b36")
                // note that this was made lighter because there was not enough contrast
                secondaryForegroundColor = color("#275662")
                primaryBackgroundColor = color("#fdf6e3")
                secondaryBackgroundColor = color("#eee8d5")
            }
        )
    }
}
