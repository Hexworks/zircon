# System Theme and Palette Tuning Handover

Follow-up to [component-palettes.md](component-palettes.md) (palettes landed, `ColorTheme` removed 2026-11-17) and
the terminal color following (`runTerminal(onTerminalColors)`, `Palettes.fromAnsi`, same day). Two sessions,
in this order; each one ends with the doc's Status line, the api dumps (`:zircon.core:apiDump`,
`:zircon.terminal:apiDump`, `:zircon.compose:apiDump`) and a `trail note`.

## Context to load first

- `.trail/learnings/2026-11-17-the-terminal-renderer-adopts-the-terminal-bruce.md`: how the terminal gets
  its colors (OSC 10/11/4 in the handshake, mode 2031 report, 2 s background poll), what tmux does not do.
- `.trail/learnings/2026-11-17-colortheme-is-gone-colors-come-only-bruce.md`: there is one color model now,
  `ComponentPalette`; default `Palettes.catppuccin()`.
- `ROADMAP.md`, "Palette import and terminal following": the agreed design, repeated below.
- Code: `zircon.core/.../api/component/theme/ThemeMode.kt` (`resolve()` asks the detector),
  `SystemThemeDetector.kt` + the platform `actual`s (`jvmMain`, `jsMain`, `linuxMain`, `macosMain`,
  `iosMain`), `api/config/ZirconDefaults.kt` (`systemThemeDetector`), `api/Palettes.kt`,
  `zircon.terminal/.../RunTerminal.kt` (`TerminalSession.publishColors`, `onTerminalColors`),
  `zircon.terminal/.../TerminalColors.kt`, `zircon.compose/.../TileGridView.kt` (the composable),
  `zircon.examples/.../themes/NeobrutalismExample.kt` (`SystemPaletteFollower`, the hand-rolled poller),
  `zircon.examples/.../ExamplesScreen.kt` (`useTerminalPalette`, the palette selector),
  `zircon.examples.terminal/src/commonMain/kotlin/main.kt` (the `onTerminalColors` glue).

## Session 1: `SystemTheme`

### Design (decided 2026-11-17)

`ThemeMode.SYSTEM` stays the app's wish ("whatever the host wants"). The one-shot
`SystemThemeDetector.detect()` behind it is replaced by one live object in core:

```kotlin
public object SystemTheme {
    /** LIGHT or DARK, what the host reports now; never SYSTEM. */
    public val modeProperty: Property<ThemeMode>
    /** The host's exact colors, null when it has none (tmux, desktop today). */
    public val paletteProperty: Property<ComponentPalette?>
    /** Renderers and platform watchers call these. */
    public fun update(mode: ThemeMode? = null, palette: ComponentPalette? = null)
    /** Re-samples ZirconDefaults.systemThemeDetector; the fallback source. */
    public fun refresh()
}
```

- `ThemeMode.resolve()` reads `SystemTheme.modeProperty.value`. `Palettes.x(ThemeMode.SYSTEM)` keeps working
  as a one-shot. `ZirconDefaults.systemThemeDetector` stays as the initial/fallback source `refresh()` samples.
- A palette stays an immutable value. Nothing observable lives inside a palette; the only observable is
  `SystemTheme` and the screen's own `componentPaletteProperty`.
- Sources write the properties:
  - terminal: `TerminalSession.publishColors` calls `SystemTheme.update(mode = if (colors.isDark) DARK else LIGHT,
    palette = colors.toComponentPalette())` besides `onTerminalColors`; a terminal that never answers leaves both
    untouched (`refresh()` then gives the OS answer, which is right for the desktop terminal case);
  - Compose: `isSystemInDarkTheme()` inside `TileGridView` is live; a `LaunchedEffect`/`SideEffect` on it calls
    `update(mode = …)`;
  - web (`jsMain`/`wasmJs`): `window.matchMedia("(prefers-color-scheme: dark)")` with a `change` listener;
  - plain JVM/native: a renderer tick or the app calls `refresh()` every ~2 s (what `SystemPaletteFollower` does
    by hand today).
- Consumer, on `Screen`:

