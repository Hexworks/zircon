package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.CatppuccinAccent
import org.hexworks.zircon.api.component.theme.CatppuccinFlavor
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.KanagawaVariant
import org.hexworks.zircon.api.component.theme.TokyoNightStyle
import org.hexworks.zircon.api.component.theme.blend
import org.hexworks.zircon.api.component.theme.componentPalette

/**
 * The bundled palettes, mapped onto the semantic keys by hand after each project's own
 * style guide (`docs/research/semantic-component-palettes.md`, sections 7 and 10). Hex values
 * are from the primary sources: catppuccin/palette `palette.json` v1.8.0,
 * folke/tokyonight.nvim `lua/tokyonight/colors/` (one file per style) (`day` from the generated
 * `extras/helix/tokyonight_day.toml`), rebelot/kanagawa.nvim `lua/kanagawa/colors.lua` and
 * `themes.lua`, Ethan Schoonover's Solarized table (`ethanschoonover.com/solarized`), morhetz/gruvbox
 * `colors/gruvbox.vim` (medium contrast).
 */
internal object BundledPalettes {

    private fun c(hex: String): Color = Color.fromString(hex)

    // ---- Catppuccin: the 26 named colors per flavor, `palette.json` order.

    private class Catppuccin(
        val rosewater: Color, val flamingo: Color, val pink: Color, val mauve: Color, val red: Color,
        val maroon: Color, val peach: Color, val yellow: Color, val green: Color, val teal: Color,
        val sky: Color, val sapphire: Color, val blue: Color, val lavender: Color,
        val text: Color, val subtext1: Color, val subtext0: Color,
        val overlay2: Color, val overlay1: Color, val overlay0: Color,
        val surface2: Color, val surface1: Color, val surface0: Color,
        val base: Color, val mantle: Color, val crust: Color,
    ) {
        fun accent(accent: CatppuccinAccent): Color = when (accent) {
            CatppuccinAccent.ROSEWATER -> rosewater
            CatppuccinAccent.FLAMINGO -> flamingo
            CatppuccinAccent.PINK -> pink
            CatppuccinAccent.MAUVE -> mauve
            CatppuccinAccent.RED -> red
            CatppuccinAccent.MAROON -> maroon
            CatppuccinAccent.PEACH -> peach
            CatppuccinAccent.YELLOW -> yellow
            CatppuccinAccent.GREEN -> green
            CatppuccinAccent.TEAL -> teal
            CatppuccinAccent.SKY -> sky
            CatppuccinAccent.SAPPHIRE -> sapphire
            CatppuccinAccent.BLUE -> blue
            CatppuccinAccent.LAVENDER -> lavender
        }
    }

