# Semantic component palettes: how other systems split shape from color

Research for the `ComponentPalette` design (a component theme supplies renderers and decorations, a separate palette
supplies colors looked up by semantic key). Date: 2026-11-14. Every factual claim cites a primary source inline; the
"Implications for Zircon" section at the end is synthesis, not sourced fact. Local clones used for line references are
the `master` heads of 2026-11-14: mc `e51e8d79b0e0`, lazygit `c63f9b024ca1`.

Zircon context: `docs/component-themes.md` defines `ComponentTheme = role -> ThemeEntry{renderer, decorations,
postProcessors, styleSet}`, `ComponentStyleSet = colors per ComponentState (DEFAULT / HIGHLIGHTED / ACTIVE / FOCUSED /
DISABLED)`, and the legacy `ColorTheme` with five fixed colors (`primaryForeground`, `secondaryForeground`,
`primaryBackground`, `secondaryBackground`, `accent`).

## TL;DR

- Every system with more than one look has three layers: a **named palette** (Catppuccin `mauve`, Kanagawa
  `sumiInk3`), a **semantic layer** of role names (`primary`, `surface`, `error`) assigned from the palette, and
  **consumers** that reference roles only. Kanagawa makes the palette -> theme step an explicit typed data structure;
  Textual derives most of it; DaisyUI / shadcn make the author fill it in by hand.
- The semantic vocabulary converges: a background/surface ladder of 3-6 steps, body / muted / disabled text, 1-3
  brand roles, four status roles (`info / success / warning / error`), a selection color, and border at rest vs
  focused. Error = red and warning = yellow in every source; "info" diverges (teal / blue / cyan).
- Where a background is painted, the mature systems pair it with an explicit "on" color (DaisyUI `X-content`,
  shadcn `X-foreground`, Catppuccin "On Accent -> Base"); none of them computes contrast at runtime (DaisyUI v5 dropped
  culori; Textual's `auto` is the exception and needs alpha blending).
- State variants are either **all explicit keys** (mc's `dfocus`/`dhotfocus`, lazygit's `activeBorderColor`) or
  **computed from the role color** (Textual `-darken-2`, DaisyUI `color-mix(... #000 7%)`, shadcn `/90` alpha). Nobody
  ships a per-state palette; the computed systems let the theme own the rule and the palette override a few slots.
- Shape and color are separate in the two systems closest to Zircon: lazygit `gui.border` (rounded/single/double/
  hidden/bold runes) vs `gui.theme`; Textual border styles in widget CSS vs `$border` color. DaisyUI puts radius /
  border / depth / noise *inside* the theme, and mc puts glyphs *and* colors in one skin, which is why it needs N x M
  skin copies.
- Light/dark is a property of the palette (Catppuccin `dark: bool`, Kanagawa wave/lotus, Textual `Theme.dark`,
  base16 `variant`), with at most one or two dark-specific derivation rules.
- "Following the terminal" is done with ANSI *indices* and `default` (mc, lazygit defaults, Textual `ansi-*`, ratatui
  `Reset`), or by querying `OSC 11 ; ?` for the background (lazygit, termenv, lipgloss, Bubble Tea) and judging
  lightness `< 0.5`. mc never queries; nobody queries `OSC 4` for the 16 palette slots.
- Zircon today: `Color` is opaque RGB with no blend/lighten, the terminal encoder emits only truecolor SGR, and no
  OSC query exists, so a computed-state design needs a blend primitive and a terminal-following palette needs either
  indexed colors in the encoder or an OSC 10/11 handshake.
- Recommendation (synthesis): ~28 keys in the shadcn/DaisyUI "surface + on-surface" style, palette carries light/dark,
  theme derives `ComponentStyleSet` per state from the palette with a sparse override map (Textual's `variables`
  shape); concrete Catppuccin Mocha and Tokyo Night mappings are in section 10.

## 1. Midnight Commander skins

mc is the oldest system here and the only one where one file carries *both* the glyphs and the colors. The user's own
setup is a good example of the "follow the terminal" strategy: `~/.config/mc/ini` line 86 has `skin=terminal`, and
`~/.local/share/mc/skins/terminal.ini` is a hand-written skin whose header reads "follows the terminal's palette
(default fg/bg + 16 ANSI colors). Works with light and dark themes, so mc changes along with the Omarchy theme."

### File structure

A skin is an INI file. Sections seen in `/usr/share/mc/skins/default.ini`, `dark.ini` and `modarin256-defbg.ini`:

| Section | Purpose (from `man mc`, "Skins" and "Description of section and parameters") |
|---|---|
| `[skin]` | metainfo: `description`, `256colors = true`, `truecolors = true` |
| `[Lines]` | box-drawing glyphs (see below); lowercase `[lines]` since 4.8.34, uppercase accepted for compatibility (`lib/skin/lines.c:94-98`) |
| `[aliases]` | optional names for single colors / attribute combos, resolvable recursively (`lib/skin/colors.c`, `mc_skin_color_look_up_alias`) |
| `[core]` | "elements that are used everywhere" |
| `[dialog]`, `[error]` | dialog windows and error dialogs |
| `[menu]`, `[popupmenu]`, `[buttonbar]`, `[statusbar]`, `[help]`, `[editor]`, `[viewer]`, `[diffviewer]` | per-widget-area sections |
| `[filehighlight]` | one color pair per file-type group named in `filehighlight.ini` |
| `[widget-panel]`, `[widget-scrollbar]`, `[widget-editor]` | *glyph* settings: `sort-up-char`, `hiddenfiles-show-char`, `first-vert-char`, `current-char`, `background-char`, `window-close-char` ... |

### Semantic keys

Exact key names from the installed skins (`/usr/share/mc/skins/default.ini`) and their meaning from `man mc`:

- `[core]`: `_default_` ("Default color pair. Used in all other sections if they not contain color definitions"),
  `selected` ("cursor"), `marked` ("selected data"), `markselect` ("cursor on selected data"), `gauge` (filled part of
  the progress bar), `input`, `inputunchanged`, `inputmark`, `disabled` ("widget that cannot be selected"), `reverse`,
  `commandlinemark`, `header` (panel header line), `inputhistory`, `commandhistory`, `shadow`.
- `[dialog]`: `_default_`, `dfocus` ("Color of active element (in focus)"), `dhotnormal` ("Color of hotkeys"),
  `dhotfocus` ("Color of hotkeys in focused element"), `dtitle`. The hardcoded skin also knows `dselnormal` /
  `dselfocus` (`lib/skin/hc-skins.c:50-91`).
- `[error]`: `_default_`, `errdfocus`, `errdhotnormal`, `errdhotfocus`, `errdtitle`.
- `[menu]`: `_default_`, `menusel`, `menuhot`, `menuhotsel`, `menuinactive`. `[popupmenu]`: `_default_`, `menusel`,
  `menutitle`.
- `[buttonbar]`: `hotkey`, `button`. `[statusbar]`: `_default_`.
- `[help]`: `helpitalic`, `helpbold`, `helplink`, `helpslink`, `helptitle`.
- `[editor]`: `editbold`, `editmarked`, `editwhitespace`, `editnonprintable`, `editlinestate`, `bookmark`,
  `bookmarkfound`, `editrightmargin`, `editbg`, `editframe`, `editframeactive`, `editframedrag`.
- `[viewer]`: `viewbold`, `viewunderline`, `viewselected`. `[diffviewer]`: `added`, `changedline`, `changednew`,
  `changed`, `removed`, `error`.

Observations on the key set: the names are **per-widget-and-state** ("dialog focused", "menu hotkey selected"), not
per-role ("primary", "warning"). There are a few cross-cutting state words that recur in every section: *normal*
(`_default_`), *focus/sel* (`dfocus`, `menusel`, `errdfocus`, `helpslink`), *hot* (hotkey highlight: `dhotnormal`,
`menuhot`), *hot+focus* (`dhotfocus`, `menuhotsel`), *inactive/disabled* (`menuinactive`, `disabled`), *marked*
(`marked`, `editmarked`, `viewselected`). That is mc's implicit state model: normal, focused, hotkey, hotkey-in-focused,
disabled, marked.

### Value syntax and fallback

- Values are `fg;bg;attr`; any field may be empty, in which case it is taken from the section's `_default_`, then
  `[core]._default_` (`man mc`, "Color pair definitions"; `lib/skin/colors.c:210-224`,
  `mc_skin_color_get_with_defaults` tries `group.name`, `group._default_`, `core._default_`). Example from
  `modarin256-defbg.ini`: `marked = color228;;bold` (background inherited).
- Attributes: `bold`, `italic`, `underline`, `reverse`, `blink`, joined with `+`; `none` means no attributes
  (`man mc`, "Colors"; `lib/tty/color-internal.c`, `attributes_table`).
- Color names: `black, gray, red, brightred, green, brightgreen, brown, yellow, blue, brightblue, magenta,
  brightmagenta, cyan, brightcyan, lightgray, white` map to curses `COLOR_*` (+ `COLOR_INTENSITY` for the bright
  eight), and `default` maps to `-1` with the comment "default color of the terminal" (`lib/tty/color-internal.c:60-76`).
  256-color skins use `color16`..`color255`, `rgb000`..`rgb555` (`16 + 36r + 6g + b`) and `gray0`..`gray23` (`232 + i`);
  truecolor skins use `#rrggbb` or `#rgb` (nibble doubled), stored with `FLAG_TRUECOLOR`
  (`lib/tty/color-internal.c`, `parse_256_or_true_color_name`). The man page says `default` "can only be used for
  background color", but the user's `terminal.ini` and the hardcoded skin (`core._default_ = "default;default"`,
  `lib/skin/hc-skins.c:50`) use it for the foreground too, and the ncurses backend allocates pair `(-1, -1)` as the
  default pair after `use_default_colors()` (`lib/tty/color-ncurses.c:141,153`).
- The skin declares its tier in `[skin]` (`256colors`, `truecolors`; `lib/skin/ini-file.c:192-193`). `mc_skin_init`
  refuses a skin whose tier the terminal lacks and falls back to `default`, then to the hardcoded skin
  (`lib/skin/common.c:97-134`, messages "Unable to use '%s' skin with true colors support" / "256 colors support").
  Truecolor needs S-Lang >= 2.3.1 and `COLORTERM=truecolor|24bit` or the terminfo RGB capability
  (`lib/tty/color-slang.c:219-228`; `seasons-winter16M.ini` header). This machine's mc is 4.8.33 built with S-Lang
  2.3.3 (`mc --version`).
- No color is ever *derived* in mc: every pair is spelled out. The only computation is `convert_256color_to_truecolor`
  when a truecolor terminal mixes 256-color and RGB pairs, and it explicitly skips the basic 16 "as they are
  terminal-dependent and user-configurable" (`lib/tty/color-internal.c:241-270`, `lib/tty/color.c:169-176`).

### Why mc "follows" the terminal theme

The 16 named colors and `default` are *indices*, not RGB values: mc emits SGR 30-37/90-97 (via S-Lang/ncurses) and the
terminal emulator looks up the index in its own palette. Repaint the terminal palette (as an Omarchy theme switch does)
and every mc cell changes color without mc knowing. The `default` index is the emulator's configured fg/bg, so a skin
built from `default;default` plus the 16 names, like the user's `terminal.ini`, is recolored completely. Skins that pin
`color16`..`color255` or `#rrggbb` (`modarin256`, `seasons-*16M`) do not move; `modarin256.ini`'s own header states the
goal: "using colors independent from the default 16 terminal colors, i.e., no matter how the 16 standard colors are
configured, the appearance of Midnight Commander will not be affected." The `-defbg` variants are the hybrid: the
header says they use "the default background color for core, editor, viewer, menu and popupmenu backgrounds, for use
with (semi-)transparent background colors" (`modarin256-defbg.ini`: `[core] _default_ = color250;default`).

mc never queries the terminal for its colors. A grep of the clone's `lib/` and `src/` for `\033]10`, `\033]11`,
`COLORFGBG` or `OSC` finds only the OSC 9;4 progress report (`lib/widget/gauge.c:63`) and a generic OSC skipper in
`lib/terminal.c:204`; there is no light/dark detection and no OSC 10/11 query.

### Shape vs color coupling

`[Lines]` lets a skin pick every frame glyph: `horiz, vert, lefttop, righttop, leftbottom, rightbottom, topmiddle,
bottommiddle, leftmiddle, rightmiddle, cross` and the double-line set `dhoriz, dvert, dlefttop ... drightmiddle`
(`lib/skin/lines.c`; defaults U+2500 etc., ACS fallback when a glyph is invalid). `default.ini` draws "double" lines
with the same single glyphs; `modarin256-defbg.ini` uses `═ ║ ╔ ╗ ╚ ╝ ╤ ╧ ╟ ╢`; the user's `terminal.ini` uses rounded
corners `╭ ╮ ╰ ╯`. `[widget-*]` sections add arrows, bullets and scrollbar characters. So **a skin is shape + color in
one file**; there is no mechanism to combine the lines of one skin with the colors of another (the `-thin` / `-defbg`
variants are copies). The hardcoded fallback shows the two halves are at least separate functions:
`mc_skin_hardcoded_ugly_lines` (ASCII `- | +`), `mc_skin_hardcoded_space_lines`, and
`mc_skin_hardcoded_blackwhite_colors` (attribute-only colors like `selected = ";;reverse"`, `marked = ";;bold"`)
(`lib/skin/hc-skins.c:50-150`).

