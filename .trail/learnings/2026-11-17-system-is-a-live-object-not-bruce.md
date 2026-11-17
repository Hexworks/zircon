---
title: "System is a live object, not a one-shot detector: SystemTheme holds the host's mode and palette, renderers write it, followSystemPalette binds a screen to it u…"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 15, tokens: 230077 }
id: l-3cbe8d83
scope: repo
status: active
src: agent
touches:
  - docs/system-theme-handover.md
---

## Decided
2026-11-17, session 1 of docs/system-theme-handover.md. `ThemeMode.SYSTEM` stays the app's wish; what the host wants right now is `SystemTheme` (core, process-wide like `ZirconDefaults`): `modeProperty` (LIGHT/DARK, never SYSTEM) and `paletteProperty` (the host's exact colors, null when it has none). `ThemeMode.resolve()` reads the mode property, so `Palettes.x(SYSTEM)` is a one-shot of the last known answer, not a shell-out per call. `ZirconDefaults.systemThemeDetector` is demoted to the fallback source `SystemTheme.refresh()` samples (once at first touch, then whoever polls).
- Sources: the terminal session publishes its OSC colors as the palette and its background luminance as the mode; while the terminal never answered (tmux) it calls `refresh()` on its 2 s poll. `TileGridView` trusts Compose's `isSystemInDarkTheme()` only where Skiko knows the theme (macOS, Windows, browser); on the Linux desktop Skiko answers UNKNOWN, so it samples the detector off the UI thread every 2 s. The browsers (jsMain, wasmJsMain) install a `matchMedia('(prefers-color-scheme: dark)')` change listener when `SystemTheme` is first touched.
- Consumer: `Screen.followSystemPalette { mode -> family(mode) }` sets `componentPalette = SystemTheme.palette ?: fallback(mode)` and re-runs on either property. It ends itself when the screen's palette is set to something it did not produce (identity check), when the screen closes, or when the returned Subscription is disposed. `ZirconDefaults.followSystemPalette` makes every new screen follow from the start.
- The properties are exposed read-only (`ObservableValue`), only `update()` writes; `reset()` forgets the palette and re-samples, for a renderer shutting down and for tests (every test that touches `SystemTheme` resets it in tearDown; it is static state).
- Examples: the selector's entries are `PaletteChoice` (`System` | `Fixed(palette)`); `System` follows with the family of the palette picked last (`paletteFamilyOf`, by name prefix). A palette set from outside (the Neobrutalism page's Light/Dark buttons) flips the choice to `Fixed` and ends the follow; the follow's own writes are told apart by name against `SystemTheme.palette ?: family(mode)`. The terminal app starts on `System`, so the first frame already has the terminal palette; `useTerminalPalette`, the launcher's `onTerminalColors` glue and `SystemPaletteFollower` are gone.

## Tried and ruled out
- Comparing palettes by equality to tell the follow's writes from explicit sets in the examples: `ComponentPalette` is an interface without equals and `Palettes.catppuccin(mode)` builds a new instance per call; names are unique, so the name is compared.
- Letting `isSystemInDarkTheme()` drive the mode on every Compose host: on Linux it is a constant `false` (Skiko UNKNOWN) and would have overridden the correct gsettings answer with LIGHT.

## Watch out
- `SystemTheme.refresh()` shells out on the desktop (gsettings, defaults, reg): never per frame. The terminal calls it on the loop thread every 2 s only while the terminal has not answered.
- A test that sets `ZirconDefaults.current.systemThemeDetector` must call `SystemTheme.refresh()` for `resolve()` to see it, and `SystemTheme.reset()` afterwards.
- Two follows on one screen: the second's first write disposes the first only if the palette actually changes; dispose the old subscription yourself when re-following (the examples do).

In the code:
- `zircon.core/.../api/component/theme/SystemTheme.kt` (+ `SystemThemeSource.{jvm,native,js,wasmJs}.kt`), `ThemeMode.resolve()`, `api/extensions/ScreenExtensions.kt#followSystemPalette`, `ZirconDefaults.followSystemPalette`, `TileGridScreen` init.
- `zircon.terminal/.../RunTerminal.kt#publishColors` and the `COLOR_POLL_MS` branch; `zircon.compose/.../SystemThemeSource.kt` + `TileGridView`.
- `zircon.examples/.../Catalog.kt` (`PaletteChoice`, `PALETTE_CHOICES`, `paletteFamilyOf`), `ExamplesScreen.kt` (`paletteChoiceProperty`, `followSystem`), `Example.kt` (`ExampleContext.followSystemPalette`), `themes/NeobrutalismExample.kt`.
- Tests: `SystemThemeTest`, `FollowSystemPaletteTest`, `ThemeModeTest`, `TerminalSessionTest.readsTheTerminalColors…`, `ExamplesScreenTest` (five new cases).