    private fun catppuccinColors(flavor: CatppuccinFlavor): Catppuccin = when (flavor) {
        CatppuccinFlavor.LATTE -> Catppuccin(
            c("#dc8a78"), c("#dd7878"), c("#ea76cb"), c("#8839ef"), c("#d20f39"), c("#e64553"), c("#fe640b"),
            c("#df8e1d"), c("#40a02b"), c("#179299"), c("#04a5e5"), c("#209fb5"), c("#1e66f5"), c("#7287fd"),
            c("#4c4f69"), c("#5c5f77"), c("#6c6f85"), c("#7c7f93"), c("#8c8fa1"), c("#9ca0b0"),
            c("#acb0be"), c("#bcc0cc"), c("#ccd0da"), c("#eff1f5"), c("#e6e9ef"), c("#dce0e8"),
        )
        CatppuccinFlavor.FRAPPE -> Catppuccin(
            c("#f2d5cf"), c("#eebebe"), c("#f4b8e4"), c("#ca9ee6"), c("#e78284"), c("#ea999c"), c("#ef9f76"),
            c("#e5c890"), c("#a6d189"), c("#81c8be"), c("#99d1db"), c("#85c1dc"), c("#8caaee"), c("#babbf1"),
            c("#c6d0f5"), c("#b5bfe2"), c("#a5adce"), c("#949cbb"), c("#838ba7"), c("#737994"),
            c("#626880"), c("#51576d"), c("#414559"), c("#303446"), c("#292c3c"), c("#232634"),
        )
        CatppuccinFlavor.MACCHIATO -> Catppuccin(
            c("#f4dbd6"), c("#f0c6c6"), c("#f5bde6"), c("#c6a0f6"), c("#ed8796"), c("#ee99a0"), c("#f5a97f"),
            c("#eed49f"), c("#a6da95"), c("#8bd5ca"), c("#91d7e3"), c("#7dc4e4"), c("#8aadf4"), c("#b7bdf8"),
            c("#cad3f5"), c("#b8c0e0"), c("#a5adcb"), c("#939ab7"), c("#8087a2"), c("#6e738d"),
            c("#5b6078"), c("#494d64"), c("#363a4f"), c("#24273a"), c("#1e2030"), c("#181926"),
        )
        CatppuccinFlavor.MOCHA -> Catppuccin(
            c("#f5e0dc"), c("#f2cdcd"), c("#f5c2e7"), c("#cba6f7"), c("#f38ba8"), c("#eba0ac"), c("#fab387"),
            c("#f9e2af"), c("#a6e3a1"), c("#94e2d5"), c("#89dceb"), c("#74c7ec"), c("#89b4fa"), c("#b4befe"),
            c("#cdd6f4"), c("#bac2de"), c("#a6adc8"), c("#9399b2"), c("#7f849c"), c("#6c7086"),
            c("#585b70"), c("#45475a"), c("#313244"), c("#1e1e2e"), c("#181825"), c("#11111b"),
        )
    }

    /**
     * The style guide's rows: Base is the pane, Surface 0/1 the surface elements, Mantle a
     * secondary pane, Subtext 0 labels, Overlay 1 subtle, Overlay 0 / Lavender the inactive /
     * active border, "On Accent" is Base (Text where that reads better: Latte's yellow and
     * green), Teal / Green / Yellow / Red the status colors, the selection an opaque stand-in
     * (Surface 2) for "Overlay 2 at 20-30%".
     */
    fun catppuccin(flavor: CatppuccinFlavor, accent: CatppuccinAccent): ComponentPalette {
        val p = catppuccinColors(flavor)
        fun on(color: Color) = readableOn(color, p.base, p.text)
        return componentPalette {
            name = "Catppuccin ${flavor.name.lowercase().replaceFirstChar { it.uppercase() }}" +
                    if (accent != CatppuccinAccent.BLUE) " (${accent.name.lowercase()})" else ""
            isDark = flavor.isDark
            background = p.base
            onBackground = p.text
            surface = p.surface0
            onSurface = p.text
            surfaceRaised = p.surface1
            onSurfaceRaised = p.text
            surfaceSunken = p.mantle
            textMuted = p.subtext0
            textDisabled = p.overlay1
            border = p.overlay0
            borderFocused = p.lavender
            primary = p.accent(accent)
            onPrimary = on(p.accent(accent))
            secondary = p.mauve
            onSecondary = on(p.mauve)
            this.accent = p.peach
            onAccent = on(p.peach)
            selection = p.surface2
            onSelection = p.text
            info = p.teal
            onInfo = on(p.teal)
            success = p.green
            onSuccess = on(p.green)
            warning = p.yellow
            onWarning = on(p.yellow)
            error = p.red
            onError = on(p.red)
        }
    }

    // ---- Tokyo Night: the palette table of each style.

    private class TokyoNight(
        val bg: Color, val bgDark: Color, val bgHighlight: Color, val terminalBlack: Color,
        val fg: Color, val dark5: Color, val comment: Color,
        val blue: Color, val blue0: Color, val blue1: Color, val blue2: Color,
        val magenta: Color, val orange: Color, val green: Color, val yellow: Color, val red1: Color,
        /** `border_highlight` is computed by the theme (`blend_bg`); `day` ships it. */
        val borderHighlight: Color = blue1.blend(bg, 0.2),
        /**
         * The theme's `bg_visual` is `blue0` at 40% over `bg`, which all but vanishes on
         * `bg_highlight` (1.1:1), where a selected tab sits; 70% keeps it a Tokyo Night blue
         * and makes the selection visible. `day` ships its own.
         */
        val bgVisual: Color = blue0.blend(bg, 0.3),
        /** The popup step; `terminal_black` unless a style says otherwise. */
        val raised: Color = terminalBlack,
    )

