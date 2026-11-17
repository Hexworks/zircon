---
title: "Palette tuning is done from a contrast table, not by eye alone: the 1.x hover rule read at 1:1 on every palette, and every ON_* color is now picked by contrast"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 29, tokens: 250031 }
id: l-7a6dd788
scope: repo
status: active
src: agent
touches:
  - README.md
  - ROADMAP.md
  - docs/component-palettes.md
  - docs/system-theme-handover.md
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/SystemThemeSource.kt
  - zircon.compose/src/commonMain/kotlin/org/hexworks/zircon/compose/TileGridView.kt
  - zircon.compose/src/jvmMain/kotlin/org/hexworks/zircon/compose/SystemThemeSource.jvm.kt
  - zircon.compose/src/wasmJsMain/kotlin/org/hexworks/zircon/compose/SystemThemeSource.wasmJs.kt
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/config/ZirconDefaultsBuilder.kt
---

## Decided
2026-11-17, session 2 of docs/system-theme-handover.md. Solarized and Gruvbox (medium) joined the bundled palettes; Nord, Dracula, Rosé Pine and Everforest stay on the roadmap. The tuning started from a WCAG contrast table over all 17 bundled palettes plus seven real terminal palettes through `Palettes.fromAnsi` (Catppuccin Mocha, Solarized dark/light, Gruvbox, Tokyo Night, xterm dark/light), then offscreen renders of the examples app confirmed by eye.
- The default theme's hover (`TEXT_MUTED` on the emphasis color, the 1.x rule) was 1.0-1.9:1 on every palette: unreadable. Now the three filled states keep the `ON_*` text and differ by background: hovered = color washed 30% toward `SURFACE`, focused = the color, pressed = color pushed 50% away from its text (toward white under dark text, toward black under light text). Pushing toward `ON_BACKGROUND` instead collapsed on light palettes, where the on-color is the dark text itself (Latte warning 1.7:1).
- Every bundled family picks the text on an accent by contrast between its two ladder ends (`readableOn`): Solarized shares its accents between modes, so base03 goes on blue/cyan/green/yellow and base3 on violet/orange/red whatever the mode; Latte's yellow and Lotus's orange want the ink, not the base.
- `fromAnsi`: `ON_*` is whichever of foreground/background contrasts more, black/white ink when neither reaches 3:1 (Solarized's grey foreground on its own blue was 1.2:1); a normal slot under 3:1 on the background gives way to its bright twin when that is better (xterm blue on black); magenta is the secondary (a 15% step was invisible as button text); bright black must reach 2.5:1 to be the muted text (Catppuccin's surface2 did not).
- Palette fixes: Tokyo Night `on*` on `bg_dark` or `fg` by contrast (fg on red1 was 1.2:1 in Day), Day's raised step 40% toward terminal_black (fg barely read on it), Tokyo Night selection `blue0` at 70% (the theme's 40% `bg_visual` is 1.1:1 against `bg_highlight`, where a selected tab sits), Kanagawa dark variants' selection waveBlue2 for the same reason, Neobrutalism light muted gray-500 (gray-400 was 2.5:1 on white), Neobrutalism dark on-primary = paper (white ink on yellow-600 was 2.9:1).
- `PaletteContrastTest` keeps floors over `Palettes.all()`, set by the weakest member rounded down (Tokyo Night Day, Solarized): a guard for the derivations, not WCAG.

## Tried and ruled out
- Tuning by eye in the running app first: a numeric table found the systematic faults in one pass; the renders were only needed to judge the selections and the washes.
- Raising Solarized's muted text or Catppuccin's borders: both are the projects' own low-contrast choices; left, with the floors set below them.

## Watch out
- Light-mode accents as text are weak and left alone: Latte peach and Lotus yellow are 1.7-1.9:1 on their surfaces; nothing in the default theme draws `ACCENT` as text, Neobrutalism uses it as a fill.
- Neobrutalism's primary is a fill color (1.3:1 as text on white); under the default theme it shows as faint yellow text. `PaletteContrastTest` excludes it from the primary-as-text floor.
- Offscreen renders of the examples from another module need the examples' `internal` properties (reflection) and a resource loader that serves the demo files from `zircon.examples/resources/files` and everything else from `ComposeResourceLoader` (which `install()` re-sets); Gradle skips an unchanged test task even when an env var changed, use `--rerun`.

In the code:
- `zircon.core/.../api/component/theme/PaletteStyles.kt` (`interactive`, `hovered`, `pressed`, `HOVER_WASH`, `PRESS_INK`), `internal/component/theme/ColorContrast.kt` (`luminance`, `contrast`, `readableOn`), `AnsiPalettes.kt`, `BundledPalettes.kt` (`solarized`, `gruvbox`, the `on()` helpers), `neobrutalism/NeobrutalismPalettes.kt`, `api/Palettes.kt`.
- Tests: `PaletteContrastTest`, `PalettesTest` (17), `AnsiPaletteTest`.
- Examples: `Catalog.kt#paletteFamilyOf` knows the two new families.