### Light/dark

None built in. `default.ini` is blue-background, `dark.ini` black-background; which one suits the terminal is the
user's choice. A skin that uses only `default` and the 16 names is light/dark-agnostic by construction.

Sources: `/usr/share/mc/skins/default.ini`, `dark.ini`, `modarin256-defbg.ini`, `seasons-winter16M.ini`;
`~/.local/share/mc/skins/terminal.ini`; `~/.config/mc/ini:86`; `man mc` sections "Colors", "Skins", "Draw lines";
https://github.com/MidnightCommander/mc `lib/skin/colors.c`, `lib/skin/ini-file.c`, `lib/skin/common.c`,
`lib/skin/lines.c`, `lib/skin/hc-skins.c`, `lib/tty/color-internal.c`, `lib/tty/color.c`, `lib/tty/color-slang.c`,
`lib/tty/color-ncurses.c`.

## 2. lazygit

lazygit is the cleanest terminal example of the split Zircon wants: `gui.border` picks the frame *shape*, `gui.theme`
picks the *colors*, and the two are independent config keys.

### Semantic keys (`gui.theme`)

From the default config embedded in `docs/Config.md` (lines 177-237 of the 2026-11-14 master):

| Key | Default | Comment in the default config |
|---|---|---|
| `activeBorderColor` | `[green, bold]` | Border color of focused window |
| `inactiveBorderColor` | `[default]` | Border color of non-focused windows |
| `searchingActiveBorderColor` | `[cyan, bold]` | Border color of focused window when searching in that window |
| `optionsTextColor` | `[blue]` | Color of keybindings help text in the bottom line |
| `selectedLineFgColor` | `[bold]` | "The attributes are added to those of the text, and a color replaces the colors of the text. Set it to 'default' to leave the text as it is" |
| `selectedLineBgColor` | `[]` | "Default: 'blue' if the terminal has a dark background, or a suitable RGB blue computed from the background color if it is light." |
| `inactiveViewSelectedLineBgColor` | `[]` | "Default: a suitable RGB grey computed from the terminal's background color." |
| `cherryPickedCommitFgColor` / `BgColor` | `[blue]` / `[cyan]` | copied commit |
| `markedBaseCommitFgColor` / `BgColor` | `[blue]` / `[yellow]` | marked base commit (for rebase) |
| `unstagedChangesColor` | `[red]` | file with unstaged changes |
| `defaultFgColor` | `[default]` | Default text color |
| `authorColors` | `{}` | map author name -> color, `'*'` wildcard |
| `branchColorPatterns` | `{}` | map regex -> color |

The key set is **per-situation** (border focused / unfocused / searching, selected line active / inactive), with explicit
fg/bg pairs where a background is painted (`cherryPickedCommit*`, `markedBaseCommit*`). There is no generic
"primary/secondary" vocabulary.

### Value format

A value is an array "with max one color attribute" (`docs/Config.md`, "Color Attributes", line 1027). Colors: `black,
red, green, yellow, blue, magenta, cyan, white` or a hex `'#ff00ff'`; modifiers: `bold, default, dim, reverse,
underline, strikethrough`. `pkg/theme/style.go:9-46` (`GetTextStyle(keys []string, background bool)`) switches on the
word: the five attribute words set flags, anything else is looked up in `style.ColorMap` and otherwise parsed as hex
via `utils.IsValidHexValue` / `color.HEX`. `ColorMap` (`pkg/gui/style/basic_styles.go:39-52`) has nine entries, the
eight basic colors plus `"default": {FgDefault, BgDefault}`, where `FgDefault = FromBasicFg(color.FgDefault)` wraps
gookit/color's SGR 39/49 "default" codes, i.e. the terminal's own fg/bg. So `default` is the terminal default, and the
bright ANSI colors (90-97) are **not** addressable by name; a theme author who wants them must use hex.

### State variants: fixed, with two computed defaults

Every state is an explicit key. The two exceptions are computed from the detected terminal background in
`pkg/config/theme_config.go:31-54` (`themeDefaults`):

```go
if lightBackground {
    SelectedLineBgColor:             mixHexColors(backgroundColor, "#0064ff", 0.25),
    InactiveViewSelectedLineBgColor: mixHexColors(backgroundColor, "#000000", 0.15),
} else {
    SelectedLineBgColor:             "blue",   // "doesn't turn into black on terminals with only 8 colors"
    InactiveViewSelectedLineBgColor: mixHexColors(backgroundColor, "#ffffff", 0.3),
}
```

with the comment "Most of them are derived from the background color, so that they keep the same distance from it
however dark or light it is. If we don't know the background color, we assume black or white." `mixHexColors` is a
linear RGB mix (`theme_config.go:58`).

### Light/dark handling

- `gui.colorScheme: auto | dark | light` (default `auto`): "With 'auto', lazygit asks the terminal, and assumes a dark
  background if the terminal doesn't tell" (`docs/Config.md:167-173`).
- `gui.darkTheme` / `gui.lightTheme` have the same fields as `gui.theme` and override it per background
  (`docs/Config.md:244-251, 1082-1100`; `ThemeForBackground` merges `override, c.Theme, themeDefaults(...)`,
  `theme_config.go:19-22`).
- Detection lives in `pkg/gocui/color_scheme.go`. It sends three things when the tty starts: `CSI ? 2031 h` to enable
  color-scheme change reports, `CSI ? 996 n` to request a report (reply `CSI ? 997 ; 1 n` dark / `; 2 n` light), and
  `OSC 11 ; ? BEL` to request the background color (lines 55-64). The OSC 11 reply `rgb:RRRR/GGGG/BBBB` (1-4 hex digits
  per channel, `rgba:` also accepted) is parsed at lines 461-497; the background wins over the terminal's own dark/light
  report because "What they base this on varies ... some go by the terminal's own colors, others by the dark or light
  mode of the operating system" (comment, lines 66-80). Light/dark is decided by CIE L* of the background, threshold 0.5
  (`colorSchemeOfBackground`, line 313). Replies are watched in the input stream because "tcell doesn't understand the
  answers and drops them". A change handler re-applies the theme live (`pkg/gui/gui.go:968`).

### Shape vs color coupling

`gui.border: rounded | single | double | hidden | bold` (default `rounded`, `docs/Config.md:348-350`) is mapped to
gocui `FrameRunes` in `pkg/gui/views.go:209-226`: single `─ │ ┌ ┐ └ ┘`, double `═ ║ ╔ ╗ ╚ ╝`, rounded `─ │ ╭ ╮ ╰ ╯`,
hidden (spaces), bold `━ ┃ ┏ ┓ ┗ ┛`, plus tee corners `├ ┤` / `╠ ╣` / `┣ ┫` for stacked views. Colors
(`FgColor`, `SelBgColor`, ...) are set in the same loop from `theme.*`, independent of the runes. `gui.nerdFontsVersion`
(`"2" | "3" | ""`) switches icon glyphs on or off (`docs/Config.md`, `user_config.go:180`). So shape = `border` +
`nerdFontsVersion`, color = `theme`, orthogonal.

### How a palette targets it: Catppuccin and Tokyo Night

`catppuccin/lazygit` ships `themes/<flavor>/<accent>.yml` and `themes-mergable/<flavor>/<accent>.yml`, loaded with
`LG_CONFIG_FILE="<config-dir>/config.yml,<theme>.yml" lazygit` (README). `themes-mergable/mocha/blue.yml` verbatim:

```yaml
gui:
  theme:
    activeBorderColor: ['#89b4fa', bold]      # blue (accent)
    inactiveBorderColor: ['#a6adc8']          # subtext0
    searchingActiveBorderColor: ['#f9e2af']   # yellow
    optionsTextColor: ['#89b4fa']             # blue
    selectedLineBgColor: ['#313244']          # surface0
    inactiveViewSelectedLineBgColor: ['#6c7086']  # overlay0
    cherryPickedCommitFgColor: ['#89b4fa']    # blue
    cherryPickedCommitBgColor: ['#45475a']    # surface1
    markedBaseCommitFgColor: ['#89b4fa']      # blue
    markedBaseCommitBgColor: ['#f9e2af']      # yellow
    unstagedChangesColor: ['#f38ba8']         # red
    defaultFgColor: ['#cdd6f4']               # text
    authorColors:
      '*': '#b4befe'                          # lavender
```

(The role names in comments are mine, matched against the Catppuccin palette in section 7.) The README notes "lazygit
uses your terminal's background color, so you should set the background yourself to match the chosen flavor", i.e. the
palette file only covers foregrounds and highlight backgrounds; the base background is the terminal's `default`. Tokyo
Night's generator does the same from Lua (section 7).

Sources: https://github.com/jesseduffield/lazygit/blob/master/docs/Config.md; lazygit `pkg/theme/style.go`,
`pkg/theme/theme.go`, `pkg/gui/style/basic_styles.go`, `pkg/config/theme_config.go`, `pkg/config/user_config.go`,
`pkg/gui/views.go`, `pkg/gui/gui.go`, `pkg/gocui/color_scheme.go`; https://github.com/catppuccin/lazygit (README,
`themes-mergable/mocha/blue.yml`).

## 5. Textual (Python TUI)

Textual is the closest analogue to what Zircon wants: a `Theme` is ~11 seed colors plus a `variables` override map, a
`ColorSystem` derives ~150 CSS variables from the seeds, and every widget's `DEFAULT_CSS` references variables only.
(Note: the API pages `/api/design/` and `/api/theme/` return 404 and are absent from `mkdocs-nav.yml`; everything below
comes from the design guide and the source on `main`, 2026-11-14.)

### Seed colors (`Theme` dataclass, `src/textual/theme.py:16-40`)

```python
name: str
primary: str                      # required
secondary, warning, error, success, accent: str | None
foreground, background, surface, panel, boost: str | None
dark: bool = True
luminosity_spread: float = 0.15
text_alpha: float = 0.95          # stored but never read in _generate()
variables: dict[str, str] = {}
ansi: bool = False
```

Fallbacks when a seed is missing (`src/textual/design.py:256-275`): `secondary = primary`, `warning = primary`,
`error = secondary`, `success = secondary`, `accent = primary`; `background = #121212` (dark) / `#efefef` (light);
`surface = #1e1e1e` / `#f5f5f5` (fixed defaults, not derived from background); `foreground = background.inverse`;
`panel = surface.blend(primary, 0.1)` plus, in dark mode, `boost = contrast_text.with_alpha(0.04)` composited on top
(lines 277-305).

### Derived variables (`ColorSystem._generate`, `design.py:248-530`)

- **Shades.** For each of `primary, secondary, primary-background, secondary-background, background, foreground, panel,
  boost, surface, warning, error, success, accent` (13 names, lines 324-338) seven variables are produced:
  `$X-darken-3..1`, `$X`, `$X-lighten-1..3`. Step = `luminosity_spread / 2 = 0.075`, so `-lighten-1/2/3` are
  `+0.075 / +0.15 / +0.225` and `-darken-*` the negatives (lines 307-360). `Color.lighten` works in CIE Lab:
  `l -= amount * 100` (`color.py`, `darken`), so one shade is 7.5 L* units. The direction does **not** flip between
  light and dark themes; the only dark-mode special case is `primary-background` / `secondary-background`, which become
  `background.blend(color, 0.15)` then blended toward white by `0.15 + delta` (lines 341-358).