    private fun tokyoNightColors(style: TokyoNightStyle): TokyoNight = when (style) {
        // `night.lua` is `storm.lua` with three backgrounds overridden.
        TokyoNightStyle.NIGHT -> TokyoNight(
            bg = c("#1a1b26"), bgDark = c("#16161e"), bgHighlight = c("#292e42"), terminalBlack = c("#414868"),
            fg = c("#c0caf5"), dark5 = c("#737aa2"), comment = c("#565f89"),
            blue = c("#7aa2f7"), blue0 = c("#3d59a1"), blue1 = c("#2ac3de"), blue2 = c("#0db9d7"),
            magenta = c("#bb9af7"), orange = c("#ff9e64"), green = c("#9ece6a"), yellow = c("#e0af68"), red1 = c("#db4b4b"),
        )
        TokyoNightStyle.STORM -> TokyoNight(
            bg = c("#24283b"), bgDark = c("#1f2335"), bgHighlight = c("#292e42"), terminalBlack = c("#414868"),
            fg = c("#c0caf5"), dark5 = c("#737aa2"), comment = c("#565f89"),
            blue = c("#7aa2f7"), blue0 = c("#3d59a1"), blue1 = c("#2ac3de"), blue2 = c("#0db9d7"),
            magenta = c("#bb9af7"), orange = c("#ff9e64"), green = c("#9ece6a"), yellow = c("#e0af68"), red1 = c("#db4b4b"),
        )
        TokyoNightStyle.MOON -> TokyoNight(
            bg = c("#222436"), bgDark = c("#1e2030"), bgHighlight = c("#2f334d"), terminalBlack = c("#444a73"),
            fg = c("#c8d3f5"), dark5 = c("#737aa2"), comment = c("#636da6"),
            blue = c("#82aaff"), blue0 = c("#3e68d7"), blue1 = c("#65bcff"), blue2 = c("#0db9d7"),
            magenta = c("#c099ff"), orange = c("#ff966c"), green = c("#c3e88d"), yellow = c("#ffc777"), red1 = c("#c53b53"),
        )
        // `day` inverts `night` at load time; these are the generated values.
        TokyoNightStyle.DAY -> TokyoNight(
            bg = c("#e1e2e7"), bgDark = c("#d0d5e3"), bgHighlight = c("#c4c8da"), terminalBlack = c("#a1a6c5"),
            fg = c("#3760bf"), dark5 = c("#68709a"), comment = c("#848cb5"),
            blue = c("#2e7de9"), blue0 = c("#7890dd"), blue1 = c("#188092"), blue2 = c("#07879d"),
            magenta = c("#9854f1"), orange = c("#b15c00"), green = c("#587539"), yellow = c("#8c6c3e"), red1 = c("#c64343"),
            borderHighlight = c("#4094a3"), bgVisual = c("#b7c1e3"),
            // Day's `terminal_black` is a mid grey the blue body text barely reads on (2.4:1);
            // the raised step stops short of it, 40% of the way from `bg_highlight`.
            raised = c("#c4c8da").blend(c("#a1a6c5"), 0.4),
        )
    }

