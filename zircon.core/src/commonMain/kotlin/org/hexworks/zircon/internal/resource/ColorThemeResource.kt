package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.builder.component.colorTheme
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.extensions.copy
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

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
            accentColor = ANSITileColor.BRIGHT_GREEN
            primaryForegroundColor = ANSITileColor.BRIGHT_MAGENTA
            secondaryForegroundColor = ANSITileColor.BRIGHT_BLUE
            primaryBackgroundColor = ANSITileColor.BRIGHT_YELLOW
            secondaryBackgroundColor = ANSITileColor.BRIGHT_RED
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
            accentColor = TileColor.fromString("#52b61d")
            primaryForegroundColor = TileColor.fromString("#b1d5d7")
            secondaryForegroundColor = TileColor.fromString("#91b5b7")
            primaryBackgroundColor = TileColor.fromString("#5c335a")
            secondaryBackgroundColor = TileColor.fromString("#341139")
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
            accentColor = TileColor.fromString("#ffe377")
            primaryForegroundColor = TileColor.fromString("#cdba76")
            secondaryForegroundColor = TileColor.fromString("#bda576")
            primaryBackgroundColor = TileColor.fromString("#52484e")
            secondaryBackgroundColor = TileColor.fromString("#292442")
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
            accentColor = TileColor.fromString("#b0523f")
            primaryForegroundColor = TileColor.fromString("#2f6f87")
            secondaryForegroundColor = TileColor.fromString("#243a56")
            primaryBackgroundColor = TileColor.fromString("#cbbfb2")
            secondaryBackgroundColor = TileColor.fromString("#0d1221")
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
            accentColor = TileColor.fromString("#8bcadd")
            primaryForegroundColor = TileColor.fromString("#d6e1e9")
            secondaryForegroundColor = TileColor.fromString("#a7bcc9")
            primaryBackgroundColor = TileColor.fromString("#2c4a78")
            secondaryBackgroundColor = TileColor.fromString("#20284e")
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
            accentColor = TileColor.fromString("#f8f4e4")
            primaryForegroundColor = TileColor.fromString("#efd98d")
            secondaryForegroundColor = TileColor.fromString("#c5af63")
            primaryBackgroundColor = TileColor.fromString("#272f3b")
            secondaryBackgroundColor = TileColor.fromString("#181b22")
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
            accentColor = TileColor.fromString("#cbffd8")
            primaryForegroundColor = TileColor.fromString("#70d38b")
            secondaryForegroundColor = TileColor.fromString("#53a788")
            primaryBackgroundColor = TileColor.fromString("#23313d")
            secondaryBackgroundColor = TileColor.fromString("#0c0d14")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/justparchment8
     */
    JUSTPARCHMENT(
        colorTheme {
            name = "Justparchment"
            accentColor = TileColor.fromString("#524839")
            primaryForegroundColor = TileColor.fromString("#73654a")
            secondaryForegroundColor = TileColor.fromString("#8b7d62")
            primaryBackgroundColor = TileColor.fromString("#e6ceac")
            secondaryBackgroundColor = TileColor.fromString("#292418")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/ammo-8
     */
    AMMO(
        colorTheme {
            name = "Ammo"
            accentColor = TileColor.fromString("#eeffcc")
            primaryForegroundColor = TileColor.fromString("#bedc7f")
            secondaryForegroundColor = TileColor.fromString("#4d8061")
            primaryBackgroundColor = TileColor.fromString("#112318")
            secondaryBackgroundColor = TileColor.fromString("#040c06")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/nyx8
     */
    NYX(
        colorTheme {
            name = "Nyx"
            accentColor = TileColor.fromString("#f6d6bd")
            primaryForegroundColor = TileColor.fromString("#c3a38a")
            secondaryForegroundColor = TileColor.fromString("#997577")
            primaryBackgroundColor = TileColor.fromString("#20394f")
            secondaryBackgroundColor = TileColor.fromString("#0f2a3f")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/slso8
     */
    SLSO(
        colorTheme {
            name = "SLSO"
            accentColor = TileColor.fromString("#ffaa5e")
            primaryForegroundColor = TileColor.fromString("#ffecd6")
            secondaryForegroundColor = TileColor.fromString("#ffd4a3")
            primaryBackgroundColor = TileColor.fromString("#203c56")
            secondaryBackgroundColor = TileColor.fromString("#0d2b45")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/beyond-the-sea-7
     */
    BEYOND_THE_SEA(
        colorTheme {
            name = "Beyond The Sea"
            accentColor = TileColor.fromString("#f4fff7")
            primaryForegroundColor = TileColor.fromString("#aceed1")
            secondaryForegroundColor = TileColor.fromString("#6bd0b5")
            primaryBackgroundColor = TileColor.fromString("#313b55")
            secondaryBackgroundColor = TileColor.fromString("#1e1a2a")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/forest-glow
     */
    FOREST_GLOW(
        colorTheme {
            name = "Forest Glow"
            accentColor = TileColor.fromString("#deca54")
            primaryForegroundColor = TileColor.fromString("#97933a")
            secondaryForegroundColor = TileColor.fromString("#5f6d43")
            primaryBackgroundColor = TileColor.fromString("#1f2c3d")
            secondaryBackgroundColor = TileColor.fromString("#00070d")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/cherry-bear
     */
    CHERRY_BEAR(
        colorTheme {
            name = "Cherry Bear"
            accentColor = TileColor.fromString("#fbb396")
            primaryForegroundColor = TileColor.fromString("#bc6a6a")
            secondaryForegroundColor = TileColor.fromString("#a35454")
            primaryBackgroundColor = TileColor.fromString("#472a36")
            secondaryBackgroundColor = TileColor.fromString("#0d0c11")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/supernova-7
     */
    SUPERNOVA(
        colorTheme {
            name = "Supernova"
            accentColor = TileColor.fromString("#ffce9c")
            primaryForegroundColor = TileColor.fromString("#cf7862")
            secondaryForegroundColor = TileColor.fromString("#a75252")
            primaryBackgroundColor = TileColor.fromString("#3d203b")
            secondaryBackgroundColor = TileColor.fromString("#1a080e")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/cp_rust-5
     */
    RUST(
        colorTheme {
            name = "Rust"
            accentColor = TileColor.fromString("#ffe2c6")
            primaryForegroundColor = TileColor.fromString("#f0bb9c")
            secondaryForegroundColor = TileColor.fromString("#e18866")
            primaryBackgroundColor = TileColor.fromString("#712f30")
            secondaryBackgroundColor = TileColor.fromString("#230000")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/cursed-turkey
     */
    CURSED_TURKEY(
        colorTheme {
            name = "Cursed Turkey"
            accentColor = TileColor.fromString("#d7ac64")
            primaryForegroundColor = TileColor.fromString("#df8c00")
            secondaryForegroundColor = TileColor.fromString("#db7209")
            primaryBackgroundColor = TileColor.fromString("#b32a12")
            secondaryBackgroundColor = TileColor.fromString("#1e110c")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/molten
     */
    MOLTEN(
        colorTheme {
            name = "Molten"
            accentColor = TileColor.fromString("#fd724e")
            primaryForegroundColor = TileColor.fromString("#a02f40")
            secondaryForegroundColor = TileColor.fromString("#5f2f45")
            primaryBackgroundColor = TileColor.fromString("#261b2e")
            secondaryBackgroundColor = TileColor.fromString("#201727")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_RED(
        colorTheme {
            name = "Stormy Red"
            accentColor = TileColor.fromString("#a95a3f")
            primaryForegroundColor = TileColor.fromString("#f8eebf")
            secondaryForegroundColor = TileColor.fromString("#edbb70")
            primaryBackgroundColor = TileColor.fromString("#3a5043")
            secondaryBackgroundColor = TileColor.fromString("#242828")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_GREEN(
        colorTheme {
            name = "Stormy Green"
            accentColor = TileColor.fromString("#7f9860")
            primaryForegroundColor = TileColor.fromString("#f8eebf")
            secondaryForegroundColor = TileColor.fromString("#edbb70")
            primaryBackgroundColor = TileColor.fromString("#3a5043")
            secondaryBackgroundColor = TileColor.fromString("#242828")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    OIL(
        colorTheme {
            name = "Oil"
            accentColor = TileColor.fromString("#c69fa5")
            primaryForegroundColor = TileColor.fromString("#fbf5ef")
            secondaryForegroundColor = TileColor.fromString("#f2d3ab")
            primaryBackgroundColor = TileColor.fromString("#494d7e")
            secondaryBackgroundColor = TileColor.fromString("#272744")
        }
    ),


    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    NATURES_ATMOSPHERE(
        colorTheme {
            name = "Nature's Atmosphere"
            accentColor = TileColor.fromString("#a6a220")
            primaryForegroundColor = TileColor.fromString("#efbe8e")
            secondaryForegroundColor = TileColor.fromString("#a6d3ff")
            primaryBackgroundColor = TileColor.fromString("#45619e")
            secondaryBackgroundColor = TileColor.fromString("#040404")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/infinite-ikea
     */
    INFINITE_IKEA(
        colorTheme {
            name = "Infinite IKEA"
            accentColor = TileColor.fromString("#f6d76b")
            primaryForegroundColor = TileColor.fromString("#f6f0f7")
            secondaryForegroundColor = TileColor.fromString("#45a9ff")
            primaryBackgroundColor = TileColor.fromString("#104a7d")
            secondaryBackgroundColor = TileColor.fromString("#12223c")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/fire-weeds
     */
    FIRE_WEEDS(
        colorTheme {
            name = "Fire Weeds"
            accentColor = TileColor.fromString("#e2d6fe")
            primaryForegroundColor = TileColor.fromString("#fea631")
            secondaryForegroundColor = TileColor.fromString("#e5371b")
            primaryBackgroundColor = TileColor.fromString("#630f19")
            secondaryBackgroundColor = TileColor.fromString("#060013")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/pola5
     */
    POLA(
        colorTheme {
            name = "Pola"
            accentColor = TileColor.fromString("#ebf9ff")
            primaryForegroundColor = TileColor.fromString("#acd6f6")
            secondaryForegroundColor = TileColor.fromString("#52a5de")
            primaryBackgroundColor = TileColor.fromString("#18284a")
            secondaryBackgroundColor = TileColor.fromString("#070810")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/spanish-sunset
     */
    SPANISH_SUNSET(
        colorTheme {
            name = "Spanish Sunset"
            accentColor = TileColor.fromString("#fd724e")
            primaryForegroundColor = TileColor.fromString("#f5ddbc")
            secondaryForegroundColor = TileColor.fromString("#fabb64")
            primaryBackgroundColor = TileColor.fromString("#a02f40")
            secondaryBackgroundColor = TileColor.fromString("#5f2f45")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/discord
     */
    DISCORD(
        colorTheme {
            name = "Discord"
            accentColor = TileColor.fromString("#7289da")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#99aab5")
            primaryBackgroundColor = TileColor.fromString("#2c2f33")
            secondaryBackgroundColor = TileColor.fromString("#23272a")
        }
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8-afterdark
     */
    AFTER_DARK(
        colorTheme {
            name = "After Dark"
            accentColor = TileColor.fromString("#bb9a67")
            primaryForegroundColor = TileColor.fromString("#a07c43")
            secondaryForegroundColor = TileColor.fromString("#776131")
            primaryBackgroundColor = TileColor.fromString("#272b29")
            secondaryBackgroundColor = TileColor.fromString("#161c20")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(
        colorTheme {
            name = "Adrift In Dreams"
            accentColor = TileColor.fromString("#CFF09E")
            primaryForegroundColor = TileColor.fromString("#A8DBA8")
            secondaryForegroundColor = TileColor.fromString("#79BD9A")
            primaryBackgroundColor = TileColor.fromString("#3B8686")
            secondaryBackgroundColor = TileColor.fromString("#0B486B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/49963/let_them_eat_cake
     */
    LET_THEM_EAT_CAKE(
        colorTheme {
            name = "Let Them Eat Cake"
            accentColor = TileColor.fromString("#C5E0DC")
            primaryForegroundColor = TileColor.fromString("#ECE5CE")
            secondaryForegroundColor = TileColor.fromString("#F1D4AF")
            primaryBackgroundColor = TileColor.fromString("#E08E79")
            secondaryBackgroundColor = TileColor.fromString("#774F38")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/15/tech_light
     */
    TECH_LIGHT(
        colorTheme {
            name = "Tech Light"
            accentColor = TileColor.fromString("#D1E751")
            primaryForegroundColor = TileColor.fromString("#FFFFFF")
            secondaryForegroundColor = TileColor.fromString("#FFFFFF")
            primaryBackgroundColor = TileColor.fromString("#26ADE4")
            secondaryBackgroundColor = TileColor.fromString("#000000")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/953498/Headache
     */
    HEADACHE(
        colorTheme {
            name = "Headache"
            accentColor = TileColor.fromString("#BF4D28")
            primaryForegroundColor = TileColor.fromString("#F6F7BD")
            secondaryForegroundColor = TileColor.fromString("#E6AC27")
            primaryBackgroundColor = TileColor.fromString("#80BCA3")
            secondaryBackgroundColor = TileColor.fromString("#655643")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/148712/Gamebookers
     */
    GAMEBOOKERS(
        colorTheme {
            name = "Gamebookers"
            accentColor = TileColor.fromString("#FF9900")
            primaryForegroundColor = TileColor.fromString("#E9E9E9")
            secondaryForegroundColor = TileColor.fromString("#BCBCBC")
            primaryBackgroundColor = TileColor.fromString("#3299BB")
            secondaryBackgroundColor = TileColor.fromString("#424242")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/903157/Entrapped_InAPalette
     */
    ENTRAPPED_IN_A_PALETTE(
        colorTheme {
            name = "Entrapped In A Palette"
            accentColor = TileColor.fromString("#7B3B3B")
            primaryForegroundColor = TileColor.fromString("#B9D7D9")
            secondaryForegroundColor = TileColor.fromString("#668284")
            primaryBackgroundColor = TileColor.fromString("#493736")
            secondaryBackgroundColor = TileColor.fromString("#2A2829")
        }
    ),


    /**
     * Taken from
     * http://www.colourlovers.com/palette/678929/War
     */
    WAR(
        colorTheme {
            name = "War"
            accentColor = TileColor.fromString("#F21D41")
            primaryForegroundColor = TileColor.fromString("#EBEBBC")
            secondaryForegroundColor = TileColor.fromString("#BCE3C5")
            primaryBackgroundColor = TileColor.fromString("#82B3AE")
            secondaryBackgroundColor = TileColor.fromString("#230F2B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/557832/Captured_By_Pirates
     */
    CAPTURED_BY_PIRATES(
        colorTheme {
            name = "Captured By Pirates"
            accentColor = TileColor.fromString("#AB9597")
            primaryForegroundColor = TileColor.fromString("#FFFBF0")
            secondaryForegroundColor = TileColor.fromString("#968F4B")
            primaryBackgroundColor = TileColor.fromString("#7A6248")
            secondaryBackgroundColor = TileColor.fromString("#030506")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/4454375/ghost_of_a_chance
     */
    GHOST_OF_A_CHANCE(
        colorTheme {
            name = "Ghost Of A Chance"
            accentColor = TileColor.fromString("#95AC54")
            primaryForegroundColor = TileColor.fromString("#F6F1CB")
            secondaryForegroundColor = TileColor.fromString("#DFD4A7")
            primaryBackgroundColor = TileColor.fromString("#7F7C69")
            secondaryBackgroundColor = TileColor.fromString("#29210A")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/1585870/after_the_heist
     */
    AFTER_THE_HEIST(
        colorTheme {
            name = "After The Heist"
            accentColor = TileColor.fromString("#b86A6A")
            primaryForegroundColor = TileColor.fromString("#F8EDD1")
            secondaryForegroundColor = TileColor.fromString("#C5CFC6")
            primaryBackgroundColor = TileColor.fromString("#9D9D93")
            secondaryBackgroundColor = TileColor.fromString("#474843")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/731302/pablo_neruda
     */
    PABLO_NERUDA(
        colorTheme {
            name = "Pablo Neruda"
            accentColor = TileColor.fromString("#E6324B")
            primaryForegroundColor = TileColor.fromString("#F2E3C6")
            secondaryForegroundColor = TileColor.fromString("#FFC6A5")
            primaryBackgroundColor = TileColor.fromString("#353634")
            secondaryBackgroundColor = TileColor.fromString("#2B2B2B")
        }
    ),

    /**
     * Taken from
     * http://www.colourlovers.com/palette/2031222/Olive_Leaf_Tea
     */
    OLIVE_LEAF_TEA(
        colorTheme {
            name = "Olive Leaf Tea"
            accentColor = TileColor.fromString("#e99A44")
            primaryForegroundColor = TileColor.fromString("#EAE7D1")
            secondaryForegroundColor = TileColor.fromString("#CCC58E")
            primaryBackgroundColor = TileColor.fromString("#7B8455")
            secondaryBackgroundColor = TileColor.fromString("#485C2B")
        }
    ),

    /**
     * Taken from
     * https://www.colourlovers.com/palette/2420454/cyberpunk
     */
    CYBERPUNK(
        colorTheme {
            name = "Cyberpunk"
            accentColor = TileColor.fromString("#61D6C4")
            primaryForegroundColor = TileColor.fromString("#71918C")
            secondaryForegroundColor = TileColor.fromString("#3D615F")
            primaryBackgroundColor = TileColor.fromString("#25343B")
            secondaryBackgroundColor = TileColor.fromString("#212429")
        }
    ),

    // these are slack themes

    AFTERGLOW(
        colorTheme {
            name = "Afterglow"
            accentColor = TileColor.fromString("#ADBA4E")
            primaryForegroundColor = TileColor.fromString("#DEDEDE")
            secondaryForegroundColor = TileColor.fromString("#D2D6D6")
            primaryBackgroundColor = TileColor.fromString("#2F2C2F")
            secondaryBackgroundColor = TileColor.fromString("#252525")
        }
    ),

    AMIGA_OS(
        colorTheme {
            name = "Amiga OS"
            accentColor = TileColor.fromString("#F08000")
            primaryForegroundColor = TileColor.fromString("#FFFFFF")
            secondaryForegroundColor = TileColor.fromString("#dddddd")
            primaryBackgroundColor = TileColor.fromString("#0050A0")
            secondaryBackgroundColor = TileColor.fromString("#000020")
        }
    ),

    ANCESTRY(
        colorTheme {
            name = "Ancestry"
            accentColor = TileColor.fromString("#F3B670")
            primaryForegroundColor = TileColor.fromString("#9CBE30")
            secondaryForegroundColor = TileColor.fromString("#7A9C0F")
            primaryBackgroundColor = TileColor.fromString("#706B63")
            secondaryBackgroundColor = TileColor.fromString("#534D46")
        }
    ),

    ARC(
        colorTheme {
            name = "Arc"
            accentColor = TileColor.fromString("#5294E2")
            primaryForegroundColor = TileColor.fromString("#FFFFFF")
            secondaryForegroundColor = TileColor.fromString("#dddddd")
            primaryBackgroundColor = TileColor.fromString("#4A5664")
            secondaryBackgroundColor = TileColor.fromString("#303641")
        }
    ),

    FOREST(
        colorTheme {
            name = "Forest"
            accentColor = TileColor.fromString("#94E864")
            primaryForegroundColor = TileColor.fromString("#FFFFFF")
            secondaryForegroundColor = TileColor.fromString("#dddddd")
            primaryBackgroundColor = TileColor.fromString("#077A07")
            secondaryBackgroundColor = TileColor.fromString("#033313")
        }
    ),

    LINUX_MINT_DARK(
        colorTheme {
            name = "Linux Mint Dark"
            accentColor = TileColor.fromString("#8FA876")
            primaryForegroundColor = TileColor.fromString("#FFFFFF")
            secondaryForegroundColor = TileColor.fromString("#818181")
            primaryBackgroundColor = TileColor.fromString("#353535")
            secondaryBackgroundColor = TileColor.fromString("#2F2F2F")
        }
    ),

    NORD(
        colorTheme {
            name = "Nord"
            accentColor = TileColor.fromString("#A3BE8C")
            primaryForegroundColor = TileColor.fromString("#D8DEE9")
            secondaryForegroundColor = TileColor.fromString("#81A1C1")
            primaryBackgroundColor = TileColor.fromString("#3B4252")
            secondaryBackgroundColor = TileColor.fromString("#2E3440")
        }
    ),

    TRON(
        colorTheme {
            name = "Tron"
            accentColor = TileColor.fromString("#1EB8EB")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#dddddd")
            primaryBackgroundColor = TileColor.fromString("#424242")
            secondaryBackgroundColor = TileColor.fromString("#000000")
        }
    ),

    SAIKU(
        colorTheme {
            name = "Saiku"
            accentColor = TileColor.fromString("#ffffff")
            primaryForegroundColor = TileColor.fromString("#cccccc")
            secondaryForegroundColor = TileColor.fromString("#999999")
            primaryBackgroundColor = TileColor.fromString("#AE1817")
            secondaryBackgroundColor = TileColor.fromString("#232323")
        }
    ),

    INGRESS_RESISTANCE(
        colorTheme {
            name = "Ingress Resistance"
            accentColor = TileColor.fromString("#F1C248")
            primaryForegroundColor = TileColor.fromString("#34EAF5")
            secondaryForegroundColor = TileColor.fromString("#0492D0")
            primaryBackgroundColor = TileColor.fromString("#393218")
            secondaryBackgroundColor = TileColor.fromString("#000E0F")
        }
    ),

    INGRESS_ENLIGHTENED(
        colorTheme {
            name = "Ingress Enlightened"
            accentColor = TileColor.fromString("#02BF02")
            primaryForegroundColor = TileColor.fromString("#34EAF5")
            secondaryForegroundColor = TileColor.fromString("#0492D0")
            primaryBackgroundColor = TileColor.fromString("#393218")
            secondaryBackgroundColor = TileColor.fromString("#000E0F")
        }
    ),

    ZENBURN_VANILLA(
        colorTheme {
            name = "Zenburn Vanilla"
            accentColor = TileColor.fromString("#f0dfaf")
            primaryForegroundColor = TileColor.fromString("#dcdccc")
            secondaryForegroundColor = TileColor.fromString("#9fafaf")
            primaryBackgroundColor = TileColor.fromString("#333333")
            secondaryBackgroundColor = TileColor.fromString("#1e2320")
        }
    ),

    ZENBURN_PINK(
        colorTheme {
            name = "Zenburn Pink"
            accentColor = TileColor.fromString("#ecbcbc")
            primaryForegroundColor = TileColor.fromString("#dcdccc")
            secondaryForegroundColor = TileColor.fromString("#9fafaf")
            primaryBackgroundColor = TileColor.fromString("#333333")
            secondaryBackgroundColor = TileColor.fromString("#1e2320")
        }
    ),

    ZENBURN_GREEN(
        colorTheme {
            name = "Zenburn Green"
            accentColor = TileColor.fromString("#709080")
            primaryForegroundColor = TileColor.fromString("#dcdccc")
            secondaryForegroundColor = TileColor.fromString("#9fafaf")
            primaryBackgroundColor = TileColor.fromString("#333333")
            secondaryBackgroundColor = TileColor.fromString("#1e2320")
        }
    ),

    MONOKAI_YELLOW(
        colorTheme {
            name = "Monokai Yellow"
            accentColor = TileColor.fromString("#ffd866")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    MONOKAI_PINK(
        colorTheme {
            name = "Monokai Pink"
            accentColor = TileColor.fromString("#ff6188")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    MONOKAI_GREEN(
        colorTheme {
            name = "Monokai Green"
            accentColor = TileColor.fromString("#a9dc76")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    MONOKAI_ORANGE(
        colorTheme {
            name = "Monokai Orange"
            accentColor = TileColor.fromString("#fc9867")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    MONOKAI_VIOLET(
        colorTheme {
            name = "Monokai Violet"
            accentColor = TileColor.fromString("#ab9df2")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    MONOKAI_BLUE(
        colorTheme {
            name = "Monokai Blue"
            accentColor = TileColor.fromString("#78dce8")
            primaryForegroundColor = TileColor.fromString("#ffffff")
            secondaryForegroundColor = TileColor.fromString("#fdf9f3")
            primaryBackgroundColor = TileColor.fromString("#3e3b3f")
            secondaryBackgroundColor = TileColor.fromString("#2c292d")
        }
    ),

    /**
     * All Solarized themes are taken from:
     * http://www.zovirl.com/2011/07/22/solarized_cheat_sheet/
     */
    SOLARIZED_DARK_YELLOW(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Yellow"
            accentColor = TileColor.fromString("#b58900")
        }
    ),

    SOLARIZED_DARK_ORANGE(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Orange"
            accentColor = TileColor.fromString("#cb4b16")
        }
    ),

    SOLARIZED_DARK_RED(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Red"
            accentColor = TileColor.fromString("#dc322f")
        }
    ),

    SOLARIZED_DARK_MAGENTA(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Magenta"
            accentColor = TileColor.fromString("#d33682")
        }
    ),

    SOLARIZED_DARK_VIOLET(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Violet"
            accentColor = TileColor.fromString("#6c71c4")
        }
    ),

    SOLARIZED_DARK_BLUE(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Blue"
            accentColor = TileColor.fromString("#268bd2")
        }
    ),

    SOLARIZED_DARK_CYAN(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Cyan"
            accentColor = TileColor.fromString("#2aa198")
        }
    ),

    SOLARIZED_DARK_GREEN(
        SOLARIZED_DARK_BASE.theme.copy {
            name = "Solarized Dark Green"
            accentColor = TileColor.fromString("#859900")
        }
    ),

    SOLARIZED_LIGHT_YELLOW(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Yellow"
            accentColor = TileColor.fromString("#b58900")
        }
    ),

    SOLARIZED_LIGHT_ORANGE(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Orange"
            accentColor = TileColor.fromString("#cb4b16")
        }
    ),

    SOLARIZED_LIGHT_RED(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Red"
            accentColor = TileColor.fromString("#dc322f")
        }
    ),

    SOLARIZED_LIGHT_MAGENTA(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Magenta"
            accentColor = TileColor.fromString("#d33682")
        }
    ),

    SOLARIZED_LIGHT_VIOLET(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Violet"
            accentColor = TileColor.fromString("#6c71c4")
        }
    ),

    SOLARIZED_LIGHT_BLUE(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Blue"
            accentColor = TileColor.fromString("#268bd2")
        }
    ),

    SOLARIZED_LIGHT_CYAN(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Cyan"
            accentColor = TileColor.fromString("#2aa198")
        }
    ),

    SOLARIZED_LIGHT_GREEN(
        SOLARIZED_LIGHT_BASE.theme.copy {
            name = "Solarized Light Green"
            accentColor = TileColor.fromString("#859900")
        }
    );

    fun getTheme() = colorTheme

    enum class SolarizedBase(val theme: ColorTheme) {
        SOLARIZED_DARK_BASE(
            colorTheme {
                primaryForegroundColor = TileColor.fromString("#fdf6e3")
                // note that this was made darker because there was not enough contrast
                secondaryForegroundColor = TileColor.fromString("#cec8b5")
                primaryBackgroundColor = TileColor.fromString("#073642")
                secondaryBackgroundColor = TileColor.fromString("#002b36")
            }
        ),

        SOLARIZED_LIGHT_BASE(
            colorTheme {
                primaryForegroundColor = TileColor.fromString("#002b36")
                // note that this was made lighter because there was not enough contrast
                secondaryForegroundColor = TileColor.fromString("#275662")
                primaryBackgroundColor = TileColor.fromString("#fdf6e3")
                secondaryBackgroundColor = TileColor.fromString("#eee8d5")
            }
        )
    }
}
