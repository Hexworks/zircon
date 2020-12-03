package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_DARK_BASE
import org.hexworks.zircon.internal.resource.ColorThemeResource.SolarizedBase.SOLARIZED_LIGHT_BASE

@Suppress("SpellCheckingInspection")
enum class ColorThemeResource(private val colorTheme: ColorTheme) {

    /**
     * This is a null object for color themes indicating that no
     * theme is chosen.
     */
    DEFAULT(ColorThemeBuilder.newBuilder()
        .withName("Default")
        .withAccentColor(ANSITileColor.BRIGHT_GREEN)
        .withPrimaryForegroundColor(ANSITileColor.BRIGHT_MAGENTA)
        .withSecondaryForegroundColor(ANSITileColor.BRIGHT_BLUE)
        .withPrimaryBackgroundColor(ANSITileColor.BRIGHT_YELLOW)
        .withSecondaryBackgroundColor(ANSITileColor.BRIGHT_RED)
        .build()
    ),

    /**
     * This is the theme that was made by Hexworks.
     */
    HEXWORKS(ColorThemeBuilder.newBuilder()
            .withName("Hexworks")
            .withAccentColor(TileColor.fromString("#52b61d"))
            .withPrimaryForegroundColor(TileColor.fromString("#b1d5d7"))
            .withSecondaryForegroundColor(TileColor.fromString("#91b5b7"))
            .withPrimaryBackgroundColor(TileColor.fromString("#5c335a"))
            .withSecondaryBackgroundColor(TileColor.fromString("#341139"))
            .build()
    ),