    /**
     * After `colors/init.lua` and the lazygit extra: `bg_dark` for popups and status lines (the
     * sunken step), `bg_highlight` one step up, `terminal_black` two, `dark5` / `comment` for
     * muted / disabled text, `border_highlight` / `orange` for the inactive / active border,
     * `bg_visual` for the selection, `blue2` / `green` / `yellow` / `red1` for the status, with
     * `bg_dark` or `fg` as the text on each, whichever reads (the fg only reads on `red1`).
     */
    fun tokyoNight(style: TokyoNightStyle): ComponentPalette {
        val p = tokyoNightColors(style)
        fun on(color: Color) = readableOn(color, p.bgDark, p.fg)
        return componentPalette {
            name = "Tokyo Night ${style.name.lowercase().replaceFirstChar { it.uppercase() }}"
            isDark = style.isDark
            background = p.bg
            onBackground = p.fg
            surface = p.bgHighlight
            onSurface = p.fg
            surfaceRaised = p.raised
            onSurfaceRaised = p.fg
            surfaceSunken = p.bgDark
            textMuted = p.dark5
            textDisabled = p.comment
            border = p.borderHighlight
            borderFocused = p.orange
            primary = p.blue
            onPrimary = on(p.blue)
            secondary = p.magenta
            onSecondary = on(p.magenta)
            accent = p.orange
            onAccent = on(p.orange)
            selection = p.bgVisual
            onSelection = p.fg
            info = p.blue2
            onInfo = on(p.blue2)
            success = p.green
            onSuccess = on(p.green)
            warning = p.yellow
            onWarning = on(p.yellow)
            error = p.red1
            onError = on(p.red1)
        }
    }

    // ---- Kanagawa: each variant's `ui` and `diag` block over the shared palette.

    private class Kanagawa(
        val bg: Color, val bgP1: Color, val bgP2: Color, val bgM1: Color,
        val fg: Color, val fgDim: Color, val disabled: Color, val floatBorder: Color,
        val fun_: Color, val keyword: Color, val highlight: Color, val bgVisual: Color,
        val info: Color, val ok: Color, val warning: Color, val error: Color,
    )

    private fun kanagawaColors(variant: KanagawaVariant): Kanagawa = when (variant) {
        KanagawaVariant.WAVE -> Kanagawa(
            bg = c("#1F1F28"), bgP1 = c("#2A2A37"), bgP2 = c("#363646"), bgM1 = c("#1a1a22"),          // sumiInk3/4/5/2
            fg = c("#DCD7BA"), fgDim = c("#C8C093"), disabled = c("#727169"), floatBorder = c("#54546D"), // fujiWhite, oldWhite, fujiGray, sumiInk6
            fun_ = c("#7E9CD8"), keyword = c("#957FB8"), highlight = c("#E6C384"), bgVisual = c("#2D4F67"), // crystalBlue, oniViolet, carpYellow, waveBlue2
            info = c("#658594"), ok = c("#98BB6C"), warning = c("#FF9E3B"), error = c("#E82424"),       // dragonBlue, springGreen, roninYellow, samuraiRed
        )
        KanagawaVariant.DRAGON -> Kanagawa(
            bg = c("#181616"), bgP1 = c("#282727"), bgP2 = c("#393836"), bgM1 = c("#1D1C19"),          // dragonBlack3/4/5/2
            fg = c("#c5c9c5"), fgDim = c("#C8C093"), disabled = c("#7a8382"), floatBorder = c("#54546D"), // dragonWhite, oldWhite, dragonGray3, sumiInk6
            fun_ = c("#8ba4b0"), keyword = c("#8992a7"), highlight = c("#c4b28a"), bgVisual = c("#2D4F67"), // dragonBlue2, dragonViolet, dragonYellow, waveBlue2
            info = c("#658594"), ok = c("#98BB6C"), warning = c("#FF9E3B"), error = c("#E82424"),
        )
        KanagawaVariant.LOTUS -> Kanagawa(
            bg = c("#f2ecbc"), bgP1 = c("#e7dba0"), bgP2 = c("#e4d794"), bgM1 = c("#e5ddb0"),          // lotusWhite3/4/5/2
            fg = c("#545464"), fgDim = c("#43436c"), disabled = c("#8a8980"), floatBorder = c("#716e61"), // lotusInk1, lotusInk2, lotusGray3, lotusGray2
            fun_ = c("#4d699b"), keyword = c("#624c83"), highlight = c("#de9800"), bgVisual = c("#c9cbd1"), // lotusBlue4, lotusViolet4, lotusYellow3, lotusViolet3
            info = c("#5a7785"), ok = c("#6f894e"), warning = c("#e98a00"), error = c("#e82424"),       // lotusTeal3, lotusGreen, lotusOrange2, lotusRed3
        )
    }

