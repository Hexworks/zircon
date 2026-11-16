---
title: "Component palettes grill: theme keeps shape, palette keeps 27 semantic colors, roles split into role+variant+part, a default theme and palette are always active"
author: Bruce
date: 2026-11-16
branch: master
cost: { minutes: 57, tokens: 155895 }
id: l-6752a3fb
scope: repo
status: active
src: agent
touches:
  - zircon.core/api/zircon.core.api
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/ComponentThemes.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/behavior/ComponentThemeOverride.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/ButtonBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/CheckBoxBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/HBoxBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/HeaderBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/IconBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/InputBuilder.kt
  - zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/builder/component/LabelBuilder.kt
---

## Decided
Grill 2026-11-16 after the research on mc, lazygit, DaisyUI, shadcn/TweakCN, Textual, Catppuccin/Tokyo Night/Kanagawa (docs/research/semantic-component-palettes.md). Full plan in docs/component-palettes.md.

- `ThemeEntry` loses its `ComponentStyleSet`; it gets `styles: (ComponentPalette) -> ComponentStyleSet`. Theme owns the derivation rule per state, palette owns colors. Blends reuse existing `Color` extensions (`interpolateTo`, `tint`, `shade`, `tone`, `desaturate`); no new primitive.
- `ComponentPalette`: `PaletteKey` enum with 27 keys in surface / on-surface style (background, surface, surfaceRaised, surfaceSunken, textMuted, textDisabled, border, borderFocused, primary, secondary, accent, selection, info, success, warning, error + on* pairs), `isDark`, sparse `overrides[(role, variant, part, state)]`, `withPrimary(...)`. No separate focus ring: a tile has one fg and one bg.
- Flat `ComponentRole` splits into closed enums `ComponentRole` (kind) + `Variant` (DEFAULT, PRIMARY, SECONDARY, LINK, INFO, SUCCESS, WARNING, ERROR) + `Part`. Lookup fallback: part, then variant, never role; then the default theme.
- A theme is always active: stock renderers become `ComponentThemes.default()`, screen/ZirconDefaults theme non-null. A palette is always active: default is `ZirconDefaults.theme.toComponentPalette()`; `ColorTheme` deprecated via that adapter.
- `ThemeMode` moves to palette selection (`Palettes.catppuccin(mode = SYSTEM)`); `ComponentTheme.mode` removed.
- Bundled palettes: Catppuccin x4, Tokyo Night x4, Kanagawa x3, Neobrutalism light/dark.
- Sessions in order C (role split), A (default theme), B (palette), D (bundled palettes + examples selector); the BinarySelector/HBox/TextArea refactors move after D.

## Tried and ruled out
- ARIA state flags (selected, invalid, ...) as a core model now: the user wants no ARIA until it has a function; deferred to ROADMAP. Selected tab stays renderer-handled via `isSelected`, colored from `selection`.
- Palette supplying every state (mc style, ~135 colors): too heavy for palette authors, leaks state semantics into the palette.
- Palette inside the theme object: switching palette would rebuild the theme; separate screen property instead.
- String/value-class variants: variants must port between themes, so closed enum.

## Watch out
- Decorations cannot differ per tab-bar orientation any more (one `TAB_BAR` entry); the decoration reads orientation.
- The user prefers one grill question at a time; a four-question batch slipped through this session, keep to one.

In the code:
- docs/component-palettes.md (plan, sessions C/A/B/D), docs/component-themes.md (*(changes)* markers), docs/component-refactoring-handover.md (order), ROADMAP.md (state flags, base16, terminal following)
- existing color helpers: zircon.core/src/commonMain/kotlin/org/hexworks/zircon/api/color/extensions/ColorExtensions.kt