- **Text.** `$text = "auto 87%"`, `$text-muted = "auto 60%"`, `$text-disabled = "auto 38%"` (lines 362-369). `auto`
  means "automatically select either white or black text for best contrast against the background"
  (https://textual.textualize.io/styles/color/); implemented as `Color.get_contrast_text`: `WHITE if brightness < 0.5
  else BLACK`, with `brightness = (299r + 587g + 114b)/1000` (`color.py`). The trailing percentage is alpha, and alpha
  colors are blended onto the actual background at render time.
- **Colored text.** `$text-primary` etc. = `contrast_text.tint(primary.with_alpha(0.66))` (lines 279-285): white or
  black tinted 66% toward the role color, so it stays legible on a `-muted` background.
- **Muted.** `$primary-muted` etc. = `primary.blend(background, 0.7)` (lines 371-385); the guide calls this "blending
  them with `$background` at 70% opacity" (https://textual.textualize.io/guide/design/ "Muted colors").
- **Foreground.** `$foreground-muted = foreground @ 0.6`, `$foreground-disabled = foreground @ 0.38` (lines 387-393).
- **Widget-facing variables** (lines 395-528): `$block-cursor-foreground = $text`, `$block-cursor-background = primary`,
  `$block-cursor-text-style = bold`, `$block-cursor-blurred-foreground = foreground`,
  `$block-cursor-blurred-background = primary @ 0.3`, `$block-cursor-blurred-text-style = none`,
  `$block-hover-background = boost @ 0.1`; `$border = primary`, `$border-blurred = surface.darken(0.025)`,
  `$surface-active = surface.lighten(spread / 2.5)`; `$scrollbar = background-darken-1 + primary @ 0.4`,
  `$scrollbar-hover = ... @ 0.5`, `$scrollbar-active = primary`, `$scrollbar-background = background-darken-1`
  (+ `-hover`, `-active`, `$scrollbar-corner-color`); `$link-background = initial`, `$link-background-hover = primary`,
  `$link-color = $text`, `$link-style = underline`, `$link-color-hover = $text`, `$link-style-hover = bold not
  underline`; `$footer-foreground`, `$footer-background = panel`, `$footer-key-foreground = accent`,
  `$footer-key-background = transparent`, `$footer-description-*`, `$footer-item-background`;
  `$input-cursor-background = foreground`, `$input-cursor-foreground = background`, `$input-cursor-text-style = none`,
  `$input-selection-background = primary-lighten-1 @ 0.4`, `$input-selection-foreground = foreground`;
  `$markdown-h1..h6-color/-background/-text-style`; `$button-foreground = foreground`,
  `$button-color-foreground = $text`, `$button-focus-text-style = "b reverse"`; `$screen-selection-background =
  primary @ 0.5`. The guide's tables disagree with the source in a few places (scrollbar defaults, hover alpha 5% vs
  0.1, `textual-dark` primary `#004578` vs `#0178D4`); the source is authoritative.
- **ANSI mode** (`_generate_ansi`, lines 117-246): all shades collapse to the base, `-muted` becomes `"<color> 50%"`,
  `$text = ansi_default`, `$border = ansi_magenta`, scrollbar = `ansi_blue` / `ansi_bright_blue`. This is Textual's
  "follow the terminal" tier, exposed as the built-in themes `ansi-dark` / `ansi-light`.

Precedence (`app.py`, `get_css_variables`): theme `variables` > generated > `App.get_theme_variable_defaults()`.

### Built-in themes (`theme.py:70-513`, `BUILTIN_THEMES`)

`textual-dark, textual-light, nord, gruvbox, catppuccin-mocha, catppuccin-frappe, catppuccin-macchiato,
catppuccin-latte, dracula, tokyo-night, monokai, flexoki, solarized-light, solarized-dark, rose-pine, rose-pine-moon,
rose-pine-dawn, atom-one-dark, atom-one-light, ansi-dark, ansi-light` (there is no `textual-ansi`). Verbatim:

```python
"catppuccin-mocha": Theme(name="catppuccin-mocha", primary="#F5C2E7", secondary="#cba6f7", warning="#FAE3B0",
    error="#F28FAD", success="#ABE9B3", accent="#fab387", foreground="#cdd6f4",
    background="#181825", surface="#313244", panel="#45475a",
    variables={"input-cursor-foreground": "#11111b", "input-cursor-background": "#f5e0dc",
               "input-selection-background": "#9399b2 30%", "border": "#b4befe", "border-blurred": "#585b70",
               "footer-background": "#45475a", "block-cursor-foreground": "#1e1e2e",
               "block-cursor-text-style": "none", "button-color-foreground": "#181825"}),
"tokyo-night": Theme(name="tokyo-night", primary="#BB9AF7", secondary="#7AA2F7", warning="#E0AF68",
    error="#F7768E", success="#9ECE6A", accent="#FF9E64", foreground="#a9b1d6",
    background="#1A1B26", surface="#24283B", panel="#414868",
    variables={"button-color-foreground": "#24283B"}),
```

Two things to notice: Textual's `catppuccin-mocha` picks `pink` as primary and `mantle` (`#181825`) rather than `base`
as background, i.e. the mapping from a named palette to the seed roles is a judgment call the theme author makes; and
almost every dark theme overrides exactly one variable, `button-color-foreground`, to its background color, because the
computed `auto 87%` text on a saturated button background looked wrong to the authors.

### How widgets consume it (`src/textual/widgets/_button.py`, `DEFAULT_CSS`)

```css
&.-style-default {
    color: $button-foreground; background: $surface;
    border-top: tall $surface-lighten-1; border-bottom: tall $surface-darken-1;
    &:disabled { text-opacity: 0.6; }
    &:focus { text-style: $button-focus-text-style; background-tint: $foreground 5%; }
    &:hover { border-top: tall $surface; background: $surface-darken-1; }
    &.-active { background: $surface; border-bottom: tall $surface-lighten-1; border-top: tall $surface-darken-1;
                tint: $background 30%; }
    &.-primary {
        color: $button-color-foreground; background: $primary;
        border-top: tall $primary-lighten-3; border-bottom: tall $primary-darken-3;
        &:hover { background: $primary-darken-2; border-top: tall $primary; }
        &.-active { background: $primary; border-bottom: tall $primary-lighten-3; border-top: tall $primary-darken-3; }
    }
    /* -success / -warning / -error same pattern with $success etc.; -error hover uses $error-darken-1 */
}
&.-style-flat {
    color: auto 90%; background: $surface; border: block $surface;
    &:hover { background: $primary; border: block $primary; }
    &.-primary { background: $primary-muted; border: block $primary-muted; color: $text-primary;
                 &:hover { color: $text; background: $primary; border: block $primary; } }
}
```

So **states are computed**: hover = `-darken-2` of the role color, pressed = swap the bevel shades + `tint $background
30%`, focus = `background-tint $foreground 5%` + a text style, disabled = `text-opacity 0.6`. The widget never names a
hex; the theme never names a widget state. Variants (`default | primary | success | warning | error`,
`ButtonVariant`, line 26) are the role axis.

### Shape vs color

Textual's theme is colors + a few text-style strings (`$block-cursor-text-style`, `$link-style`,
`$button-focus-text-style`, `$markdown-hN-text-style`). Border *shape* (`tall`, `block`, `round`, `heavy` ...) lives in
widget CSS, not in the theme; a theme can only recolor it through `$border` / `$border-blurred`.

Sources: https://textual.textualize.io/guide/design/ ; https://textual.textualize.io/styles/color/ ;
https://github.com/Textualize/textual/blob/main/src/textual/design.py ;
https://github.com/Textualize/textual/blob/main/src/textual/theme.py ;
https://github.com/Textualize/textual/blob/main/src/textual/color.py ;
https://github.com/Textualize/textual/blob/main/src/textual/widgets/_button.py ;
https://github.com/Textualize/textual/blob/main/src/textual/app.py .

## 3. DaisyUI v5

DaisyUI is a Tailwind component library; its theme is a flat list of CSS custom properties, and it is the clearest
example of **shape tokens living inside the theme next to the colors**. Package version at fetch time: 5.8.0
(`packages/daisyui/package.json`).

### Semantic color set (20 tokens, exact)

From https://daisyui.com/docs/colors/ (source `packages/docs/src/routes/(routes)/docs/colors/+page.md` lines 107-128)
and the plugin's Tailwind color map (`packages/daisyui/functions/variables.js`):

| Token | CSS variable | Doc description |
|---|---|---|
| `primary` / `primary-content` | `--color-primary` / `--color-primary-content` | "Primary brand color" / "Foreground content color to use on `primary` color" |
| `secondary` / `secondary-content` | `--color-secondary(-content)` | "The optional, secondary color of your brand" |
| `accent` / `accent-content` | `--color-accent(-content)` | "The optional, accent color of your brand" |
| `neutral` / `neutral-content` | `--color-neutral(-content)` | "Neutral dark color, For not-saturated parts of UI" |
| `base-100` | `--color-base-100` | "Base surface color of page, used for blank backgrounds" |
| `base-200` | `--color-base-200` | "Base color, darker shade, to create elevations" |
| `base-300` | `--color-base-300` | "Base color, even more darker shade, to create elevations" |
| `base-content` | `--color-base-content` | "Foreground content color to use on `base` color" |
| `info` / `info-content` | `--color-info(-content)` | "For informative/helpful messages" |
| `success` / `success-content` | | "For success/safe messages" |
| `warning` / `warning-content` | | "For warning/caution messages" |
| `error` / `error-content` | | "For error/danger/destructive messages" |

Muted text is not a token: "The best way to do this is using Tailwind CSS color opacity modifier ... Like
`text-base-content/50`" (colors doc, line 203). All built-in themes are written in `oklch(...)`; v5 says "we can
finally put the whole color inside CSS variables. All color formats are supported, no transformation is needed"
(`docs/v5/+page@(routes).md` lines 189-195).

### Theme definition and shape tokens

From https://daisyui.com/docs/themes/ (source lines 104-156):

```css
@plugin "daisyui/theme" {
  name: "mytheme"; default: true; prefersdark: false; color-scheme: light;
  --color-base-100 ... --color-error-content;          /* 20 colors */
  --radius-selector: 1rem; --radius-field: 0.25rem; --radius-box: 0.5rem;
  --size-selector: 0.25rem; --size-field: 0.25rem;
  --border: 1px;
  --depth: 1; --noise: 0;
}
```

The eight non-color tokens and their option sets in the theme generator
(`packages/docs/src/routes/(routes)/theme-generator/+page.server.js` lines 15-52): `--radius-box` ("Boxes": card,
modal, alert), `--radius-field` ("Fields": button, input, select, tab), `--radius-selector` ("Selectors": checkbox,
toggle, badge) each from `0rem, 0.25rem, 0.5rem, 1rem, 2rem`; `--size-field` / `--size-selector` from five rem values,
multiplied per size class in components (`.btn { --size: calc(var(--size-field, 0.25rem) * 10); }`, `.btn-xs * 6` ...
`.btn-xl * 14`, `button.css`); `--border` from `0.5px, 1px, 1.5px, 2px`; `--depth` and `--noise` are `0 | 1` toggles
("3D depth on fields & selectors", "Noise pattern on fields & selectors"). A built-in theme file is a bare declaration
list: `color-scheme` + 20 colors + 8 shape tokens, e.g. `src/themes/dark.css` ends with
`--radius-selector: 0.5rem; --radius-field: 0.25rem; --radius-box: 0.5rem; --size-selector: 0.25rem; --size-field:
0.25rem; --border: 1px; --depth: 1; --noise: 0;`. 35 themes ship, including `nord` and `dracula`
(`packages/daisyui/src/themes/`).

### `-content` pairs are explicit, not derived