    /**
     * After `themes.lua`: `ui.bg` / `bg_p1` / `bg_p2` / `bg_m1` are the surface ladder,
     * `ui.fg` / `fg_dim` the text, `float.fg_border` the border, `syn.fun` the primary and the
     * focused border, `syn.keyword` the secondary, the yellow highlight the accent,
     * `ui.bg_visual` the selection (the dark variants take `ui.bg_search`, waveBlue2, instead:
     * waveBlue1 is 1.1:1 against `bg_p1`, where a selected tab sits), `diag.*` the status; the
     * text on an accent is `ui.bg` or `ui.fg`, whichever reads (Lotus's orange warning wants
     * the ink).
     */
    fun kanagawa(variant: KanagawaVariant): ComponentPalette {
        val p = kanagawaColors(variant)
        fun on(color: Color) = readableOn(color, p.bg, p.fg)
        return componentPalette {
            name = "Kanagawa ${variant.name.lowercase().replaceFirstChar { it.uppercase() }}"
            isDark = variant.isDark
            background = p.bg
            onBackground = p.fg
            surface = p.bgP1
            onSurface = p.fg
            surfaceRaised = p.bgP2
            onSurfaceRaised = p.fg
            surfaceSunken = p.bgM1
            textMuted = p.fgDim
            textDisabled = p.disabled
            border = p.floatBorder
            borderFocused = p.fun_
            primary = p.fun_
            onPrimary = on(p.fun_)
            secondary = p.keyword
            onSecondary = on(p.keyword)
            accent = p.highlight
            onAccent = on(p.highlight)
            selection = p.bgVisual
            onSelection = p.fg
            info = p.info
            onInfo = on(p.info)
            success = p.ok
            onSuccess = on(p.ok)
            warning = p.warning
            onWarning = on(p.warning)
            error = p.error
            onError = on(p.error)
        }
    }

    // ---- Solarized: the sixteen colors; a mode picks which of the base tones are background and text.

    private val solBase03 = c("#002b36")
    private val solBase02 = c("#073642")
    private val solBase01 = c("#586e75")
    private val solBase00 = c("#657b83")
    private val solBase0 = c("#839496")
    private val solBase1 = c("#93a1a1")
    private val solBase2 = c("#eee8d5")
    private val solBase3 = c("#fdf6e3")
    private val solYellow = c("#b58900")
    private val solOrange = c("#cb4b16")
    private val solRed = c("#dc322f")
    private val solMagenta = c("#d33682")
    private val solViolet = c("#6c71c4")
    private val solBlue = c("#268bd2")
    private val solCyan = c("#2aa198")
    private val solGreen = c("#859900")

    /**
     * After the usage table: in dark mode base03 is the background, base02 the background
     * highlights, base01 comments / secondary content, base0 body text and base1 emphasized
     * content; light mode walks the ladder from the other end (base3, base2, base1, base00,
     * base01). Solarized has two background tones per mode, so the raised and sunken steps are
     * derived. Blue is the primary, violet the secondary, orange the accent, the four status
     * colors cyan / green / yellow / red; the accents are shared by both modes, so the text on
     * each is the end of the ladder that contrasts more with it (base03 on the mid-luminance
     * blue, cyan, green and yellow, base3 on the darker violet, orange and red), whatever the
     * mode. The selection is vim's `Visual` (the comment tone) with the far end of the ladder
     * as its text.
     */
    fun solarized(dark: Boolean): ComponentPalette = componentPalette {
        name = if (dark) "Solarized Dark" else "Solarized Light"
        isDark = dark
        val bg = if (dark) solBase03 else solBase3
        val highlight = if (dark) solBase02 else solBase2
        val comment = if (dark) solBase01 else solBase1
        val body = if (dark) solBase0 else solBase00
        val emphasized = if (dark) solBase1 else solBase01
        val farEnd = if (dark) solBase3 else solBase03
        fun on(color: Color) = readableOn(color, solBase03, solBase3)
        background = bg
        onBackground = body
        surface = highlight
        onSurface = body
        onSurfaceRaised = emphasized
        textMuted = comment
        border = comment
        borderFocused = solBlue
        primary = solBlue
        onPrimary = on(solBlue)
        secondary = solViolet
        onSecondary = on(solViolet)
        accent = solOrange
        onAccent = on(solOrange)
        selection = comment
        onSelection = farEnd
        info = solCyan
        onInfo = on(solCyan)
        success = solGreen
        onSuccess = on(solGreen)
        warning = solYellow
        onWarning = on(solYellow)
        error = solRed
        onError = on(solRed)
    }

