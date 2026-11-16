---
title: "Component palettes: a palette is always active, themes derive colors from it, ColorTheme is a deprecated input whose screen setter switches the palette, and th…"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 29, tokens: 478164 }
id: l-0bd5cb74
scope: repo
status: active
src: agent
touches:
  - docs/component-palettes.md
  - docs/component-themes.md
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/ComponentThemes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/behavior/ComponentThemeOverride.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/ButtonBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/CheckBoxBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/HBoxBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/HeaderBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/HorizontalNumberInputBuilder.kt
---

## Decided
Session B of the palette plan (2026-11-16). `ComponentPalette` (27 `PaletteKey`s, `isDark`, sparse `overrides`, `withPrimary`) lives on the screen and every component next to the theme, propagated and pinned the same way. `ThemeEntry.styles` is `(ComponentPalette) -> ComponentStyleSet`; `ThemeEntry.stylesFor` applies the palette's overrides after the derivation. The default theme's derivations (`PaletteStyles`) are the 1.x `convertColorTheme` rules in palette terms; Neobrutalism derives its own (hard swaps, alerts as a blend of the status color toward the surface).

`ColorTheme.toComponentPalette()` maps `secondaryBackgroundColor` to `BACKGROUND` and `primaryBackgroundColor` to `SURFACE`, the reverse of the grill table: `DefaultRootContainer` painted the screen with the secondary background and `toContainerStyle` the panels with the primary one (gamebookers: grey screen, blue panels). Keeping the names' order would have swapped every 1.x app's screen and panel colors.

`ColorTheme` stays as a deprecated input: `screen.theme = x` switches the screen palette to the adapted one, but only when `x` differs from the current theme, because every `BaseView` sets `screen.theme` on dock and would otherwise undo a palette set in `ZirconDefaults` or on the screen. A builder's `colorTheme` presets (never pins) the adapted palette. `ZirconDefaults.componentPalette` defaults to the adapted `ZirconDefaults.theme`.

Themes became singletons (`ComponentThemes.neobrutalism()`, `default()`): shape has no knobs yet; `NeobrutalismBuilder`, `NeobrutalismPalette`, `ComponentTheme.mode` are gone, `ThemeMode` now picks a palette (`Palettes.neobrutalism(mode)`).

## Tried and ruled out
- Per-component `ColorTheme` setters converting to a palette on every change: the attach-time `themeProperty.updateFrom(parent)` fires before the palette inheritance and would have overwritten a propagated Neobrutalism palette with the adapted color theme. Only the screen's setter bridges.
- Keeping HyperUI's exact pale alert hexes as palette keys (`infoBackground`, `successDot`...): status keys are one color plus `ON_*`; the pale wash and the dark text are derived by the theme (`blend` toward surface 0.8, toward onSurface 0.6). Exact colors can come back as palette `overrides`.
- Deprecating `ColorTheme` without suppressions: ~150 warnings from the plumbing; the files that must keep using it carry `@file:Suppress("DEPRECATION")`.

## Watch out
- Visual changes to eyeball in the examples app: input fields and text areas are now sunken wells (`ON_BACKGROUND` on `SURFACE_SUNKEN`) with the selection colors while focused, where 1.x inverted them; the selected tab is `ON_SELECTION` on `SELECTION` (accent-tinted) instead of the primary text/background pair; the progress bar fills in `PRIMARY` instead of muted text.
- Interactive hover is still the 1.x rule (`TEXT_MUTED` on the emphasis color); it may read badly on real palettes (Catppuccin subtext on blue). Tune in D.
- `ComponentPaletteBuilder` requires `background`, `onBackground`, `primary` and derives the rest by blending toward white/black (`STEP` 0.12) and toward the background (`MUTED` 0.35); a palette that wants exact steps must set them.
- Pre-existing, unrelated failures on this machine: `ExamplesScreenTest.aRebuiltScreenKeepsTheSelection` and `aTilesetChangeReachesTheGridAndTheComponents` (same at HEAD).

In the code:
- `zircon.core/.../api/component/theme/PaletteKey.kt`, `ComponentPalette.kt` (interface, extension getters, `blend`, `isDark`), `ComponentPaletteBuilder.kt`, `PaletteStyles.kt`, `StyleOverrideKey.kt`, `ColorThemeAdapter.kt`; `api/Palettes.kt`; `api/behavior/ComponentPaletteOverride.kt`
- `internal/component/theme/DefaultComponentPalette.kt`, `DefaultComponentTheme.kt` (derivations), `neobrutalism/NeobrutalismPalettes.kt`, `NeobrutalismTheme.kt` (object)
- bridge: `internal/component/impl/ModalComponentContainer.kt` (`theme` setter); propagation: `DefaultContainer`, `DefaultComponent.inheritComponentPalette` / `applyPalette`
- tests: `ComponentPaletteTest`, `ComponentThemeTest`, `NeobrutalismRenderTest`; example: `zircon.examples/.../themes/NeobrutalismExample.kt`