v5 has no contrast computation. `functions/themePlugin.js` destructures `name, default, prefersdark, color-scheme,
root, themecontroller` and spreads every other token verbatim into a CSS rule; an omitted `--color-primary-content` is
simply undefined (or inherited when `name` matches a built-in: `{ ...builtinTheme, ...customThemeTokens }`). The v4
`culori` dependency was removed ("daisyUI 5 has no dependencies", v5 page lines 101-119) and survives only as a dev test,
`functions/contrast.test.js`, which checks the 11 `[X, X-content]` pairs of every shipped theme for WCAG ratio > 3.
The theme generator (https://daisyui.com/theme-generator/, `+page.svelte`) treats `-content` as a separately picked
swatch; its only automatism is the randomizer's `getContrastColor`, which picks Tailwind shade `50` if the base shade
is `> 400`, else `950` (`src/lib/themeGeneratorRandomizer.js`).

### States: computed with `color-mix`, scaled by `--depth`

`packages/daisyui/src/components/button.css`. Variants only set two indirection variables:
`.btn-primary { --btn-color: var(--color-primary); --btn-fg: var(--color-primary-content); }`. Then:

```css
--btn-bg: var(--btn-color, var(--color-base-200));
--btn-border: color-mix(in oklab, var(--btn-color, var(--color-base-200)), #000 calc(var(--depth) * 5%));
--btn-shadow: 0 3px 2px -2px color-mix(in oklab, var(--btn-bg) calc(var(--depth) * 30%), #0000), ...;
--btn-inset: 0 0.5px 0 0.5px oklch(100% 0 0 / calc(var(--depth) * 6%));
background-size: auto, calc(var(--noise, 0) * 100%);
@media (hover: hover) { &:hover { --btn-bg: color-mix(in oklab, var(--btn-color, ...), #000 7%); } }
&:active, &.btn-active, &[aria-pressed="true"] { translate: 0 0.5px;
    --btn-bg: color-mix(in oklab, var(--btn-color, ...), #000 5%);
    --btn-border: color-mix(in oklab, var(--btn-color, ...), #000 7%); --btn-shadow: none-ish; }
&:focus-visible { outline-width: 2px; outline-style: solid; outline-color: var(--btn-color, var(--color-base-content)); }
&:disabled, .btn-disabled { color: color-mix(in oklch, var(--color-base-content) 20%, #0000); @apply bg-base-content/10; }
```

So hover = 7% black mixed in, active = 5% black plus a 0.5px shift, disabled = 20%-alpha text over a 10% wash; no
state token exists. `.btn-soft` mixes 8% of the color into `base-100`, `.btn-outline` / `.btn-ghost` use `--btn-color`
as border/text on a transparent background.

### Light/dark

Per-theme, not per-token: `@plugin "daisyui" { themes: light --default, dark --prefersdark; }`; `--prefersdark` emits
`@media (prefers-color-scheme: dark) { :root:not([data-theme]) { ... } }`, and `data-theme="NAME"` on any element
applies a theme to its subtree (`functions/pluginOptionsHandler.js`; https://daisyui.com/docs/themes/). `color-scheme`
in the theme tells the browser how to draw its own UI.

Sources: https://daisyui.com/docs/colors/ ; https://daisyui.com/docs/themes/ ; https://daisyui.com/theme-generator/ ;
https://github.com/saadeghi/daisyui `packages/daisyui/src/components/button.css`, `src/themes/dark.css`,
`functions/themePlugin.js`, `functions/pluginOptionsHandler.js`, `functions/variables.js`,
`functions/contrast.test.js`, `packages/docs/.../theme-generator/+page.server.js`, `.../docs/v5/+page@(routes).md`.

## 4. shadcn/ui and TweakCN

### shadcn token list and the `-foreground` convention

https://ui.shadcn.com/docs/theming (source `apps/v4/content/docs/(root)/theming.mdx`): "We use semantic background and
foreground pairs. The base token controls the surface color and the `-foreground` token controls the text and icon
color that sits on that surface." "The background suffix is omitted for the surface token. For example, `primary`
pairs with `primary-foreground`."

| Surface token | Foreground | Role note from the doc |
|---|---|---|
| `background` | `foreground` | page |
| `card` | `card-foreground` | |
| `popover` | `popover-foreground` | |
| `primary` | `primary-foreground` | |
| `secondary` | `secondary-foreground` | |
| `muted` | `muted-foreground` | "Subtle surfaces and lower-emphasis content" |
| `accent` | `accent-foreground` | "Interactive hover, focus, and active surfaces" |
| `destructive` | (none in the current default theme) | |
| `border`, `input`, `ring` | | `input` = "Form control borders and input surface treatment"; `ring` = "Focus rings and outlines" |
| `chart-1` .. `chart-5` | | |
| `sidebar`, `sidebar-primary`, `sidebar-accent` | `sidebar-foreground`, `sidebar-primary-foreground`, `sidebar-accent-foreground` | plus `sidebar-border`, `sidebar-ring` |
| `radius` | | the one shape token |

Default light values are oklch, e.g. `--background: oklch(1 0 0); --foreground: oklch(0.145 0 0); --primary:
oklch(0.205 0 0); --primary-foreground: oklch(0.985 0 0); --muted-foreground: oklch(0.556 0 0); --destructive:
oklch(0.577 0.245 27.325); --border: oklch(0.922 0 0); --ring: oklch(0.708 0 0); --radius: 0.625rem`. Dark mode
"works by overriding the same tokens inside a `.dark` selector" (`@custom-variant dark (&:is(.dark *))`); the dark
`--border: oklch(1 0 0 / 10%)` and `--input: oklch(1 0 0 / 15%)` are translucent white rather than opaque greys.
Tailwind v4 bridge: `@theme inline { --color-background: var(--background); ... --radius-sm: calc(var(--radius) *
0.6); --radius-md: * 0.8; --radius-lg: var(--radius); --radius-xl: * 1.4; ... }`.

### States: alpha modifiers or the `accent` pair

`apps/v4/registry/new-york-v4/ui/button.tsx`: `default: "bg-primary text-primary-foreground hover:bg-primary/90"`,
`secondary: "... hover:bg-secondary/80"`, `destructive: "bg-destructive text-white hover:bg-destructive/90
focus-visible:ring-destructive/20 dark:bg-destructive/60"`, `outline: "border bg-background hover:bg-accent
hover:text-accent-foreground dark:bg-input/30 dark:hover:bg-input/50"`, `ghost: "hover:bg-accent
hover:text-accent-foreground"`, base: `focus-visible:border-ring focus-visible:ring-[3px] focus-visible:ring-ring/50
disabled:pointer-events-none disabled:opacity-50 aria-invalid:border-destructive`. Hover is the same token at 90/80%
alpha (or a swap to the `accent` pair), focus is `ring` at 50%, disabled is whole-element 50% opacity; no pressed state.

### TweakCN

https://github.com/jnsahaj/tweakcn (README: "a powerful Visual Theme Editor for tailwind CSS & shadcn/ui components").
Its zod schema `themeStylePropsSchema` (`types/theme.ts`) is the 32 shadcn color tokens **plus `destructive-foreground`**,
then `font-sans`, `font-serif`, `font-mono`, `radius`, `shadow-color`, `shadow-opacity`, `shadow-blur`,
`shadow-spread`, `shadow-offset-x`, `shadow-offset-y`, `letter-spacing`, `spacing`; each preset has `styles.light` and
`styles.dark` with the non-color tokens shared (`COMMON_STYLES`, `config/theme.ts`; defaults `radius: 0.625rem`,
`shadow-opacity: 0.1`, `shadow-blur: 3px`, `shadow-offset-y: 1px`, `letter-spacing: 0em`, `spacing: 0.25rem`). Editor
tabs: Colors, Typography, Other (Font Family, Letter Spacing, HSL Adjustments, Radius, Spacing, Shadow;
`components/editor/theme-control-panel.tsx`). Output (`utils/theme-style-generator.ts`): `:root` / `.dark` blocks with
the color vars, fonts, `--radius`, raw `--shadow-x/-y/-blur/-spread/-opacity/-color`, computed `--shadow-2xs ..
--shadow-2xl`, `--tracking-normal`, `--spacing`, and an `@theme inline` block deriving `--tracking-tighter ..
--tracking-widest` and `--radius-sm/md/lg/xl`. Export: Tailwind v3 or v4 CSS in `hex | rgb | hsl | oklch`
(`types/index.ts:54`), and a shadcn registry item (`type: "registry:style"`, `cssVars: { theme, light, dark }`)
installable with `npx shadcn@latest add https://tweakcn.com/r/themes/{id}.json` (`utils/registry/themes.ts`,
`components/editor/code-panel.tsx`). Presets (`utils/theme-presets.ts`, 42): Modern Minimal, Violet Bloom, T3 Chat,
Twitter, Mocha Mousse, Bubblegum, Amethyst Haze, Notebook, Doom 64, **Catppuccin**, Graphite, Perpetuity, Kodama
Grove, Cosmic Night, Tangerine, Quantum Rose, Nature, Bold Tech, Elegant Luxury, Amber Minimal, Supabase, **Neo
Brutalism**, Solar Dusk, Claymorphism, Cyberpunk, Pastel Dreams, Clean Slate, Caffeine, Ocean Breeze, Retro Arcade,
Midnight Bloom, Candyland, Northern Lights, Vintage Paper, Sunset Horizon, Starry Night, Claude, Vercel, Darkmatter,
Mono, Soft Pop, Sage Garden. Catppuccin is the only well-known editor palette; Tokyo Night, Kanagawa, Nord, Dracula,
Gruvbox are absent (grep of `theme-presets.ts`). Notably TweakCN's "Neo Brutalism" is a *preset of the same token set*
(colors + radius 0 + hard shadows), i.e. in the shadcn world Neobrutalism is a palette-and-shape *value*, not a
separate component theme.

Sources: https://ui.shadcn.com/docs/theming ; https://github.com/shadcn-ui/ui
`apps/v4/content/docs/(root)/theming.mdx`, `apps/v4/registry/new-york-v4/ui/button.tsx`; https://tweakcn.com/editor/theme ;
https://github.com/jnsahaj/tweakcn `README.md`, `types/theme.ts`, `config/theme.ts`, `utils/theme-style-generator.ts`,
`utils/shadows.ts`, `utils/registry/themes.ts`, `utils/theme-presets.ts`, `components/editor/code-panel.tsx`,
`components/editor/theme-control-panel.tsx`.

## 6. ratatui and Bubble Tea / lipgloss / termenv

These two show the *color value model* of modern terminal libraries and the one piece of runtime detection they all
do: asking the terminal for its background color. (The xterm `ctlseqs.html` fetch failed with HTTP errors; the text
below cites Thomas Dickey's git mirror of the same document, `xterm-snapshots/ctlseqs.txt`, "updated for XTerm Patch
#411 (2026/08/23)".)

### ratatui: a closed `Color` enum, no theme concept in core

`ratatui_core::style::Color` (https://docs.rs/ratatui/latest/ratatui/style/enum.Color.html): `Reset` (`#[default]`,
"Resets the foreground or background color", i.e. SGR 39/49 = terminal default), the 16 ANSI names `Black, Red, Green,
Yellow, Blue, Magenta, Cyan, Gray` (SGR 30-37) and `DarkGray, LightRed, ..., White` (SGR 90-97), `Rgb(u8, u8, u8)`
("only terminals that support 24-bit true color will display this correctly"), `Indexed(u8)`. `Style { fg, bg,
underline_color, add_modifier, sub_modifier }` with a `patch` that lets a later style override only what it sets
(https://docs.rs/ratatui/latest/ratatui/style/struct.Style.html). There is no palette or theme type in ratatui and no
recipe for one on https://ratatui.rs/recipes/ (the `color-schemes` page is a 404). Community crates each define a
struct of named slots: `ratatui-themes` 0.4.0 (`ThemePalette` with `accent, bg, error, muted ...`, `ThemeName` enum of
15 built-ins incl. `TokyoNight`, https://docs.rs/ratatui-themes/latest/ratatui_themes/), `ratatui-themekit` 0.6.1
(`Theme` trait "Semantic color contract", `ThemeData` "15 color slots", constants `CATPPUCCIN_MOCHA`, `NORD`,
`TOKYO_NIGHT`, and a `NO_COLOR` theme that "uses `Color::Reset` throughout",
https://docs.rs/ratatui-themekit/latest/ratatui_themekit/), `rat-theme`, `ratatui-base16`. Takeaway: the semantic
palette is an app-level struct, and the "follow the terminal" tier is a theme whose every slot is `Reset` or an ANSI
name.

### lipgloss v1: color *selectors* instead of color values

`github.com/charmbracelet/lipgloss` v1.1.0, `color.go`:

- `Color("21")` / `Color("#0000ff")`: "specifies a color by hex or ANSI value"; resolved through
  `r.ColorProfile().Color(...)`, so termenv downgrades hex to 256/16 colors as needed.
- `AdaptiveColor{Light, Dark string}`: "The appropriate color will be returned at runtime based on the darkness of the
  terminal background color" (`if r.HasDarkBackground() { Dark } else { Light }`).
- `CompleteColor{TrueColor, ANSI256, ANSI string}`: "exact values for truecolor, ANSI256, and ANSI color profiles.
  Automatic color degradation will not be performed." `CompleteAdaptiveColor{Light, Dark CompleteColor}` combines both.
- `NoColor{}`: "foreground colors will be rendered with the terminal's default text color, and background colors will
  not be drawn at all."

lipgloss v2 (v2.1.0, 2026-11-09) replaces the structs with standard `image/color.Color` plus selector functions
`LightDark(isDark bool) func(light, dark color.Color) color.Color` and `Complete(p colorprofile.Profile) func(ansi,
ansi256, truecolor color.Color) color.Color`; the v1 structs survive in the `compat` package (`color.go`, `query.go`,
`compat/`). `HasDarkBackground(in, out)` "will return true if it encounters an error".

### termenv: the background query, precisely

`github.com/muesli/termenv` v0.16.0:

- `BackgroundColor()` -> `termStatusReport(11)`, `ForegroundColor()` -> `termStatusReport(10)` (`termenv_unix.go`).
  The function refuses when `TERM` starts with `screen`, `tmux` or `dumb` ("screen/tmux can't support OSC, because they
  can be connected to multiple terminals concurrently"), when there is no TTY, or when the process is not in the
  foreground. It sets the tty to no-echo/non-canonical, writes `OSC 11 ; ? ST` followed by `CSI 6 n` (cursor position
  report) as a sentinel "which should be supported by all terminals", and reads until the OSC reply or the CPR. Timeout
  `OSCTimeout = 5 * time.Second`.
- Reply parsing (`xTermColor`, `color.go`): expects `ESC ] 11 ; rgb:RRRR/GGGG/BBBB` + `BEL | ESC | ST`, 24-25 bytes,
  keeps the first two hex digits of each 16-bit component.
- Fallback: `COLORFGBG` split on `;`, first field = fg, **last** field = bg (so both `fg;bg` and `fg;default;bg` parse),
  else `ANSIColor(7)` / `ANSIColor(0)`. Windows, js, plan9 never query (`termenv_windows.go`, `termenv_other.go`).
- `HasDarkBackground()` = `ConvertToRGB(BackgroundColor()).Hsl()` lightness `< 0.5` (`output.go`); an ANSI index from
  `COLORFGBG` is mapped through the default xterm hex table first.
- Profile detection (`ColorProfile()`): not a TTY or `CI` set -> `Ascii`; `COLORTERM=truecolor|24bit` -> `TrueColor`
  (but `ANSI256` under `screen` unless `TERM_PROGRAM=tmux`); `TERM` in `alacritty, contour, rio, wezterm,
  xterm-ghostty, xterm-kitty` -> `TrueColor`; `*256color` -> `ANSI256`; `linux`, `xterm`, `*color`, `*ansi` -> `ANSI`.
  Downgrade (`color.go`): hex -> nearest of the 6x6x6 cube or the gray ramp by `DistanceHSLuv`, then -> nearest of
  indices 0-15.

Bubble Tea v2 wraps the same query as a message: `RequestBackgroundColor()` is a `Cmd`; the reply arrives as
`BackgroundColorMsg{color.Color}` with `IsDark()`, "commonly used in [Model.Init] to get the terminal background color
for style definitions" (https://pkg.go.dev/github.com/charmbracelet/bubbletea/v2, `color.go`). The constant is
`ansi.RequestBackgroundColor = "\x1b]11;?\x07"` (charmbracelet/x `ansi/background.go`).

Sources: https://docs.rs/ratatui/latest/ratatui/style/enum.Color.html ;
https://docs.rs/ratatui/latest/ratatui/style/struct.Style.html ; https://ratatui.rs/recipes/ ;
https://docs.rs/ratatui-themes/latest/ratatui_themes/ ; https://docs.rs/ratatui-themekit/latest/ratatui_themekit/ ;
https://pkg.go.dev/github.com/charmbracelet/lipgloss (v1.1.0 `color.go`, `renderer.go`);
https://pkg.go.dev/github.com/charmbracelet/lipgloss/v2 (`color.go`, `query.go`, `terminal.go`, `compat/`);
https://github.com/muesli/termenv (`termenv.go`, `termenv_unix.go`, `termenv_windows.go`, `termenv_other.go`,
`output.go`, `color.go`, `profile.go`); https://pkg.go.dev/github.com/charmbracelet/bubbletea/v2 ;
https://github.com/charmbracelet/x `ansi/background.go`, `ansi/util.go`.

## 7. Palette exchange formats and palette-to-role mappings

Two families exist: **slot-indexed** palettes (base16/base24, Gogh, Catppuccin's `ansiColors`) and **named palette +
semantic theme layer** (Catppuccin style guide, Tokyo Night `colors/init.lua`, Kanagawa `themes.lua`).

### base16 / base24 (tinted-theming)

Scheme file (`builder.md`, "Common Scheme Format"): `system: base16|base24`, `name`, `slug`, `author`, `description`,
`variant: dark|light` ("optional, but recommended"), `palette: { base00: "000000", ... }` with hex values, `#`
optional. Roles from `styling.md` v0.4.2:

| key | ANSI slot | editor meaning |
|---|---|---|
| base00 | 0 | Default Background |
| base01 | 18 | Lighter Background (status bars) |
| base02 | 19 | Selection Background |
| base03 | 8 | Comments, Invisibles, Line Highlighting |
| base04 | 20 | Dark Foreground (status bars) |
| base05 | 7 | Default Foreground, Caret, Delimiters, Operators |
| base06 | 21 | Light Foreground |
| base07 | 15 | The Lightest Foreground |
| base08 | 1 and 9 | Variables, XML Tags, Markup Link Text, Diff Deleted |
| base09 | 16 | Integers, Boolean, Constants, XML Attributes |
| base0A | 3 and 11 | Classes, Markup Bold, Search Text Background |
| base0B | 2 and 10 | Strings, Inherited Class, Diff Inserted |
| base0C | 6 and 14 | Support, Regular Expressions, Escape Characters |
| base0D | 4 and 12 | Functions, Methods, Attribute IDs, Headings |
| base0E | 5 and 13 | Keywords, Storage, Selector, Diff Changed |
| base0F | 17 | Deprecated, Opening/Closing Embedded Language Tags |

"Colors `base00` to `base07` are typically variations of a shade and run from darkest to lightest for dark themes"
(light schemes invert). base24 adds `base10`, `base11` (darker backgrounds) and `base12`..`base17` (bright red, yellow,
green, cyan, blue, magenta), with a fallback table to base16 (`base24/styling.md` v0.1.3). base24's styling also adds UI
roles: text `base05` on `base00`, current line `base01`, focused tab fg `base00` on `base0D`, inactive `base03`, warning
`base0F`, errors `base08`, selected menu option on `base02`, search matches `base06`. The shell template
(`tinted-shell/templates/base16.mustache`) maps `color00 = base00, 01 = base08, 02 = base0B, 03 = base0A, 04 = base0D,
05 = base0E, 06 = base0C, 07 = base05, 08 = base03, 09..14 = 01..06, 15 = base07, 16 = base09, 17 = base0F, 18 = base01,
19 = base02, 20 = base04, 21 = base06`, fg `base05`, bg `base00`. Note the caveat "It can't be assumed that the extended
ANSI colors are set when using a tinted-theming terminal template theme."

### Gogh

A terminal scheme only: `color_01`..`color_08` (regular), `color_09`..`color_16` (bold), `background`, `foreground`,
`cursor`, plus `name`, `author`, `variant: dark|light` (`docs/CONTRIBUTING.md`). No semantic roles beyond ANSI slots;
the template comments are the only hints ("Blue (Path)", "Bright Red (Command error)"). `themes/Catppuccin Mocha.yml`
is exactly Catppuccin's `ansiColors` (e.g. `color_01: '#45475A'`, `color_02: '#F38BA8'`, `color_05: '#89B4FA'`,
`color_06: '#F5C2E7'` (pink), `color_07: '#94E2D5'` (teal), `background: '#1E1E2E'`, `foreground: '#CDD6F4'`,
`cursor: '#F5E0DC'`). ~1000 themes; the catalog is published as `data/themes.json`. No Tokyo Night file.

### Catppuccin

`palette.json` v1.8.0: four flavors (`latte` with `dark: false`; `frappe`, `macchiato`, `mocha` with `dark: true`),
each with 26 named `colors` (`hex`, `rgb`, `hsl`, `oklch`, `accent: bool`) and 8 `ansiColors` with `normal`/`bright`.

| name | Mocha | Latte | | name | Mocha | Latte |
|---|---|---|---|---|---|---|
| rosewater | #f5e0dc | #dc8a78 | | text | #cdd6f4 | #4c4f69 |
| flamingo | #f2cdcd | #dd7878 | | subtext1 | #bac2de | #5c5f77 |
| pink | #f5c2e7 | #ea76cb | | subtext0 | #a6adc8 | #6c6f85 |
| mauve | #cba6f7 | #8839ef | | overlay2 | #9399b2 | #7c7f93 |
| red | #f38ba8 | #d20f39 | | overlay1 | #7f849c | #8c8fa1 |
| maroon | #eba0ac | #e64553 | | overlay0 | #6c7086 | #9ca0b0 |
| peach | #fab387 | #fe640b | | surface2 | #585b70 | #acb0be |
| yellow | #f9e2af | #df8e1d | | surface1 | #45475a | #bcc0cc |
| green | #a6e3a1 | #40a02b | | surface0 | #313244 | #ccd0da |
| teal | #94e2d5 | #179299 | | base | #1e1e2e | #eff1f5 |
| sky | #89dceb | #04a5e5 | | mantle | #181825 | #e6e9ef |
| sapphire | #74c7ec | #209fb5 | | crust | #11111b | #dce0e8 |
| blue | #89b4fa | #1e66f5 | | | | |
| lavender | #b4befe | #7287fd | | | | |

The first 14 are `accent: true`. Mocha ANSI: black `#45475a`/`#585b70`, red `#f38ba8`/`#f37799`, green
`#a6e3a1`/`#89d88b`, yellow `#f9e2af`/`#ebd391`, blue `#89b4fa`/`#74a8fc`, magenta `#f5c2e7`/`#f2aede` (pink),
cyan `#94e2d5`/`#6bd7ca` (teal), white `#a6adc8`/`#bac2de`. The style guide (`docs/style-guide.md`) is the
palette-to-role layer:

| Role | Catppuccin color |
|---|---|
| Background Pane | Base |
| Secondary Panes | Crust, Mantle |
| Surface Elements | Surface 0, Surface 1, Surface 2 |
| Overlays | Overlay 0, Overlay 1, Overlay 2 |
| Body Copy, Main Headline | Text |
| Sub-Headlines, Labels | Subtext 0, Subtext 1 |
| Subtle | Overlay 1 |
| On Accent | Base |
| Links, URLs; Tags, Pills | Blue |
| Success / Warnings / Errors | Green / Yellow / Red |
| Selection Background | Overlay 2 at 20-30% opacity |
| Cursor | Rosewater |
| Terminal active / inactive border | Lavender / Overlay 0 |
| Editor: Information | Teal |

with the caveat "Text colors are guidelines, certain cases require deviations ... Legibility always comes first." The
accent itself is user-chosen (hence `themes/mocha/blue.yml` and 13 siblings in catppuccin/lazygit).

### Tokyo Night (folke/tokyonight.nvim)

`lua/tokyonight/colors/night.lua` is `storm` plus three overrides (`bg = "#1a1b26"`, `bg_dark = "#16161e"`,
`bg_dark1 = "#0C0E14"`). Effective **night** palette (`colors/storm.lua` + overrides): `bg #1a1b26`, `bg_dark
#16161e`, `bg_highlight #292e42`, `fg #c0caf5`, `fg_dark #a9b1d6`, `fg_gutter #3b4261`, `comment #565f89`, `dark3
#545c7e`, `dark5 #737aa2`, `terminal_black #414868`, `blue #7aa2f7`, `blue0 #3d59a1`, `blue1 #2ac3de`, `blue2
#0db9d7`, `blue5 #89ddff`, `blue6 #b4f9f8`, `blue7 #394b70`, `cyan #7dcfff`, `green #9ece6a`, `green1 #73daca`,
`green2 #41a6b5`, `magenta #bb9af7`, `magenta2 #ff007c`, `orange #ff9e64`, `purple #9d7cd8`, `red #f7768e`, `red1
#db4b4b`, `teal #1abc9c`, `yellow #e0af68`, `git.add #449dab`, `git.change #6183bb`, `git.delete #914c54`. Four
styles: `night`, `storm`, `moon`, `day`.

The semantic layer is arithmetic (`colors/init.lua`, `util.lua`: `blend(fg, alpha, bg) = alpha*fg + (1-alpha)*bg`,
`blend_bg(hex, amount) = blend(hex, amount, bg)`):

```lua
colors.diff = { add = blend_bg(green2, 0.25), delete = blend_bg(red1, 0.25), change = blend_bg(blue7, 0.15), text = blue7 }
colors.black = blend_bg(bg, 0.8, "#000000");  colors.border = colors.black
colors.border_highlight = blend_bg(blue1, 0.8)
colors.bg_popup = bg_dark; colors.bg_statusline = bg_dark          -- "always get a dark background"
colors.bg_sidebar / bg_float = bg | bg_dark | none  (opts.styles.sidebars / floats)
colors.bg_visual = blend_bg(blue0, 0.4);  colors.bg_search = blue0
colors.fg_sidebar = fg_dark; colors.fg_float = fg
colors.error = red1; colors.warning = yellow; colors.info = blue2; colors.hint = teal; colors.todo = blue
```

Its lazygit extra (`lua/tokyonight/extra/lazygit.lua`; 47 extras in total, from alacritty and kitty to tmux, fzf,
helix, yazi and `tailwindv4`) is a template over this merged table:

```yaml
gui:
  nerdFontsVersion: "3"
  theme:
    activeBorderColor: ["${orange}", "bold"]
    inactiveBorderColor: ["${border_highlight}"]
    searchingActiveBorderColor: ["${orange}", "bold"]
    optionsTextColor: ["${blue}"]
    selectedLineBgColor: ["${bg_visual}"]
    cherryPickedCommitFgColor: ["${blue}"]
    cherryPickedCommitBgColor: ["${magenta}"]
    markedBaseCommitFgColor: ["${blue}"]
    markedBaseCommitBgColor: ["${yellow}"]
    unstagedChangesColor: ["${red1}"]
    defaultFgColor: ["${fg}"]
```

Compare with the Catppuccin file in section 2: both authors reach for the same roles (an accent for active borders, a
dim color for inactive borders, a low-contrast surface for the selected line, red for unstaged), but Tokyo Night uses a
*derived* `bg_visual` (40% blue over bg) where Catppuccin picks a *named* surface (`surface0`).

### Kanagawa (rebelot/kanagawa.nvim)

The only system here with a first-class, typed, per-variant **palette -> theme** data structure. `colors.lua`
defines `palette` (named colors: `sumiInk0..6` backgrounds, `waveBlue1/2` popups, `fujiWhite`/`oldWhite`/`fujiGray`
foregrounds, `oniViolet`, `crystalBlue`, `springGreen`, `carpYellow`, `waveRed`, `samuraiRed`, `roninYellow`,
`dragonBlack0..6`, `lotusWhite0..5` ...), and `setup(opts)` merges `override_colors.palette`, calls
`themes[theme](palette)` to build the semantic table, then merges `override_colors.theme.all` and
`override_colors.theme[theme]`. `themes.lua` documents the semantic classes:

- `ui`: `fg` "Default foreground", `fg_dim`, `fg_reverse`, `bg_dim`, `bg_m3`, `bg_m2`, `bg_m1`, `bg` "Default
  background", `bg_p1` "Lighter background ColorColumn, Folded, Gutter", `bg_p2` "Lighter background
  Cursor{Line,Column}, TabLineSel (Selected Items)", `bg_gutter`, `special`, `nontext`, `whitespace`, `bg_search`,
  `bg_visual`, `pmenu { fg, fg_sel, bg, bg_sel, bg_sbar, bg_thumb }`, `float { fg, bg, fg_border, bg_border }`
- `syn`: `string, variable, number, constant, identifier, parameter, fun, statement, keyword, operator, preproc, type,
  regex, deprecated, comment, punct, special1, special2, special3`
- `diag`: `error, ok, warning, info, hint`; `diff`: `add, delete, change, text`; `vcs`: `added, removed, changed`;
  `term`: 18 colors (16 ANSI + 2 extended)

`wave`'s `ui` block, verbatim: `fg = fujiWhite, fg_dim = oldWhite, fg_reverse = waveBlue1, bg_dim = sumiInk1,
bg_gutter = sumiInk4, bg_m3 = sumiInk0, bg_m2 = sumiInk1, bg_m1 = sumiInk2, bg = sumiInk3, bg_p1 = sumiInk4, bg_p2 =
sumiInk5, special = springViolet1, nontext = sumiInk6, whitespace = sumiInk6, bg_search = waveBlue2, bg_visual =
waveBlue1, pmenu = { fg = fujiWhite, fg_sel = "none", bg = waveBlue1, bg_sel = waveBlue2, bg_sbar = waveBlue1,
bg_thumb = waveBlue2 }, float = { fg = oldWhite, bg = sumiInk0, fg_border = sumiInk6, bg_border = sumiInk0 }`; `diag =
{ ok = springGreen, error = samuraiRed, warning = roninYellow, info = dragonBlue, hint = waveAqua1 }`. The `bg_m3 ..
bg .. bg_p2` ladder (minus 3 to plus 2 around the base background) is Kanagawa's version of Textual's shades and
DaisyUI's `base-100/200/300`, but hand-assigned from the palette rather than computed. Variants `wave` (dark), `dragon`
(dark, `dragonBlack*`), `lotus` (light, `lotusWhite*`) are functions of one shared palette; README maps
`background = { dark = "wave", light = "lotus" }` to Vim's `background` option. Keys from the task brief that do **not**
exist on master: `ui.win_separator`, `ui.indent`, `ui.indent_scope`, `ui.picker.*`.

Sources: https://github.com/tinted-theming/home (`styling.md`, `builder.md`); https://github.com/tinted-theming/base24
(`styling.md`); https://github.com/tinted-theming/tinted-shell (`templates/base16.mustache`, `base24.mustache`);
https://gogh-co.github.io/Gogh/ ; https://github.com/Gogh-Co/Gogh (`docs/CONTRIBUTING.md`, `themes/Catppuccin
Mocha.yml`); https://github.com/catppuccin/palette (`README.md`, `palette.json`);
https://github.com/catppuccin/catppuccin/blob/main/docs/style-guide.md ; https://github.com/folke/tokyonight.nvim
(`lua/tokyonight/colors/night.lua`, `storm.lua`, `init.lua`, `util.lua`, `lua/tokyonight/extra/lazygit.lua`);
https://github.com/rebelot/kanagawa.nvim (`lua/kanagawa/colors.lua`, `themes.lua`, `README.md`).

## 8. Terminal palette following

There are exactly three mechanisms by which a terminal application can "follow" the user's terminal theme, and the
systems above use all three.

1. **Indexed colors.** SGR 30-37 / 90-97 / `38;5;n` and SGR 39/49 (`default`) are *indices* into the emulator's palette.
   The emulator resolves them at paint time, so changing the emulator palette recolors the app. This is how mc's
   `terminal.ini` and `default.ini`, lazygit's default theme (`green`, `blue`, `default`), ratatui's `Color::Reset` /
   named variants and Textual's `ansi-dark` theme follow the terminal. mc's source states the reason it never converts
   the basic 16: "they are terminal-dependent and user-configurable" (`lib/tty/color-internal.c:264`). The price is a
   16-color vocabulary and no control over contrast; base16 and Catppuccin both publish *which* palette color sits in
   each slot (section 7) precisely so that apps using indices land on sensible colors.
2. **Querying the dynamic colors.** xterm's control sequences (`ctlseqs.txt`, "Operating System Commands"): "The 10
   colors (below) which may be set or queried using 1 0 through 1 9 are denoted dynamic colors ... the dynamic text
   foreground and background colors are used when ANSI colors are reset using SGR 3 9 and 4 9"; "If a "?" is given
   rather than a name or RGB specification, xterm replies with a control sequence of the same form which can be used to
   set the corresponding dynamic color." `OSC 10 ; ?` = text foreground, `OSC 11 ; ?` = text background, `OSC 12 ; ?`
   = cursor; `OSC 4 ; c ; ?` queries palette entry `c` ("The color numbers correspond to the ANSI colors 0-7, their
   bright versions 8-15, and if supported, the remainder of the 88-color or 256-color table"); `OSC 104` / `110` /
   `111` / `112` reset. "XTerm accepts either BEL or ST for terminating OSC sequences, and when returning information,
   uses the same terminator used in a query." The reply is formatted by xterm as `"%d;rgb:%04x/%04x/%04x"`
   (`misc.c`, `ReportColorRequest`), i.e. 16-bit components in XParseColor syntax (`rgb:<red>/<green>/<blue>`, `h |
   hh | hhh | hhhh`, scaled; libX11 `XQueryColor.man`). lazygit (section 2), termenv, lipgloss v2 and Bubble Tea
   (section 6) all send `OSC 11 ; ?`; termenv also sends `OSC 10 ; ?`; none of them sends `OSC 4 ; c ; ?` for the 16
   palette entries. lazygit additionally sends `CSI ? 996 n` (reply `CSI ? 997 ; 1|2 n`, dark/light) and enables
   `CSI ? 2031 h` change notifications, but trusts the OSC 11 answer over the dark/light report
   (`pkg/gocui/color_scheme.go:55-80`). Every implementation needs a sentinel (`CSI 6 n` in termenv, DA1 in lipgloss v2,
   tcell's own queries in lazygit) and a timeout (5 s termenv, 2 s lipgloss v2) because a terminal that does not
   understand the query simply never answers; termenv also refuses under `screen`/`tmux`.
3. **`COLORFGBG`.** rxvt-unicode's `rxvt.1.pod`: "Set to a string of the form `fg;bg` or `fg;xpm;bg`, where `fg` is the
   colour code used as default foreground/text colour (or the string `default` ...), `bg` is the colour code used as
   default background colour ... Libraries like `ncurses` and `slang` can (and do) use this information to optimize
   screen output." Only termenv reads it (last field = bg), and only as a fallback. mc does not read it (grep of the
   clone).

The dark/light verdict is HSL (termenv, lipgloss) or CIE L* (lazygit) lightness `< 0.5` of the background in every
implementation; lipgloss and lazygit assume dark when nothing answers.

**Relevance to Zircon.** Zircon is not a terminal emulator: the Compose/Swing/wasm renderers paint RGB tiles, so
mechanism 1 does not exist for them and mechanisms 2/3 have nothing to query. `Color` is opaque RGB or fully
`TRANSPARENT` with "no partial transparency" (`zircon.core/.../api/color/Color.kt:24-28`), and `Color.kt` has no
lighten/darken/blend operation today (grep of the file); `DEFAULT_FOREGROUND_COLOR` / `DEFAULT_BACKGROUND_COLOR` are
`DefaultAnsiPalette[WHITE]` / `[BLACK]` (`Color.kt:45-51`), i.e. Zircon already carries a 16-entry ANSI palette with
fixed RGB values. The terminal renderer emits only truecolor SGR `38;2` / `48;2` (`zircon.terminal/.../AnsiEncoder.kt:180-185`),
never indexed or `default` colors, and `RunTerminal.kt:136` queries only the kitty keyboard protocol and DA1, never
OSC 10/11. So for the terminal target, "follow the terminal" would be a *new* capability on two possible paths: (a) an
`OSC 10/11 ; ?` query at startup (with sentinel + timeout, as every library above does) feeding a `ComponentPalette`
derived from the answer, or (b) a palette whose entries are *symbolic ANSI indices* that the encoder turns into SGR
30-37/90-97/39/49, which the other renderers would resolve through `DefaultAnsiPalette`. Both are synthesis; see the
next sections.

Sources: https://invisible-island.net/xterm/ctlseqs/ctlseqs.html (fetched as
https://raw.githubusercontent.com/ThomasDickey/xterm-snapshots/master/ctlseqs.txt);
https://raw.githubusercontent.com/ThomasDickey/xterm-snapshots/master/misc.c ;
https://gitlab.freedesktop.org/xorg/lib/libx11/-/raw/master/man/XQueryColor.man ;
https://raw.githubusercontent.com/exg/rxvt-unicode/master/doc/rxvt.1.pod ; sections 1, 2 and 6 above; Zircon
`zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/color/Color.kt`,
`zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/AnsiEncoder.kt`, `RunTerminal.kt`.

## 9. Comparison

| System | Seed colors | Derived shades? | fg/content pairs explicit? | States computed? | Non-color tokens in the theme | Light/dark |
|---|---|---|---|---|---|---|
| mc skin | ~90 fg;bg;attr pairs, per widget and state | no | every key is a full pair | no, every state is a key (`dfocus`, `menusel`, `disabled` ...) | yes: `[Lines]` glyphs, `[widget-*]` chars, attributes | none; separate skins |
| lazygit | 12 keys (+ author/branch maps) | no (2 defaults mixed from the terminal bg) | only where a bg is painted (`cherryPicked*`, `markedBase*`) | no; focused/unfocused/searching are keys | no; `gui.border` and `nerdFontsVersion` are separate keys | `colorScheme` + `darkTheme` / `lightTheme` overlays, OSC 11 detection |
| DaisyUI v5 | 20 (10 roles x `-content`, 3-step `base` ladder) | no (`base-100/200/300` hand-picked) | yes, `X` / `X-content`, hand-picked, tested > 3:1 | yes: `color-mix(... #000 7%)` hover, 5% active, scaled by `--depth` | yes: 3 radii, 2 sizes, `--border`, `--depth`, `--noise` | per theme (`--default`, `--prefersdark`, `data-theme`) |
| shadcn/ui | 32 (surface / `-foreground` pairs, `border`, `input`, `ring`, charts, sidebar) | no | yes, `X` / `X-foreground` | yes: alpha (`/90`, `/80`), `ring/50`, `opacity-50`, or swap to `accent` | `--radius` only (TweakCN adds fonts, shadows, tracking, spacing) | `.dark` overrides the same names |
| Textual | 11 (`primary, secondary, warning, error, success, accent, foreground, background, surface, panel, boost`) + `dark` | yes: 3 lighten + 3 darken (7.5 L* each), `-muted` (70% toward bg), `$text-X` | computed (`auto 87%`, `$text-X`), overridable via `variables` | yes: widget CSS uses `-darken-2`, `tint`, `background-tint`, `text-opacity` | a few text-style strings; border shape in widget CSS | `dark` flag on the theme; `ansi-*` themes for terminal following |
| ratatui | none in core; crates use 15-slot structs | no | no | no | no | `Color::Reset` only |
| lipgloss / termenv | none; per-color `AdaptiveColor{Light, Dark}`, `CompleteColor{TrueColor, ANSI256, ANSI}` | no | no | no | no | runtime OSC 11 / `COLORFGBG`, HSL `< 0.5` |
| base16 / base24 | 16 / 24 slots with documented roles | no (`base00..07` *is* the ladder) | no (fg/bg roles are slots) | no | no | `variant: dark|light` per file |
| Catppuccin | 26 named + 16 ANSI, 4 flavors | no (`crust/mantle/base/surface0-2/overlay0-2` *is* the ladder) | style guide: "On Accent -> Base" | no | no | `dark: bool` per flavor |
| Tokyo Night | ~35 named per style | yes, arithmetic: `blend_bg(x, 0.15..0.8)` for `bg_visual`, `border`, `diff.*` | no | no | no | 4 styles (`night` = `storm` + 3 keys) |
| Kanagawa | ~100 named palette -> typed `theme` (ui/syn/diag/diff/vcs/term) | no (`bg_m3..bg_p2` hand-assigned) | `fg`/`bg` fields per ui element (`float`, `pmenu`) | no | no | 3 themes as functions of one palette, `background = { dark = wave, light = lotus }` |

## 10. Implications for Zircon (synthesis, not sourced fact)

Everything in this section is my reading of the sources above; the citations point at the pattern being borrowed, not
at any statement about Zircon.

### The split the sources agree on

Every system that supports more than one look separates three things, even when it stores them in one file:

1. **Named palette** (Catppuccin's 26 colors, Tokyo Night's `storm.lua`, Kanagawa's `palette`, base16's 16 slots):
   colors with *aesthetic* names and no meaning.
2. **Semantic layer** (Catppuccin style guide, Kanagawa `theme.ui.*`, Textual's seeds + generated variables, DaisyUI /
   shadcn tokens): colors with *role* names, assigned from the palette by a human (Kanagawa, Catppuccin, shadcn) or
   partly derived (Textual, Tokyo Night).
3. **Consumers** (widgets / highlight groups / components) that reference roles only. Textual's `Button` never names a
   hex; lazygit's views read `theme.*`; Kanagawa's highlight groups read `theme.ui.bg_p2`.

Shape lives in a *fourth* place in the two systems closest to Zircon: lazygit's `gui.border` is a separate key from
`gui.theme`, and Textual's border styles sit in widget CSS. DaisyUI is the counter-example (radius / border / depth /
noise *inside* the theme), and TweakCN's "Neo Brutalism" preset shows where that leads: in the web world Neobrutalism is
a *value* of the shape tokens (radius 0, hard shadow) plus a palette, not a separate component kit. mc is the cautionary
tale: glyphs and colors in one file means N x M copies (`modarin256`, `-defbg`, `-thin`, `-root`, and all four
combinations).

So the proposed `ComponentTheme` (renderers + decorations = shape) x `ComponentPalette` (roles -> colors) matches the
lazygit / Textual arrangement, and the open question is only how the two meet: the theme's `ComponentStyleSet` for a
role must be *computed from* the palette, the way Textual's CSS computes from `$primary` and DaisyUI's `button.css`
computes from `--btn-color`.

### Recommended semantic key set

Synthesized from the overlap of DaisyUI (section 3), shadcn (section 4), Textual (section 5), Catppuccin's style guide
and Kanagawa's `ui` (section 7), trimmed to what Zircon components actually paint (a tile has one fg, one bg, no
alpha). Names follow the shadcn/DaisyUI "surface token + its content color" convention because every pair is explicit
and a tile renderer cannot compute `auto` contrast at paint time without a blend operation.

| Key | Meaning | Sources for the role |
|---|---|---|
| `background` / `onBackground` | the screen; body text | shadcn `background/foreground`, Textual `background/foreground`, DaisyUI `base-100/base-content`, Catppuccin Base/Text, Kanagawa `ui.bg/ui.fg` |
| `surface` / `onSurface` | panels, cards, dialogs, one step up | Textual `surface`, Kanagawa `bg_p1`, Catppuccin Surface 0, DaisyUI `base-200`, shadcn `card` |
| `surfaceRaised` / `onSurfaceRaised` | popups, modals, tooltips, two steps up | Textual `panel`, Kanagawa `bg_p2` / `float.bg`, Catppuccin Surface 1 / Overlays, DaisyUI `base-300`, shadcn `popover` |
| `surfaceSunken` | wells, input fields, status bars, one step down | Kanagawa `bg_m1`/`bg_dim`, Catppuccin Mantle, Tokyo Night `bg_dark`, mc `[statusbar]`, Textual `boost` (inverse) |
| `textMuted`, `textDisabled` | labels, hints; disabled text | Textual `$text-muted` / `$text-disabled`, shadcn `muted-foreground`, Catppuccin Subtext 0 / Overlay 1, Kanagawa `fg_dim` / `nontext`, mc `disabled` |
| `border`, `borderFocused` | frame glyph color at rest / on the focused component | Textual `$border-blurred` / `$border`, lazygit `inactiveBorderColor` / `activeBorderColor`, Catppuccin "Inactive Border Overlay 0 / Active Border Lavender", Kanagawa `float.fg_border` |
| `primary` / `onPrimary` | the brand / main action | all of DaisyUI, shadcn, Textual, TweakCN; Catppuccin "accent" (user-chosen) |
| `secondary` / `onSecondary` | less emphasized action | DaisyUI, shadcn, Textual |
| `accent` / `onAccent` | highlight, hover surfaces, badges | DaisyUI `accent`, shadcn `accent` ("Interactive hover, focus, and active surfaces"), Textual `accent` (footer keys) |
| `selection` / `onSelection` | selected list row, marked text | lazygit `selectedLineBgColor`, mc `selected`/`marked`, Kanagawa `bg_visual`, Tokyo Night `bg_visual`, Textual `$block-cursor-*`, Catppuccin "Selection Background" |
| `info`, `success`, `warning`, `error` (+ `on*`) | status | DaisyUI, Textual, Tokyo Night `info/warning/error/hint`, Kanagawa `diag.*`, Catppuccin Teal/Green/Yellow/Red, base24 `base08` errors / `base0F` warning |
| `focusRing` (optional) | focus indicator color when it is not the border | shadcn `ring`, Textual `$block-cursor-background` (= primary) |

That is 11 surface/role colors with an `on*` partner (22) plus 7 single colors (`surfaceSunken`, `textMuted`,
`textDisabled`, `border`, `borderFocused`, `selection`'s partner counted above, `focusRing`) = about 28 keys, in the
same range as DaisyUI (20) and shadcn (32) and well below Kanagawa's `ui` block. The legacy `ColorTheme` maps onto a
strict subset: `primaryBackground -> background`, `secondaryBackground -> surface`, `primaryForeground ->
onBackground`, `secondaryForeground -> textMuted`, `accent -> primary`, which gives a mechanical migration path for the
77 existing color themes (`zircon.core/.../api/ColorThemes.kt` has 77 functions returning `ColorTheme`).

Deliberately **not** included, with the reason: a `neutral` role (DaisyUI) is covered by the surface ladder; shadcn's
`card`/`popover`/`sidebar` triplets are the same surface ladder under layout names; Textual's `boost` is an alpha overlay
Zircon cannot paint; `chart-1..5` are out of scope; `cursor` (Catppuccin Rosewater) belongs to the input renderer, not
the palette; mc's per-widget keys (`menuhot`, `helpslink`) are what roles + states replace.

### Concrete mappings

Catppuccin Mocha (hex from `palette.json` v1.8.0, assignments following the style guide rows in section 7; the
Textual and catppuccin/lazygit choices are noted where they differ):

| Key | Catppuccin | Hex | Note |
|---|---|---|---|
| `background` / `onBackground` | base / text | `#1e1e2e` / `#cdd6f4` | "Background Pane" / "Body Copy"; Textual picks mantle `#181825` as background |
| `surface` / `onSurface` | surface0 / text | `#313244` / `#cdd6f4` | "Surface Elements"; catppuccin/lazygit uses surface0 for `selectedLineBgColor` |
| `surfaceRaised` / `onSurfaceRaised` | surface1 / text | `#45475a` / `#cdd6f4` | Textual `panel` = surface1 `#45475a` |
| `surfaceSunken` | mantle | `#181825` | "Secondary Panes: Crust, Mantle" |
| `textMuted` / `textDisabled` | subtext0 / overlay1 | `#a6adc8` / `#7f849c` | "Sub-Headlines, Labels" / "Subtle" |
| `border` / `borderFocused` | overlay0 / lavender | `#6c7086` / `#b4befe` | "Inactive Border" / "Active Border"; Textual `border-blurred` = surface2 `#585b70` |
| `primary` / `onPrimary` | blue / base | `#89b4fa` / `#1e1e2e` | default accent in catppuccin/lazygit; "On Accent -> Base"; Textual picks pink |
| `secondary` / `onSecondary` | mauve / base | `#cba6f7` / `#1e1e2e` | Textual `secondary` = mauve |
| `accent` / `onAccent` | peach / base | `#fab387` / `#1e1e2e` | Textual `accent` = peach |
| `selection` / `onSelection` | surface2 / text | `#585b70` / `#cdd6f4` | opaque stand-in for "Overlay 2 at 20-30%"; lazygit theme uses surface0 |
| `info` / `onInfo` | teal / base | `#94e2d5` / `#1e1e2e` | editor "Information: Teal"; sapphire/sky are alternatives |
| `success` / `onSuccess` | green / base | `#a6e3a1` / `#1e1e2e` | |
| `warning` / `onWarning` | yellow / base | `#f9e2af` / `#1e1e2e` | |
| `error` / `onError` | red / base | `#f38ba8` / `#1e1e2e` | |
| `focusRing` | lavender | `#b4befe` | same as `borderFocused` |

Tokyo Night, `night` style (hex from `colors/storm.lua` + `night.lua` overrides; derived values via `blend_bg` are
given as the formula because the result is computed at load time):

| Key | Tokyo Night | Hex | Note |
|---|---|---|---|
| `background` / `onBackground` | bg / fg | `#1a1b26` / `#c0caf5` | Textual uses `fg_dark #a9b1d6` as foreground |
| `surface` / `onSurface` | bg_highlight / fg | `#292e42` / `#c0caf5` | Textual `surface` = storm's bg `#24283b` |
| `surfaceRaised` / `onSurfaceRaised` | terminal_black / fg | `#414868` / `#c0caf5` | Textual `panel` = `#414868` |
| `surfaceSunken` | bg_dark | `#16161e` | `bg_popup`, `bg_statusline` "always get a dark background" |
| `textMuted` / `textDisabled` | dark5 / comment | `#737aa2` / `#565f89` | `fg_gutter #3b4261` is the next step down |
| `border` / `borderFocused` | border_highlight / orange | `blend_bg(blue1 #2ac3de, 0.8)` / `#ff9e64` | both from `extra/lazygit.lua` (`inactiveBorderColor`, `activeBorderColor`) |
| `primary` / `onPrimary` | blue / bg_dark | `#7aa2f7` / `#16161e` | Textual picks magenta `#bb9af7` as primary, blue as secondary |
| `secondary` / `onSecondary` | magenta / bg_dark | `#bb9af7` / `#16161e` | |
| `accent` / `onAccent` | orange / bg_dark | `#ff9e64` / `#16161e` | Textual `accent` = orange |
| `selection` / `onSelection` | bg_visual / fg | `blend_bg(blue0 #3d59a1, 0.4)` / `#c0caf5` | lazygit `selectedLineBgColor`; `bg_search = blue0` is the opaque alternative |
| `info` / `onInfo` | blue2 / bg_dark | `#0db9d7` / `#16161e` | `colors.info` |
| `success` / `onSuccess` | green / bg_dark | `#9ece6a` / `#16161e` | Textual `success` |
| `warning` / `onWarning` | yellow / bg_dark | `#e0af68` / `#16161e` | `colors.warning` |
| `error` / `onError` | red1 / fg | `#db4b4b` / `#c0caf5` | `colors.error` and `unstagedChangesColor`; Textual uses `red #f7768e` |
| `focusRing` | orange | `#ff9e64` | |

Two lessons from building these tables: (a) every author picks a *different* primary for the same palette (Textual:
pink; catppuccin/lazygit: blue, with 13 alternatives; Textual Tokyo Night: magenta; tokyonight's lazygit extra:
orange for borders, blue for options), so `primary` must be a palette-author choice and ideally a user override, as
Catppuccin's accent variants are; (b) the surface ladder is the part that needs human judgment most, because palettes
disagree about how many steps they have (Catppuccin 6: crust..surface2; Tokyo Night 4: bg_dark..terminal_black;
Kanagawa 6: bg_m3..bg_p2).

### State variants: three options

Zircon's `ComponentStyleSet` has five states (`DEFAULT, HIGHLIGHTED, FOCUSED, DISABLED, ACTIVE`,
`api/component/data/ComponentState.kt`). The sources offer three ways to fill them from a palette:

1. **Explicit per state** (mc, lazygit, shadcn-without-Tailwind). The palette carries a color for every (role, state).
   Honest and renderer-independent, but it is what makes mc skins 90 lines and what forced lazygit to add
   `darkTheme`/`lightTheme` overlays. For ~28 keys x 5 states this is ~140 colors per palette: too many for palette
   authors, and it leaks state semantics (what "highlighted" means in Neobrutalism vs a flat theme) into the palette.
2. **Computed from the role color** (Textual, DaisyUI). Textual: hover = `-darken-2` (15 L*), pressed = bevel swap +
   `tint $background 30%`, focus = `background-tint $foreground 5%` + text style, disabled = `text-opacity 0.6`
   (section 5). DaisyUI: hover = `color-mix(in oklab, color, #000 7%)`, active = 5% + shift, disabled = 20% alpha text
   over a 10% wash (section 3). Both need a *blend* primitive. Zircon's `Color` has none and no partial alpha, so this
   option implies adding `Color.blend(other, amount)` (linear RGB is enough: Tokyo Night's `blend`, lazygit's
   `mixHexColors` and Textual's `Color.blend` are all linear RGB; only Textual's `lighten/darken` go through Lab) and
   letting the *theme*, not the palette, own the derivation rule, since the hover of a Neobrutalist button (a hard color
   swap or a shadow shift) is not the hover of a flat button (a 7% darken).
3. **Hybrid** (Textual's `variables`, lazygit's `themeDefaults`, Kanagawa's `theme` overrides): the theme computes
   defaults from the palette's base roles, and the palette may *override* specific derived slots. Textual's built-in
   themes show how little is overridden in practice (most dark themes override one variable,
   `button-color-foreground`; `catppuccin-mocha` overrides nine), which argues that computed defaults are good enough
   as a baseline and a small override map covers taste.

Option 3 maps naturally onto the existing design: `ComponentTheme.entryFor(role)` already produces a `ComponentStyleSet`;
with a palette it would produce it through a per-theme `StyleSetDerivation(palette, role) -> ComponentStyleSet` whose
defaults are "DEFAULT = (surface, onSurface), HIGHLIGHTED = blend toward accent/primary, ACTIVE = swap to (primary,
onPrimary), FOCUSED = DEFAULT with `borderFocused`, DISABLED = (surface, textDisabled)" and which consults
`palette.overrides[role, state]` first. A palette then stays ~28 colors plus an optional sparse override map, exactly
Textual's shape.

### Light/dark and the terminal

- Every multi-mode system keys light/dark at the *palette* level (Catppuccin flavors with `dark: bool`, Kanagawa `wave`
  vs `lotus`, Textual `Theme.dark`, DaisyUI `prefersdark`, base16 `variant`). `ThemeMode` in `docs/component-themes.md`
  therefore belongs on the palette selection, not on the renderers: `ComponentThemes.neobrutalism { palette =
  Palettes.catppuccin(MOCHA) }` or `{ palette = Palettes.catppuccin(mode = SYSTEM) }` resolving to latte/mocha.
- Textual's only dark-specific derivation (`primary-background` blended toward white, section 5) and lazygit's
  `themeDefaults` (blue vs mixed-blue selection, section 2) show that derivation rules may need a `dark` flag; the
  palette should expose one.
- For the terminal renderer specifically, the "follow the terminal" tier can be a palette whose entries are ANSI indices
  (`AnsiPaletteColor(index)` resolved through `DefaultAnsiPalette` on graphical renderers and emitted as SGR 30-37 /
  90-97 / 39 / 49 on the terminal), which is how mc's `terminal.ini`, lazygit's defaults and Textual's `ansi-*` themes
  work; or an OSC 10/11 query at startup building an RGB palette, which lazygit, termenv, lipgloss and Bubble Tea do.
  The index approach needs the encoder to learn indexed colors; the query approach needs a sentinel and a timeout and
  fails silently under tmux (termenv refuses there; and this repo's own learning records that tmux never answers the
  kitty keyboard query, so the same caution applies).

### Open questions

1. Does `Color` grow a `blend`/`lighten` operation (option 2/3 above), or do themes ship explicit per-state
   derivations from a fixed set of palette keys only (option 1)? Textual's `luminosity_spread = 0.15` / 7.5 L* per
   shade and DaisyUI's 7%/5% mixes are starting values if it does.
2. How many surface steps does the palette expose: three (`surfaceSunken`, `surface`, `surfaceRaised`, like DaisyUI's
   `base-100/200/300`) or a computed ladder (Textual's `-lighten-1..3`)? The former is easier to map from Catppuccin /
   Kanagawa; the latter needs the blend primitive.
3. Is `primary` chosen by the palette author or by the user (Catppuccin ships 14 accent variants; TweakCN and Textual
   fix one)? A `Palette.withAccent(color)` override would mirror catppuccin/lazygit's directory layout.
4. Is there a role for `focusRing` separate from `borderFocused`, or does Zircon's single-fg/single-bg tile make them
   the same thing in practice?
5. Should palettes be loadable from a file format? base16 YAML is the only cross-tool standard and covers 16 slots with
   documented roles (section 7); a `Palettes.fromBase16(scheme)` mapping (`base00 -> background`, `base01 ->
   surfaceSunken`/status bars, `base02 -> selection`, `base03 -> textDisabled`, `base04 -> textMuted`, `base05 ->
   onBackground`, `base0D -> primary`, `base0E -> secondary`, `base09 -> accent`, `base08 -> error`, `base0A ->
   warning`, `base0B -> success`, `base0C -> info`) would give several hundred palettes for free from
   tinted-theming/schemes.
6. Should the Neobrutalism theme's hard-coded `NeobrutalismPalette` (the trail learning for 2026-11-14 lists it under the
   theme API) become the first `ComponentPalette`, i.e. a palette with the shape-agnostic key set above, so that
   Neobrutalism-with-Catppuccin is a one-line swap?

## Sources

Local files (this machine, 2026-11-14):

- `/usr/share/mc/skins/default.ini`, `dark.ini`, `modarin256-defbg.ini`, `seasons-winter16M.ini`
- `~/.local/share/mc/skins/terminal.ini`; `~/.config/mc/ini` (line 86, `skin=terminal`); `mc --version` (4.8.33, S-Lang 2.3.3)
- `man mc`: sections "Colors", "Skins", "Description of section and parameters", "Color pair definitions", "Draw lines"
- Zircon: `docs/component-themes.md`; `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/color/Color.kt`;
  `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/ColorTheme.kt`;
  `zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/component/data/ComponentState.kt`;
  `zircon.terminal/src/commonMain/kotlin/org/hexworks/zircon/terminal/AnsiEncoder.kt`, `RunTerminal.kt`;
  `.trail/learnings/2026-11-14-component-themes-grill-2026-11-14-bruce.md`,
  `.trail/learnings/2026-10-30-tmux-never-answers-the-kitty-keyboard-bruce.md`

Midnight Commander (https://github.com/MidnightCommander/mc, master `e51e8d79b0e0`, 2026-11-14): `lib/skin/colors.c`,
`lib/skin/ini-file.c`, `lib/skin/common.c`, `lib/skin/lines.c`, `lib/skin/hc-skins.c`, `lib/tty/color-internal.c`,
`lib/tty/color.c`, `lib/tty/color-slang.c`, `lib/tty/color-ncurses.c`, `lib/widget/gauge.c`, `lib/terminal.c`.

lazygit (https://github.com/jesseduffield/lazygit, master `c63f9b024ca1`, 2026-11-14): `docs/Config.md`,
`pkg/theme/style.go`, `pkg/theme/theme.go`, `pkg/gui/style/basic_styles.go`, `pkg/config/theme_config.go`,
`pkg/config/user_config.go`, `pkg/gui/views.go`, `pkg/gui/gui.go`, `pkg/gocui/color_scheme.go`.
https://github.com/catppuccin/lazygit: `README.md`, `themes-mergable/mocha/blue.yml`.

DaisyUI: https://daisyui.com/docs/colors/ , https://daisyui.com/docs/themes/ , https://daisyui.com/theme-generator/ ;
https://github.com/saadeghi/daisyui (5.8.0): `packages/daisyui/src/components/button.css`,
`packages/daisyui/src/themes/dark.css`, `packages/daisyui/functions/themePlugin.js`,
`functions/pluginOptionsHandler.js`, `functions/variables.js`, `functions/contrast.test.js`,
`packages/docs/src/routes/(routes)/theme-generator/+page.server.js`, `+page.svelte`,
`packages/docs/src/lib/themeGeneratorRandomizer.js`, `packages/docs/src/routes/(routes)/docs/v5/+page@(routes).md`.

shadcn/ui and TweakCN: https://ui.shadcn.com/docs/theming ; https://github.com/shadcn-ui/ui
`apps/v4/content/docs/(root)/theming.mdx`, `apps/v4/registry/new-york-v4/ui/button.tsx`;
https://tweakcn.com/editor/theme ; https://github.com/jnsahaj/tweakcn `README.md`, `types/theme.ts`,
`types/index.ts`, `config/theme.ts`, `utils/theme-style-generator.ts`, `utils/shadows.ts`, `utils/registry/themes.ts`,
`utils/theme-presets.ts`, `public/r/registry.json`, `components/editor/code-panel.tsx`,
`components/editor/theme-control-panel.tsx`.

Textual: https://textual.textualize.io/guide/design/ ; https://textual.textualize.io/styles/color/ ;
https://textual.textualize.io/styles/background/ ; https://github.com/Textualize/textual (main, 2026-11-14):
`src/textual/design.py`, `src/textual/theme.py`, `src/textual/color.py`, `src/textual/app.py`,
`src/textual/constants.py`, `src/textual/css/_styles_builder.py`, `src/textual/widgets/_button.py`, `mkdocs-nav.yml`,
`CHANGELOG.md`. (`/api/design/` and `/api/theme/` are 404.)

ratatui, lipgloss, termenv, Bubble Tea: https://docs.rs/ratatui/latest/ratatui/style/enum.Color.html ;
https://docs.rs/ratatui/latest/ratatui/style/struct.Style.html ; https://docs.rs/ratatui/latest/ratatui/style/struct.Modifier.html ;
https://ratatui.rs/recipes/ ; https://ratatui.rs/concepts/ ; https://docs.rs/ratatui-themes/latest/ratatui_themes/ ;
https://docs.rs/ratatui-themekit/latest/ratatui_themekit/ ; https://docs.rs/rat-theme/latest/rat_theme/ ;
https://pkg.go.dev/github.com/charmbracelet/lipgloss (v1.1.0 `color.go`, `renderer.go`);
https://pkg.go.dev/github.com/charmbracelet/lipgloss/v2 (`color.go`, `query.go`, `terminal.go`, `compat`);
https://github.com/muesli/termenv (v0.16.0: `termenv.go`, `termenv_unix.go`, `termenv_windows.go`,
`termenv_other.go`, `output.go`, `color.go`, `profile.go`); https://pkg.go.dev/github.com/charmbracelet/bubbletea/v2
(`color.go`, `tea.go`); https://github.com/charmbracelet/x `ansi/background.go`, `ansi/util.go`.

Palette formats: https://github.com/tinted-theming/home (`styling.md` v0.4.2, `builder.md` v0.11.2);
https://github.com/tinted-theming/base24 (`styling.md` v0.1.3); https://github.com/tinted-theming/tinted-shell
(`templates/base16.mustache`, `base24.mustache`, `config.yaml`); https://gogh-co.github.io/Gogh/ ;
https://github.com/Gogh-Co/Gogh (`docs/CONTRIBUTING.md`, `themes/Catppuccin Mocha.yml`, `data/themes.json`);
https://github.com/catppuccin/palette (`README.md`, `palette.json` v1.8.0);
https://github.com/catppuccin/catppuccin/blob/main/docs/style-guide.md ; https://github.com/folke/tokyonight.nvim
(`lua/tokyonight/colors/night.lua`, `storm.lua`, `init.lua`, `lua/tokyonight/util.lua`,
`lua/tokyonight/extra/lazygit.lua`, `lua/tokyonight/extra/` listing); https://github.com/rebelot/kanagawa.nvim
(`lua/kanagawa/colors.lua`, `lua/kanagawa/themes.lua`, `README.md`).

Terminal control sequences: https://invisible-island.net/xterm/ctlseqs/ctlseqs.html (fetch failed; read via
https://raw.githubusercontent.com/ThomasDickey/xterm-snapshots/master/ctlseqs.txt, XTerm Patch #411);
https://raw.githubusercontent.com/ThomasDickey/xterm-snapshots/master/misc.c ;
https://gitlab.freedesktop.org/xorg/lib/libx11/-/raw/master/man/XQueryColor.man ;
https://raw.githubusercontent.com/exg/rxvt-unicode/master/doc/rxvt.1.pod .

Fetch failures: `textual.textualize.io/api/design/`, `/api/theme/` (404, pages do not exist);
`invisible-island.net/xterm/ctlseqs/ctlseqs.html` (HTTP error, mirror used); `ratatui.rs/recipes/apps/color-schemes/`
(404); `man.archlinux.org/man/rxvt.7` (404, pod source used); `tokyonight.nvim/lua/tokyonight/extras/lazygit.lua`
(404, the directory is `extra/`); `tinted-shell/templates/default.mustache` (404, `base16.mustache` used).