    // ---- Gruvbox (medium contrast): the dark and light tables of `gruvbox.vim`.

    private class Gruvbox(
        val bg0: Color, val bg0Hard: Color, val bg1: Color, val bg2: Color, val bg3: Color,
        val fg0: Color, val fg1: Color, val fg4: Color, val gray: Color,
        val red: Color, val green: Color, val yellow: Color, val blue: Color, val purple: Color, val aqua: Color, val orange: Color,
    )

    private fun gruvboxColors(dark: Boolean): Gruvbox = if (dark) Gruvbox(
        bg0 = c("#282828"), bg0Hard = c("#1d2021"), bg1 = c("#3c3836"), bg2 = c("#504945"), bg3 = c("#665c54"),
        fg0 = c("#fbf1c7"), fg1 = c("#ebdbb2"), fg4 = c("#a89984"), gray = c("#928374"),
        // The bright accents, what the dark theme paints syntax with.
        red = c("#fb4934"), green = c("#b8bb26"), yellow = c("#fabd2f"), blue = c("#83a598"),
        purple = c("#d3869b"), aqua = c("#8ec07c"), orange = c("#fe8019"),
    ) else Gruvbox(
        bg0 = c("#fbf1c7"), bg0Hard = c("#f9f5d7"), bg1 = c("#ebdbb2"), bg2 = c("#d5c4a1"), bg3 = c("#bdae93"),
        fg0 = c("#282828"), fg1 = c("#3c3836"), fg4 = c("#7c6f64"), gray = c("#928374"),
        // The faded accents, what the light theme paints syntax with.
        red = c("#9d0006"), green = c("#79740e"), yellow = c("#b57614"), blue = c("#076678"),
        purple = c("#8f3f71"), aqua = c("#427b58"), orange = c("#af3a03"),
    )

    /**
     * After `gruvbox.vim`: `Normal` is fg1 on bg0, `CursorLine` / `Pmenu` step up through bg1
     * and bg2, bg0_h is the sunken step, `Comment` is gray, fg4 the dimmed text, `VertSplit`
     * bg3 the border, `Visual` bg3 the selection; blue (`PmenuSel`) is the primary, purple the
     * secondary, orange the accent and the focused border, aqua / green / yellow / red the status
     * with bg0 or fg0 on them, whichever reads.
     */
    fun gruvbox(dark: Boolean): ComponentPalette {
        val p = gruvboxColors(dark)
        fun on(color: Color) = readableOn(color, p.bg0, p.fg0)
        return componentPalette {
            name = if (dark) "Gruvbox Dark" else "Gruvbox Light"
            isDark = dark
            background = p.bg0
            onBackground = p.fg1
            surface = p.bg1
            onSurface = p.fg1
            surfaceRaised = p.bg2
            onSurfaceRaised = p.fg1
            surfaceSunken = p.bg0Hard
            textMuted = p.fg4
            textDisabled = p.gray
            border = p.bg3
            borderFocused = p.orange
            primary = p.blue
            onPrimary = on(p.blue)
            secondary = p.purple
            onSecondary = on(p.purple)
            accent = p.orange
            onAccent = on(p.orange)
            selection = p.bg3
            onSelection = p.fg0
            info = p.aqua
            onInfo = on(p.aqua)
            success = p.green
            onSuccess = on(p.green)
            warning = p.yellow
            onWarning = on(p.yellow)
            error = p.red
            onError = on(p.red)
        }
    }
}