    /**
     * Taken from
     * https://lospec.com/palette-list/stale-sunset
     */
    STALE_SUNSET(ColorThemeBuilder.newBuilder()
            .withName("Stale Sunset")
            .withAccentColor(TileColor.fromString("#ffe377"))
            .withPrimaryForegroundColor(TileColor.fromString("#cdba76"))
            .withSecondaryForegroundColor(TileColor.fromString("#bda576"))
            .withPrimaryBackgroundColor(TileColor.fromString("#52484e"))
            .withSecondaryBackgroundColor(TileColor.fromString("#292442"))
            .build()
    ),
    /**
     * Taken from
     * https://lospec.com/palette-list/afternoon-haze
     */
    AFTERNOON_HAZE(ColorThemeBuilder.newBuilder()
            .withName("Afternoon Haze")
            .withAccentColor(TileColor.fromString("#b0523f"))
            .withPrimaryForegroundColor(TileColor.fromString("#2f6f87"))
            .withSecondaryForegroundColor(TileColor.fromString("#243a56"))
            .withPrimaryBackgroundColor(TileColor.fromString("#cbbfb2"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0d1221"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/winter-wonderland
     */
    WINTER_WONDERLAND(ColorThemeBuilder.newBuilder()
            .withName("Winter Wonderland")
            .withAccentColor(TileColor.fromString("#8bcadd"))
            .withPrimaryForegroundColor(TileColor.fromString("#d6e1e9"))
            .withSecondaryForegroundColor(TileColor.fromString("#a7bcc9"))
            .withPrimaryBackgroundColor(TileColor.fromString("#2c4a78"))
            .withSecondaryBackgroundColor(TileColor.fromString("#20284e"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8
     */
    PETITE(ColorThemeBuilder.newBuilder()
            .withName("Petite")
            .withAccentColor(TileColor.fromString("#f8f4e4"))
            .withPrimaryForegroundColor(TileColor.fromString("#efd98d"))
            .withSecondaryForegroundColor(TileColor.fromString("#c5af63"))
            .withPrimaryBackgroundColor(TileColor.fromString("#272f3b"))
            .withSecondaryBackgroundColor(TileColor.fromString("#181b22"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/waverator
     */
    WAVERATOR(ColorThemeBuilder.newBuilder()
            .withName("Waverator")
            .withAccentColor(TileColor.fromString("#cbffd8"))
            .withPrimaryForegroundColor(TileColor.fromString("#70d38b"))
            .withSecondaryForegroundColor(TileColor.fromString("#53a788"))
            .withPrimaryBackgroundColor(TileColor.fromString("#23313d"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0c0d14"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/justparchment8
     */
    JUSTPARCHMENT(ColorThemeBuilder.newBuilder()
            .withName("Justparchment")
            .withAccentColor(TileColor.fromString("#524839"))
            .withPrimaryForegroundColor(TileColor.fromString("#73654a"))
            .withSecondaryForegroundColor(TileColor.fromString("#8b7d62"))
            .withPrimaryBackgroundColor(TileColor.fromString("#e6ceac"))
            .withSecondaryBackgroundColor(TileColor.fromString("#292418"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/ammo-8
     */
    AMMO(ColorThemeBuilder.newBuilder()
            .withName("Ammo")
            .withAccentColor(TileColor.fromString("#eeffcc"))
            .withPrimaryForegroundColor(TileColor.fromString("#bedc7f"))
            .withSecondaryForegroundColor(TileColor.fromString("#4d8061"))
            .withPrimaryBackgroundColor(TileColor.fromString("#112318"))
            .withSecondaryBackgroundColor(TileColor.fromString("#040c06"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/nyx8
     */
    NYX(ColorThemeBuilder.newBuilder()
            .withName("Nyx")
            .withAccentColor(TileColor.fromString("#f6d6bd"))
            .withPrimaryForegroundColor(TileColor.fromString("#c3a38a"))
            .withSecondaryForegroundColor(TileColor.fromString("#997577"))
            .withPrimaryBackgroundColor(TileColor.fromString("#20394f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0f2a3f"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/slso8
     */
    SLSO(ColorThemeBuilder.newBuilder()
            .withName("SLSO")
            .withAccentColor(TileColor.fromString("#ffaa5e"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffecd6"))
            .withSecondaryForegroundColor(TileColor.fromString("#ffd4a3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#203c56"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0d2b45"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/beyond-the-sea-7
     */
    BEYOND_THE_SEA(ColorThemeBuilder.newBuilder()
            .withName("Beyond The Sea")
            .withAccentColor(TileColor.fromString("#f4fff7"))
            .withPrimaryForegroundColor(TileColor.fromString("#aceed1"))
            .withSecondaryForegroundColor(TileColor.fromString("#6bd0b5"))
            .withPrimaryBackgroundColor(TileColor.fromString("#313b55"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e1a2a"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/forest-glow
     */
    FOREST_GLOW(ColorThemeBuilder.newBuilder()
            .withName("Forest Glow")
            .withAccentColor(TileColor.fromString("#deca54"))
            .withPrimaryForegroundColor(TileColor.fromString("#97933a"))
            .withSecondaryForegroundColor(TileColor.fromString("#5f6d43"))
            .withPrimaryBackgroundColor(TileColor.fromString("#1f2c3d"))
            .withSecondaryBackgroundColor(TileColor.fromString("#00070d"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/cherry-bear
     */
    CHERRY_BEAR(ColorThemeBuilder.newBuilder()
            .withName("Cherry Bear")
            .withAccentColor(TileColor.fromString("#fbb396"))
            .withPrimaryForegroundColor(TileColor.fromString("#bc6a6a"))
            .withSecondaryForegroundColor(TileColor.fromString("#a35454"))
            .withPrimaryBackgroundColor(TileColor.fromString("#472a36"))
            .withSecondaryBackgroundColor(TileColor.fromString("#0d0c11"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/supernova-7
     */
    SUPERNOVA(ColorThemeBuilder.newBuilder()
            .withName("Supernova")
            .withAccentColor(TileColor.fromString("#ffce9c"))
            .withPrimaryForegroundColor(TileColor.fromString("#cf7862"))
            .withSecondaryForegroundColor(TileColor.fromString("#a75252"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3d203b"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1a080e"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/cp_rust-5
     */
    RUST(ColorThemeBuilder.newBuilder()
            .withName("Rust")
            .withAccentColor(TileColor.fromString("#ffe2c6"))
            .withPrimaryForegroundColor(TileColor.fromString("#f0bb9c"))
            .withSecondaryForegroundColor(TileColor.fromString("#e18866"))
            .withPrimaryBackgroundColor(TileColor.fromString("#712f30"))
            .withSecondaryBackgroundColor(TileColor.fromString("#230000"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/cursed-turkey
     */
    CURSED_TURKEY(ColorThemeBuilder.newBuilder()
            .withName("Cursed Turkey")
            .withAccentColor(TileColor.fromString("#d7ac64"))
            .withPrimaryForegroundColor(TileColor.fromString("#df8c00"))
            .withSecondaryForegroundColor(TileColor.fromString("#db7209"))
            .withPrimaryBackgroundColor(TileColor.fromString("#b32a12"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e110c"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/molten
     */
    MOLTEN(ColorThemeBuilder.newBuilder()
            .withName("Molten")
            .withAccentColor(TileColor.fromString("#fd724e"))
            .withPrimaryForegroundColor(TileColor.fromString("#a02f40"))
            .withSecondaryForegroundColor(TileColor.fromString("#5f2f45"))
            .withPrimaryBackgroundColor(TileColor.fromString("#261b2e"))
            .withSecondaryBackgroundColor(TileColor.fromString("#201727"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_RED(ColorThemeBuilder.newBuilder()
            .withName("Stormy Red")
            .withAccentColor(TileColor.fromString("#a95a3f"))
            .withPrimaryForegroundColor(TileColor.fromString("#f8eebf"))
            .withSecondaryForegroundColor(TileColor.fromString("#edbb70"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3a5043"))
            .withSecondaryBackgroundColor(TileColor.fromString("#242828"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/stormy-6
     */
    STORMY_GREEN(ColorThemeBuilder.newBuilder()
            .withName("Stormy Green")
            .withAccentColor(TileColor.fromString("#7f9860"))
            .withPrimaryForegroundColor(TileColor.fromString("#f8eebf"))
            .withSecondaryForegroundColor(TileColor.fromString("#edbb70"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3a5043"))
            .withSecondaryBackgroundColor(TileColor.fromString("#242828"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    OIL(ColorThemeBuilder.newBuilder()
            .withName("Oil")
            .withAccentColor(TileColor.fromString("#c69fa5"))
            .withPrimaryForegroundColor(TileColor.fromString("#fbf5ef"))
            .withSecondaryForegroundColor(TileColor.fromString("#f2d3ab"))
            .withPrimaryBackgroundColor(TileColor.fromString("#494d7e"))
            .withSecondaryBackgroundColor(TileColor.fromString("#272744"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/natures-atmosphere
     */
    NATURES_ATMOSPHERE(ColorThemeBuilder.newBuilder()
            .withName("Nature's Atmosphere")
            .withAccentColor(TileColor.fromString("#a6a220"))
            .withPrimaryForegroundColor(TileColor.fromString("#efbe8e"))
            .withSecondaryForegroundColor(TileColor.fromString("#a6d3ff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#45619e"))
            .withSecondaryBackgroundColor(TileColor.fromString("#040404"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/infinite-ikea
     */
    INFINITE_IKEA(ColorThemeBuilder.newBuilder()
            .withName("Infinite IKEA")
            .withAccentColor(TileColor.fromString("#f6d76b"))
            .withPrimaryForegroundColor(TileColor.fromString("#f6f0f7"))
            .withSecondaryForegroundColor(TileColor.fromString("#45a9ff"))
            .withPrimaryBackgroundColor(TileColor.fromString("#104a7d"))
            .withSecondaryBackgroundColor(TileColor.fromString("#12223c"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/fire-weeds
     */
    FIRE_WEEDS(ColorThemeBuilder.newBuilder()
            .withName("Fire Weeds")
            .withAccentColor(TileColor.fromString("#e2d6fe"))
            .withPrimaryForegroundColor(TileColor.fromString("#fea631"))
            .withSecondaryForegroundColor(TileColor.fromString("#e5371b"))
            .withPrimaryBackgroundColor(TileColor.fromString("#630f19"))
            .withSecondaryBackgroundColor(TileColor.fromString("#060013"))
            .build()),
    /**
     * Taken from
     * https://lospec.com/palette-list/pola5
     */
    POLA(ColorThemeBuilder.newBuilder()
            .withName("Pola")
            .withAccentColor(TileColor.fromString("#ebf9ff"))
            .withPrimaryForegroundColor(TileColor.fromString("#acd6f6"))
            .withSecondaryForegroundColor(TileColor.fromString("#52a5de"))
            .withPrimaryBackgroundColor(TileColor.fromString("#18284a"))
            .withSecondaryBackgroundColor(TileColor.fromString("#070810"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/spanish-sunset
     */
    SPANISH_SUNSET(ColorThemeBuilder.newBuilder()
            .withName("Spanish Sunset")
            .withAccentColor(TileColor.fromString("#fd724e"))
            .withPrimaryForegroundColor(TileColor.fromString("#f5ddbc"))
            .withSecondaryForegroundColor(TileColor.fromString("#fabb64"))
            .withPrimaryBackgroundColor(TileColor.fromString("#a02f40"))
            .withSecondaryBackgroundColor(TileColor.fromString("#5f2f45"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/discord
     */
    DISCORD(ColorThemeBuilder.newBuilder()
            .withName("Discord")
            .withAccentColor(TileColor.fromString("#7289da"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#99aab5"))
            .withPrimaryBackgroundColor(TileColor.fromString("#2c2f33"))
            .withSecondaryBackgroundColor(TileColor.fromString("#23272a"))
            .build()),

    /**
     * Taken from
     * https://lospec.com/palette-list/petite-8-afterdark
     */
    AFTER_DARK(ColorThemeBuilder.newBuilder()
            .withName("After Dark")
            .withAccentColor(TileColor.fromString("#bb9a67"))
            .withPrimaryForegroundColor(TileColor.fromString("#a07c43"))
            .withSecondaryForegroundColor(TileColor.fromString("#776131"))
            .withPrimaryBackgroundColor(TileColor.fromString("#272b29"))
            .withSecondaryBackgroundColor(TileColor.fromString("#161c20"))
            .build()),
    /**
     * Taken from
     * http://www.colourlovers.com/palette/580974/Adrift_in_Dreams
     */
    ADRIFT_IN_DREAMS(ColorThemeBuilder.newBuilder()
            .withName("Adrift In Dreams")
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
            .withName("Let Them Eat Cake")
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
            .withName("Tech Light")
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
            .withName("Headache")
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
            .withName("Gamebookers")
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
            .withName("Entrapped In A Palette")
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
            .withName("War")
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
            .withName("Captured By Pirates")
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
            .withName("Ghost Of A Chance")
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
            .withName("After The Heist")
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
            .withName("Pablo Neruda")
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
            .withName("Olive Leaf Tea")
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
            .withName("Cyberpunk")
            .withAccentColor(TileColor.fromString("#61D6C4"))
            .withPrimaryForegroundColor(TileColor.fromString("#71918C"))
            .withSecondaryForegroundColor(TileColor.fromString("#3D615F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#25343B"))
            .withSecondaryBackgroundColor(TileColor.fromString("#212429"))
            .build()),

    // these are slack themes

    AFTERGLOW(ColorThemeBuilder.newBuilder()
            .withName("Afterglow")
            .withAccentColor(TileColor.fromString("#ADBA4E"))
            .withPrimaryForegroundColor(TileColor.fromString("#DEDEDE"))
            .withSecondaryForegroundColor(TileColor.fromString("#D2D6D6"))
            .withPrimaryBackgroundColor(TileColor.fromString("#2F2C2F"))
            .withSecondaryBackgroundColor(TileColor.fromString("#252525"))
            .build()),

    AMIGA_OS(ColorThemeBuilder.newBuilder()
            .withName("Amiga OS")
            .withAccentColor(TileColor.fromString("#F08000"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#0050A0"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000020"))
            .build()),

    ANCESTRY(ColorThemeBuilder.newBuilder()
            .withName("Ancestry")
            .withAccentColor(TileColor.fromString("#F3B670"))
            .withPrimaryForegroundColor(TileColor.fromString("#9CBE30"))
            .withSecondaryForegroundColor(TileColor.fromString("#7A9C0F"))
            .withPrimaryBackgroundColor(TileColor.fromString("#706B63"))
            .withSecondaryBackgroundColor(TileColor.fromString("#534D46"))
            .build()),

    ARC(ColorThemeBuilder.newBuilder()
            .withName("Arc")
            .withAccentColor(TileColor.fromString("#5294E2"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#4A5664"))
            .withSecondaryBackgroundColor(TileColor.fromString("#303641"))
            .build()),

    FOREST(ColorThemeBuilder.newBuilder()
            .withName("Forest")
            .withAccentColor(TileColor.fromString("#94E864"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#077A07"))
            .withSecondaryBackgroundColor(TileColor.fromString("#033313"))
            .build()),

    LINUX_MINT_DARK(ColorThemeBuilder.newBuilder()
            .withName("Linux Mint Dark")
            .withAccentColor(TileColor.fromString("#8FA876"))
            .withPrimaryForegroundColor(TileColor.fromString("#FFFFFF"))
            .withSecondaryForegroundColor(TileColor.fromString("#818181"))
            .withPrimaryBackgroundColor(TileColor.fromString("#353535"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2F2F2F"))
            .build()),

    NORD(ColorThemeBuilder.newBuilder()
            .withName("Nord")
            .withAccentColor(TileColor.fromString("#A3BE8C"))
            .withPrimaryForegroundColor(TileColor.fromString("#D8DEE9"))
            .withSecondaryForegroundColor(TileColor.fromString("#81A1C1"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3B4252"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2E3440"))
            .build()),

    TRON(ColorThemeBuilder.newBuilder()
            .withName("Tron")
            .withAccentColor(TileColor.fromString("#1EB8EB"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#dddddd"))
            .withPrimaryBackgroundColor(TileColor.fromString("#424242"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000000"))
            .build()),

    SAIKU(ColorThemeBuilder.newBuilder()
            .withName("Saiku")
            .withAccentColor(TileColor.fromString("#ffffff"))
            .withPrimaryForegroundColor(TileColor.fromString("#cccccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#999999"))
            .withPrimaryBackgroundColor(TileColor.fromString("#AE1817"))
            .withSecondaryBackgroundColor(TileColor.fromString("#232323"))
            .build()),

    INGRESS_RESISTANCE(ColorThemeBuilder.newBuilder()
            .withName("Ingress Resistance")
            .withAccentColor(TileColor.fromString("#F1C248"))
            .withPrimaryForegroundColor(TileColor.fromString("#34EAF5"))
            .withSecondaryForegroundColor(TileColor.fromString("#0492D0"))
            .withPrimaryBackgroundColor(TileColor.fromString("#393218"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    INGRESS_ENLIGHTENED(ColorThemeBuilder.newBuilder()
            .withName("Ingress Enlightened")
            .withAccentColor(TileColor.fromString("#02BF02"))
            .withPrimaryForegroundColor(TileColor.fromString("#34EAF5"))
            .withSecondaryForegroundColor(TileColor.fromString("#0492D0"))
            .withPrimaryBackgroundColor(TileColor.fromString("#393218"))
            .withSecondaryBackgroundColor(TileColor.fromString("#000E0F"))
            .build()),

    ZENBURN_VANILLA(ColorThemeBuilder.newBuilder()
            .withName("Zenburn Vanilla")
            .withAccentColor(TileColor.fromString("#f0dfaf"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_PINK(ColorThemeBuilder.newBuilder()
            .withName("Zenburn Pink")
            .withAccentColor(TileColor.fromString("#ecbcbc"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    ZENBURN_GREEN(ColorThemeBuilder.newBuilder()
            .withName("Zenburn Green")
            .withAccentColor(TileColor.fromString("#709080"))
            .withPrimaryForegroundColor(TileColor.fromString("#dcdccc"))
            .withSecondaryForegroundColor(TileColor.fromString("#9fafaf"))
            .withPrimaryBackgroundColor(TileColor.fromString("#333333"))
            .withSecondaryBackgroundColor(TileColor.fromString("#1e2320"))
            .build()),

    MONOKAI_YELLOW(ColorThemeBuilder.newBuilder()
            .withName("Monokai Yellow")
            .withAccentColor(TileColor.fromString("#ffd866"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_PINK(ColorThemeBuilder.newBuilder()
            .withName("Monokai Pink")
            .withAccentColor(TileColor.fromString("#ff6188"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_GREEN(ColorThemeBuilder.newBuilder()
            .withName("Monokai Green")
            .withAccentColor(TileColor.fromString("#a9dc76"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_ORANGE(ColorThemeBuilder.newBuilder()
            .withName("Monokai Orange")
            .withAccentColor(TileColor.fromString("#fc9867"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_VIOLET(ColorThemeBuilder.newBuilder()
            .withName("Monokai Violet")
            .withAccentColor(TileColor.fromString("#ab9df2"))
            .withPrimaryForegroundColor(TileColor.fromString("#ffffff"))
            .withSecondaryForegroundColor(TileColor.fromString("#fdf9f3"))
            .withPrimaryBackgroundColor(TileColor.fromString("#3e3b3f"))
            .withSecondaryBackgroundColor(TileColor.fromString("#2c292d"))
            .build()),

    MONOKAI_BLUE(ColorThemeBuilder.newBuilder()
            .withName("Monokai Blue")
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
            .withName("Solarized Dark Yellow")
            .withAccentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_DARK_ORANGE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Orange")
            .withAccentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_DARK_RED(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Red")
            .withAccentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_DARK_MAGENTA(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Magenta")
            .withAccentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_DARK_VIOLET(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Violet")
            .withAccentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_DARK_BLUE(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Blue")
            .withAccentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_DARK_CYAN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Cyan")
            .withAccentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_DARK_GREEN(SOLARIZED_DARK_BASE.colorThemeBuilder.copy()
            .withName("Solarized Dark Green")
            .withAccentColor(TileColor.fromString("#859900"))
            .build()),

    SOLARIZED_LIGHT_YELLOW(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Yellow")
            .withAccentColor(TileColor.fromString("#b58900"))
            .build()),

    SOLARIZED_LIGHT_ORANGE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Orange")
            .withAccentColor(TileColor.fromString("#cb4b16"))
            .build()),

    SOLARIZED_LIGHT_RED(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Red")
            .withAccentColor(TileColor.fromString("#dc322f"))
            .build()),

    SOLARIZED_LIGHT_MAGENTA(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Magenta")
            .withAccentColor(TileColor.fromString("#d33682"))
            .build()),

    SOLARIZED_LIGHT_VIOLET(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Violet")
            .withAccentColor(TileColor.fromString("#6c71c4"))
            .build()),

    SOLARIZED_LIGHT_BLUE(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Blue")
            .withAccentColor(TileColor.fromString("#268bd2"))
            .build()),

    SOLARIZED_LIGHT_CYAN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Cyan")
            .withAccentColor(TileColor.fromString("#2aa198"))
            .build()),

    SOLARIZED_LIGHT_GREEN(SOLARIZED_LIGHT_BASE.colorThemeBuilder.copy()
            .withName("Solarized Light Green")
            .withAccentColor(TileColor.fromString("#859900"))
            .build());

    fun getTheme() = colorTheme

    enum class SolarizedBase(val colorThemeBuilder: ColorThemeBuilder) {
        SOLARIZED_DARK_BASE(ColorThemeBuilder.newBuilder()
                .withPrimaryForegroundColor(TileColor.fromString("#fdf6e3"))
                // note that this was made darker because there was not enough contrast
                .withSecondaryForegroundColor(TileColor.fromString("#cec8b5"))
                .withPrimaryBackgroundColor(TileColor.fromString("#073642"))
                .withSecondaryBackgroundColor(TileColor.fromString("#002b36"))),

        SOLARIZED_LIGHT_BASE(ColorThemeBuilder.newBuilder()
                .withPrimaryForegroundColor(TileColor.fromString("#002b36"))
                // note that this was made lighter because there was not enough contrast
                .withSecondaryForegroundColor(TileColor.fromString("#275662"))
                .withPrimaryBackgroundColor(TileColor.fromString("#fdf6e3"))
                .withSecondaryBackgroundColor(TileColor.fromString("#eee8d5")))
    }
}
