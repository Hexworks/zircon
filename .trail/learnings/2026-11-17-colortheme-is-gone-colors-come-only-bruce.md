---
title: "ColorTheme is gone: colors come only from ComponentPalette, the default is Catppuccin Mocha, and the 77 legacy five-color themes are not bundled any more"
author: Bruce
date: 2026-11-17
branch: master
cost: { minutes: 13, tokens: 145361 }
id: l-22b01078
scope: repo
status: active
src: agent
touches:
  - ROADMAP.md
  - docs/research/semantic-component-palettes.md
---

## Decided
2026-11-17, on the user's call ("from now on we only use the new themes"), a session after the palette plan had deprecated `ColorTheme`. Removed outright: `ColorTheme`, `ColorThemes` (77 themes), `ColorThemeResource`, `ColorThemeBuilder`, the adapter (`toComponentPalette`), `ColorThemeOverride` / `HasColorTheme`, `ComponentMetadata.themeProperty`, the builders' `colorTheme`, `ZirconDefaults.theme`, `screen.theme`, `Group`/`MenuBar` `theme`, `View`/`BaseView` `theme` (a view now takes a `ComponentPalette`), `ComponentRenderContext.theme` (`component4()` is the palette now). The default palette is `Palettes.catppuccin()` (Mocha); the examples' default palette and the golden images follow it (goldens regenerated for compose and terminal).
- A component or modal no longer needs "its own theme or style set" when it does not update on attach: the palette is always there, so those `require`s are gone.
- `LogArea.addRow(applyTheme)` applies the log area's palette instead of its theme.
- The toggle button renderer reads `palette.textMuted` / `primary` / `onSurface` for its switch.
- Tests: the per-component `expectedComponentStyles` (five-color expectations) and `DEFAULT_THEME` are gone from `CommonComponentTest`; nothing read them.
- New palettes and color fine-tuning are the next theme session; `ROADMAP.md` keeps the palette ideas.

## Tried and ruled out
- Keeping the 77 legacy themes as bundled palettes through the adapter: the user wants only the new themes; re-adding a favourite later is one `componentPalette { }`.

## Watch out
- The three container files keep `@file:Suppress("DEPRECATION")` for other deprecated calls; every other file-level suppress that was there for `ColorTheme` is gone.
- `docs/research/semantic-component-palettes.md` still describes `ColorTheme` as history; it is a research record, not current API.

In the code:
- Deleted under `zircon.core/.../api`: `component/ColorTheme.kt`, `ColorThemes.kt`, `builder/component/ColorThemeBuilder.kt`, `behavior/ColorThemeOverride.kt`, `behavior/HasColorTheme.kt`, `component/theme/ColorThemeAdapter.kt`, `extensions/ColorThemeExtensions.kt`, `component/extensions/ColorThemeExtensions.kt`; under `internal`: `resource/ColorThemeResource.kt`, `component/impl/DefaultColorTheme.kt`, `behavior/impl/DefaultColorThemeOverride.kt`.
- `ZirconDefaultsBuilder.DEFAULT_COMPONENT_PALETTE = Palettes.catppuccin()`; `BaseView(tileGrid, palette)`; `zircon.examples/.../Catalog.kt` (`PALETTES = Palettes.all()`).