```kotlin
public fun Screen.followSystemPalette(fallback: (ThemeMode) -> ComponentPalette): Subscription
```

  binds `componentPalette = SystemTheme.paletteProperty.value ?: fallback(SystemTheme.modeProperty.value)` and
  re-runs on either property. An explicit `screen.componentPalette = x` afterwards ends the follow (same rule as a
  pinned palette ending inheritance): the binding disposes itself when the screen's palette is set to something
  it did not produce. `ZirconDefaults { followSystemPalette = { mode -> … } }` makes a new screen follow from the
  start.

### Steps

1. Core: `SystemTheme` (+ tests with `SystemThemeDetector.fixed`: `update` changes `modeProperty`, `refresh()`
   samples the detector, `resolve()` reads the property). Keep `SystemThemeDetector` public; drop nothing yet.
2. `Screen.followSystemPalette` (+ `ZirconDefaults.followSystemPalette`): test with a fake host palette, then
   `null` + mode flip, then an explicit set ending the follow.
3. Terminal source in `TerminalSession.publishColors` (extend `TerminalSessionTest.readsTheTerminalColorsAtStartup…`
   to assert `SystemTheme.paletteProperty`); keep `onTerminalColors`, it costs nothing.
4. Compose source in `TileGridView`; web source in `jsMain` + `wasmJsMain` (the browser detector already exists).
5. Examples: the palette selector gets a `System` entry (a small `PaletteChoice = System | Fixed(palette)` in the
   examples, or a sentinel mapped in `ExamplesScreen`) that calls `followSystemPalette { mode -> family(mode) }`
   with the family last selected (Catppuccin → Latte/Mocha, Tokyo Night → Day/Night, Kanagawa → Lotus/Wave,
   Neobrutalism → light/dark); any concrete entry ends the follow. Delete `SystemPaletteFollower` and the Neo
   page's System/Light/Dark buttons become the selector's job (or keep them as shortcuts). Delete
   `useTerminalPalette` and the launcher glue; in the terminal the `System` entry is the live terminal palette.
   `ExamplesScreenTest`: selecting `System` under a fixed detector gives the family's light/dark; a pushed host
   palette wins; selecting a concrete palette stops following.
6. Docs: README terminal and desktop sections (`followSystemPalette` replaces the `onTerminalColors` example),
   `docs/component-palettes.md` status, this doc's Status line.

### Watch out

- `SystemTheme` is process-wide static state, like `ZirconDefaults`: never capture a screen in anything stored
  there (the examples' error-handler chain leak of 2026-11-17); the follow binding must be disposable and
  disposed when the screen closes.
- cobalt properties now keep their listeners in the property's own scope (cobalt change of 2026-11-17, must be
  released before zircon 2.0.0), so a dropped screen with a live follow is collected once the binding is disposed.
- tmux: no colors, no 2031, no kitty query; `System` there means the family in the OS light/dark.
- The detectors shell out (`gsettings`, `defaults`, `reg`): `refresh()` must not run per frame.

## Session 2: new palettes and color tuning

- Candidates (pick with the user; each is one `componentPalette { }` from the project's own style guide, like
  `BundledPalettes`): Nord (the examples' default before 2026-11-17), Solarized (light/dark), Gruvbox,
  Dracula, Rosé Pine, Everforest. Add to `Palettes` with a `mode` overload where a light/dark pair exists, to
  `Palettes.all()`, and to `PalettesTest`.
- Tuning by eye in the desktop and terminal demos, both shapes (Default, Neobrutalism), every bundled palette:
  hover/focus/active contrast of buttons and tabs, `textMuted` on `surface`, selection, the Neobrutalism light
  `textMuted`, and the `Palettes.fromAnsi` mapping (surface steps 0.04/0.08, selection 0.22, muted fallback 0.55)
  on a few real terminal themes. Fixes go into `PaletteStyles` (derivation) or a palette's `overrides` (one slot).
- Golden images (`zircon.compose`, `zircon.terminal`) are rendered with `Palettes.catppuccin()`; a derivation
  change refreshes them with `UPDATE_GOLDENS=1`.

## Rules that held

- Commits: subject line only, no body, no AI footers. Commit only when asked.
- `trail check` before committing; `trail note` for what the diff cannot show.
- New feature ideas go to `ROADMAP.md` as short outlines.

## Status

- Session 1: not started.
- Session 2: not started.
